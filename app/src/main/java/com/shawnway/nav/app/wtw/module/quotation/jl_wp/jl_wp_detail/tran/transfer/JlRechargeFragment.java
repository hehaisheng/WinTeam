package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.tran.transfer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.net.JlWPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.X5WebView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Cicinnus on 2016/12/9
 * 吉交所微盘充值页面
 */

public class JlRechargeFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {



    @BindView(R.id.tv_confirm_wechat)
     Button tv_wechat;
    @BindView(R.id.tv_confirm_alipay)
    Button tv_alipay;
    @BindView(R.id.et_bankCard)
    EditText etBankCard;
    @BindView(R.id.cb5000)
    CheckBox cb5000;
    @BindView(R.id.cb2000)
    CheckBox cb2000;
    @BindView(R.id.cb1000)
    CheckBox cb1000;
    @BindView(R.id.cb500)
    CheckBox cb500;
    @BindView(R.id.cb100)
    CheckBox cb100;
    @BindView(R.id.cb_customize)
    CheckBox cbCustomize;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.ll_customize_money)
    LinearLayout ll_customize_money;

    private X5WebView wb;
    private String bankCardNum;
    private int money = 5000;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_jl_wp_recharge;
    }

    public static JlRechargeFragment newInstance() {

        Bundle args = new Bundle();

        JlRechargeFragment fragment = new JlRechargeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initEventAndData() {

//        getUserBankCard();
        initListener();
    }

    private void initListener() {
        cb100.setOnCheckedChangeListener(this);
        cb500.setOnCheckedChangeListener(this);
        cb1000.setOnCheckedChangeListener(this);
        cb2000.setOnCheckedChangeListener(this);
        cb5000.setOnCheckedChangeListener(this);
        cbCustomize.setOnCheckedChangeListener(this);
    }

    /**
     * 获取用户绑定的银行卡，如果有绑定，则不允许用户手动输入
     */
    private void getUserBankCard() {
        final LoadingView loadingView = new LoadingView(mContext);
        loadingView.show();
        String access_token = SPUtils.getInstance(mContext, SPUtils.SP_JL_WP)
                .getString(SPUtils.JL_WP_TOKEN, "");
        JlWPRetrofitClient.getInstance()
                .api()
                .getUserBankInfo(access_token)
                .compose(SchedulersCompat.<JlWithDrawBean>applyIoSchedulers())
                .subscribe(new BaseSubscriber<JlWithDrawBean>() {
                    @Override
                    public void onSuccess(JlWithDrawBean jlWithDrawBean) {
                        loadingView.dismiss();
                        if (jlWithDrawBean.getData() != null) {
                            etBankCard.setText(String.format("%s", jlWithDrawBean.getData().getBankNo()));
                            etBankCard.setFocusable(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        loadingView.dismiss();
                    }
                });
    }

    @OnClick({R.id.tv_confirm_wechat,R.id.tv_confirm_alipay})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm_wechat:
//                if (etBankCard.getText().length() == 0) {
//                    ToastUtil.showShort(mContext, "请输入银行卡号");
//                    return;
//                }
                if (ll_customize_money.getVisibility() == View.VISIBLE && etMoney.getText().length() == 0) {
                    ToastUtil.showShort(mContext, "请输入需要充值的金额");
                    return;
                }
                JlChargeActivity.startWeChat(mContext, money);

                break;
            case R.id.tv_confirm_alipay:
                if (ll_customize_money.getVisibility() == View.VISIBLE && etMoney.getText().length() == 0) {
                    ToastUtil.showShort(mContext, "请输入需要充值的金额");
                    return;
                }
                JlChargeActivity.startAliPay(mContext, money,true);

                break;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb100:
                if (isChecked) {
                    money = 100;
                    ll_customize_money.setVisibility(View.GONE);
                    cb500.setChecked(false);
                    cb1000.setChecked(false);
                    cb2000.setChecked(false);
                    cb5000.setChecked(false);
                    cbCustomize.setChecked(false);
                }
                break;
            case R.id.cb500:
                if (isChecked) {
                    ll_customize_money.setVisibility(View.GONE);
                    money = 500;
                    cb100.setChecked(false);
                    cb1000.setChecked(false);
                    cb2000.setChecked(false);
                    cb5000.setChecked(false);
                    cbCustomize.setChecked(false);
                }
                break;
            case R.id.cb1000:
                if (isChecked) {
                    ll_customize_money.setVisibility(View.GONE);
                    money = 1000;
                    cb500.setChecked(false);
                    cb100.setChecked(false);
                    cb2000.setChecked(false);
                    cb5000.setChecked(false);
                    cbCustomize.setChecked(false);
                }
                break;
            case R.id.cb2000:
                if (isChecked) {
                    ll_customize_money.setVisibility(View.GONE);
                    money = 2000;
                    cb500.setChecked(false);
                    cb1000.setChecked(false);
                    cb100.setChecked(false);
                    cb5000.setChecked(false);
                    cbCustomize.setChecked(false);
                }
                break;
            case R.id.cb5000:
                if (isChecked) {
                    ll_customize_money.setVisibility(View.GONE);
                    money = 5000;
                    cb500.setChecked(false);
                    cb1000.setChecked(false);
                    cb2000.setChecked(false);
                    cb100.setChecked(false);
                    cbCustomize.setChecked(false);
                }
                break;
            case R.id.cb_customize:
                if (isChecked) {
                    ll_customize_money.setVisibility(View.VISIBLE);
                    cb500.setChecked(false);
                    cb1000.setChecked(false);
                    cb2000.setChecked(false);
                    cb5000.setChecked(false);
                    cb100.setChecked(false);
                }
                break;
        }
    }
}
