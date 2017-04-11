package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.tran.transfer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.WPStateDescWrapper;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.WithDrawTranBean;
import com.shawnway.nav.app.wtw.service.UpdateJlWpInfoService;
import com.shawnway.nav.app.wtw.tool.PasswordUtil;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.NormalDialog;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Cicinnus on 2016/12/9
 * 提现页面
 */

public class JlWithDrawFragment extends BaseFragment<JlTransferTranPresenter> implements JlTransferTranConstract.ITransferTranView {


    private final String RESULT_OK = "200";
    private final String RESULT_ERROR = "500";
    @BindView(R.id.fragment_withdraw_useable_balance)
    TextView fragmentWithdrawUseableBalance;
    @BindView(R.id.fragment_withdraw_frozen_balance)
    TextView fragmentWithdrawFrozenBalance;
    @BindView(R.id.fragment_withdraw_amount)
    EditText fragmentWithdrawAmount;
    @BindView(R.id.fragment_withdraw_cardholder)
    EditText fragmentWithdrawCardholder;
    @BindView(R.id.fragment_withdraw_cardno)
    TextView fragmentWithdrawCardno;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_send_sms_code)
    TextView tvSendSmsCode;
    @BindView(R.id.fragment_withdraw_btn)
    Button fragmentWithdrawBtn;
    @BindView(R.id.root_view)
    LinearLayout rootView;

    private String BANK_CARD_HOLDER_NAME = "";
    private NormalDialog withdrawDialog;

    private String commit_tip = "";
    private LoadingView loadingGif;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_jl_wp_withdraw;
    }

    public static JlWithDrawFragment newInstance() {

        Bundle args = new Bundle();

        JlWithDrawFragment fragment = new JlWithDrawFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected JlTransferTranPresenter getPresenter() {
        return new JlTransferTranPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        setPricePoint(fragmentWithdrawAmount);
    }


    /**
     * 懒加载
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initData();
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        String access_token = SPUtils.getInstance(mContext, SPUtils.SP_JL_WP)
                .getString(SPUtils.JL_WP_TOKEN, "");
        initUserBalance(access_token);
        initUserAccount(access_token);
    }

    /**
     * 初始化用户资金信息（可用余额）
     *
     * @param access_token
     */
    private void initUserAccount(String access_token) {
        mPresenter.getUserAccountInfo(access_token);
    }

    /**
     * 初始化用户绑定银行卡信息
     *
     * @param access_token
     */
    private void initUserBalance(String access_token) {
        mPresenter.getUserBalanceInfo(access_token);
    }

    /**
     * 设置用户绑定银行卡信息
     *
     * @param jlWithDrawBean
     */
    @Override
    public void addUserBalanceInfo(JlWithDrawBean jlWithDrawBean) {
        if (TextUtils.equals(jlWithDrawBean.getState(), RESULT_OK)) {
            if (jlWithDrawBean.getData() != null) {
                fragmentWithdrawCardno.setText(jlWithDrawBean.getData().getBankNo());
            } else {
                Toast.makeText(mContext, "还未绑定银行卡,请先进行充值绑定,再进行提现操作", Toast.LENGTH_SHORT).show();
            }
        } else if (TextUtils.equals(jlWithDrawBean.getState(), RESULT_ERROR)) {
            Toast.makeText(mContext, "获取银行卡信息失败,请稍后再试", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 设置用户资金信息（可用余额）
     *
     * @param accountBean
     */
    @Override
    public void addUserAccountInfo(UserAccountBean accountBean) {
        if (TextUtils.equals(accountBean.state, RESULT_OK)) {
            fragmentWithdrawUseableBalance.setText(accountBean.data.getUseableBalance() + "");
            fragmentWithdrawFrozenBalance.setText(accountBean.data.getFrozenBalance() + "");
        } else if (TextUtils.equals(accountBean.state, RESULT_ERROR)) {
            Toast.makeText(mContext, "获取账户余额失败,请稍后再试", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void showLoading() {
        loadingGif = new LoadingView(mContext);
        loadingGif.show();
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {
        loadingGif.dismiss();
    }

    @Override
    public void showError(String errorMsg) {
        ToastUtil.showShort(mContext, errorMsg);
        loadingGif.dismiss();
    }

    @OnClick({R.id.fragment_withdraw_btn,R.id.tv_send_sms_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_withdraw_btn:
                if (validateNull()) {
                    withdrawDialog = NormalDialog.newInstance("提现", "是否提现" + fragmentWithdrawAmount.getText().toString() + "元到绑定银行卡", "确定");
                    withdrawDialog.setOnDialogClickListener(new NormalDialog.OnConfirmClickListener() {
                        @Override
                        public void onClick() {
                            //微盘提现
                            ensureWithDraw();
                            withdrawDialog.dismiss();
                        }
                    });
                    withdrawDialog.showDialog(getFragmentManager());
                } else {
                    Toast.makeText(mContext, commit_tip, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_send_sms_code:
                String token = SPUtils.getInstance(mContext, SPUtils.SP_JL_WP).getString(SPUtils.JL_WP_TOKEN, "");
                mPresenter.sendSmsCode(token);
                break;
        }
    }

    /**
     * 验证器
     *
     * @return
     */
    private boolean validateNull() {
        if (isTextsEmpty(fragmentWithdrawAmount)
                && isTextsEmpty(fragmentWithdrawCardholder)
                && isTextsEmpty(fragmentWithdrawCardno)
                && isTextsEmpty(etCode)
                ) {
            if (Double.valueOf(fragmentWithdrawAmount.getText().toString().trim()) >= 10) {
                return true;
            } else {
                commit_tip = "提交金额不能少于10元";
                return false;
            }
        }
        commit_tip = "请先完善提现信息";
        return false;
    }

    /**
     * 判断空值
     *
     * @param textView
     * @return
     */
    private boolean isTextsEmpty(TextView textView) {
        boolean isTextEmpty;
        if (!TextUtils.isEmpty(textView.getText().toString().trim()) && !TextUtils.equals(textView.getText().toString().trim(), "")) {
            isTextEmpty = true;
        } else isTextEmpty = false;
        return isTextEmpty;
    }

    /**
     * 微盘提现
     */
    private void ensureWithDraw() {
        String pwd = "";
        try {
            pwd = PasswordUtil.aesDecrypt(SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT).getString(SPUtils.PWD, ""), PasswordUtil._key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        WithDrawTranBean withDrawTranBean = new WithDrawTranBean();
        withDrawTranBean.setAmount(fragmentWithdrawAmount.getText().toString());
        withDrawTranBean.setCardno(fragmentWithdrawCardno.getText().toString());
        withDrawTranBean.setPwd(pwd);
        withDrawTranBean.setVerifyCode(etCode.getText().toString());
        withDrawTranBean.setCardusername(fragmentWithdrawCardholder.getText().toString());
        String access_token = SPUtils.getInstance(mContext, SPUtils.SP_JL_WP)
                .getString(SPUtils.JL_WP_TOKEN, "");

        //保存绑定银行卡信息到自己的后台
        updateWPInfo(withDrawTranBean);

        mPresenter.putWithDrawTran(access_token, withDrawTranBean);
    }


    /**
     *
     */
    private void updateWPInfo(WithDrawTranBean withDrawTranBean) {
        Intent intent = new Intent(mContext, UpdateJlWpInfoService.class);
        intent.putExtra(UpdateJlWpInfoService.MOBILE
                , SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT).getString(SPUtils.PHONE, ""));
        intent.putExtra(UpdateJlWpInfoService.BANK_CARD_NUM, withDrawTranBean.getCardno());
        intent.putExtra(UpdateJlWpInfoService.BANK_CARD_HOLDER_NAME, withDrawTranBean.getCardusername());
        mContext.startService(intent);

    }

    /**
     * 返回提现结果
     *
     * @param wpStateDescWrapper
     */
    @Override
    public void addWithDrawTranResult(WPStateDescWrapper wpStateDescWrapper) {
        if(wpStateDescWrapper.state.equals("200")){
            ToastUtil.showShort(mContext,"提现成功，请留意银行卡余额变化");
            mContext.finish();
        }else {
            String str;
            switch (wpStateDescWrapper.desc) {
                case "balance_not_enough":
                    str = "余额不足";
                    break;
                case "system_error":
                    str = "系统错误";
                    break;
                case "Server_Exception":
                    str = "服务器异常";
                    break;
                case "more_than_single_limit_amount":
                    str = "超过单笔提现金额";
                    break;
                case "more_than_max_limit_amount":
                    str = "超过当天提现总额";
                    break;
                case "no_withdraw":
                    str = "已经被禁止提现";
                    break;
                case "repeated_submission":
                    str = "重复提交时出现，一小时只能提交一次";
                    break;
                case "no_transaction_before":
                    str = "非赢家券交易金额不满 20 元";
                    break;
                case "amount_less_than_ten":
                    str = "提现金额不能小于 10";
                    break;
                default:
                    str = "提现失败：" + wpStateDescWrapper.desc;
                    break;
            }
            ToastUtil.showShort(mContext, str);
        }
    }

    @Override
    public void sendSuccess() {
        loadingGif.dismiss();
        ToastUtil.showShort(mContext,"发送成功，请留意手机短信");
        Observable
                .interval(0, 1, TimeUnit.SECONDS)
                .take(120)
                .compose(SchedulersCompat.<Long>applyIoSchedulers())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        tvSendSmsCode.setText("发送验证码");
                        this.unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        tvSendSmsCode.setText(String.format("%s", 120 - aLong));
                    }
                });
    }

    /**
     * 判断两位小数
     *
     * @param editText
     */
    private void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

        });

    }

}
