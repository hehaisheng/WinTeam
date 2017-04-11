package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.transfer_tran;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserBalanceBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.WPStateDescWrapper;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.WithDrawTranBean;
import com.shawnway.nav.app.wtw.service.UpdateWpInfoService;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.NormalDialog;
import com.shawnway.nav.app.wtw.view.city_pick.AddrXmlParser;
import com.shawnway.nav.app.wtw.view.city_pick.city_bean.CityInfoModel;
import com.shawnway.nav.app.wtw.view.city_pick.city_bean.ProvinceInfoModel;
import com.shawnway.nav.app.wtw.view.city_pick.widget.WheelView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Kevin on 2016/12/9
 * 提现页面
 */

public class WithDrawFragment extends BaseFragment<TransferTranPresenter> implements TransferTranConstract.ITransferTranView {
    @BindView(R.id.root_view)
    LinearLayout root_view;
    @BindView(R.id.fragment_withdraw_useable_balance)
    TextView fragmentWithdrawUseableBalance;
    @BindView(R.id.fragment_withdraw_frozen_balance)
    TextView fragmentWithdrawFrozenBalance;
    @BindView(R.id.fragment_withdraw_amount)
    EditText fragmentWithdrawAmount;
    @BindView(R.id.fragment_withdraw_cardholder)
    EditText fragmentWithdrawCardholder;
    @BindView(R.id.fragment_withdraw_province)
    TextView fragmentWithdrawProvince;
    @BindView(R.id.fragment_withdraw_bankname)
    TextView fragmentWithdrawBankname;
    @BindView(R.id.fragment_withdraw_branchname)
    EditText fragmentWithdrawBranchname;
    @BindView(R.id.fragment_withdraw_cardno)
    TextView fragmentWithdrawCardno;
    @BindView(R.id.fragment_withdraw_btn)
    Button fragmentWithdrawBtn;

    private final String RESULT_OK = "200";
    private final String RESULT_ERROR = "500";
    private String BANK_CARD_HOLDER_NAME = "";


    /**
     * 与选择地址相关
     */
    protected ArrayList<String> mProvinceDatas;
    protected Map<String, ArrayList<String>> mCitisDatasMap = new HashMap<String, ArrayList<String>>();
    protected String mCurrentProviceName;
    protected String mCurrentCityName;
    private boolean isDataLoaded = false;
    private View contentView;
    private WheelView mProvincePicker;
    private WheelView mCityPicker;
    private WheelView mCountyPicker;
    private TextView cancel;
    private TextView confirm;
    private PopupWindow addrPopWindow;
    private NormalDialog withdrawDialog;

    private String commit_tip = "";
    private LoadingView loadingGif;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wp_withdraw;
    }

    public static WithDrawFragment newInstance() {

        Bundle args = new Bundle();

        WithDrawFragment fragment = new WithDrawFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected TransferTranPresenter getPresenter() {
        return new TransferTranPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        initPickCityView();
        setPricePoint(fragmentWithdrawAmount);
    }

    /**
     * 初始化选择器
     */
    private void initPickCityView() {
        contentView = LayoutInflater.from(mContext).inflate(
                R.layout.layout_addr_picker, null);
        mProvincePicker = (WheelView) contentView.findViewById(R.id.province);
        mCityPicker = (WheelView) contentView.findViewById(R.id.city);
        mCountyPicker = (WheelView) contentView.findViewById(R.id.county);
        mCountyPicker.setVisibility(View.GONE);
        cancel = (TextView) contentView.findViewById(R.id.cancel);
        confirm = (TextView) contentView.findViewById(R.id.confirm);

        addrPopWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addrPopWindow.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentWithdrawProvince.setText(mCurrentProviceName + "--" + mCurrentCityName);

                addrPopWindow.dismiss();
            }
        });

        mProvincePicker.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                String provinceText = mProvinceDatas.get(id);
                if (!mCurrentProviceName.equals(provinceText)) {
                    mCurrentProviceName = provinceText;
                    ArrayList<String> mCityData = mCitisDatasMap.get(mCurrentProviceName);
                    mCityPicker.resetData(mCityData);
                    mCityPicker.setDefault(0);
                    mCurrentCityName = mCityData.get(0);
                }
            }

            @Override
            public void selecting(int id, String text) {
            }
        });

        mCityPicker.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                ArrayList<String> mCityData = mCitisDatasMap.get(mCurrentProviceName);
                String cityText = mCityData.get(id);
                if (!mCurrentCityName.equals(cityText)) {
                    mCurrentCityName = cityText;
                }
            }

            @Override
            public void selecting(int id, String text) {

            }
        });


        // 启动线程读取数据
        new Thread() {
            @Override
            public void run() {
                // 读取数据
                isDataLoaded = readAddrDatas();

                // 通知界面线程
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mProvincePicker.setData(mProvinceDatas);
                        mProvincePicker.setDefault(0);
                        mCurrentProviceName = mProvinceDatas.get(0);

                        ArrayList<String> mCityData = mCitisDatasMap.get(mCurrentProviceName);
                        mCityPicker.setData(mCityData);
                        mCityPicker.setDefault(0);
                        mCurrentCityName = mCityData.get(0);
                    }
                });
            }
        }.start();
    }


    /**
     * 读取地址数据，请使用线程进行调用
     *
     * @return
     */
    protected boolean readAddrDatas() {
        List<ProvinceInfoModel> provinceList;
        AssetManager asset = mContext.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            AddrXmlParser handler = new AddrXmlParser();
            parser.parse(input, handler);
            input.close();
            provinceList = handler.getDataList();
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityInfoModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                }
            }
            mProvinceDatas = new ArrayList<>();
            for (int i = 0; i < provinceList.size(); i++) {
                mProvinceDatas.add(provinceList.get(i).getName());
                List<CityInfoModel> cityList = provinceList.get(i).getCityList();
                ArrayList<String> cityNames = new ArrayList<String>();
                for (int j = 0; j < cityList.size(); j++) {
                    cityNames.add(cityList.get(j).getName());
                }
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
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
        String access_token = SPUtils.getInstance(mContext, SPUtils.SP_WP)
                .getString(SPUtils.WP_TOKEN, "");
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
     * @param userBalanceBean
     */
    @Override
    public void addUserBalanceInfo(UserBalanceBean userBalanceBean) {
        if (TextUtils.equals(userBalanceBean.getState(), RESULT_OK)) {
            if (userBalanceBean.getData() != null) {
                fragmentWithdrawBankname.setText(userBalanceBean.getData().getRechargeBankName());
                fragmentWithdrawCardno.setText(userBalanceBean.getData().getRechargeBankNo());
            } else {
                Toast.makeText(mContext, "还未绑定银行卡,请先进行充值绑定,再进行提现操作", Toast.LENGTH_SHORT).show();
            }
        } else if (TextUtils.equals(userBalanceBean.getState(), RESULT_ERROR)) {
            Toast.makeText(mContext, "服务器异常,请稍后再试", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(mContext, "服务器异常,请稍后再试", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(mContext, "服务器异常,请稍后再试", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.fragment_withdraw_province, R.id.fragment_withdraw_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_withdraw_province:
                if (mContext.getCurrentFocus() != null) {
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mContext.getCurrentFocus().getWindowToken(), 0);

                }
                if (isDataLoaded) {
                    addrPopWindow.showAtLocation(root_view, Gravity.BOTTOM, 0, 0);
                }
                break;
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
                    withdrawDialog.showDialog(((TransferTranActivity) mContext).getSupportFragmentManager());
                } else {
                    Toast.makeText(mContext, commit_tip, Toast.LENGTH_SHORT).show();
                }
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
                && isTextsEmpty(fragmentWithdrawBankname)
                && isTextsEmpty(fragmentWithdrawBranchname)
                && isTextsEmpty(fragmentWithdrawCardholder)
                && isTextsEmpty(fragmentWithdrawCardno)
                && isTextsEmpty(fragmentWithdrawProvince)
                ) {
            if (Double.valueOf(fragmentWithdrawAmount.getText().toString().trim()) >= 2) {
                return true;
            } else {
                commit_tip = "提交金额不能少于2元";
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
        WithDrawTranBean withDrawTranBean = new WithDrawTranBean();
        withDrawTranBean.setAmount(fragmentWithdrawAmount.getText().toString());
        withDrawTranBean.setBankname(fragmentWithdrawBankname.getText().toString());
        withDrawTranBean.setBranchname(fragmentWithdrawBranchname.getText().toString());
        withDrawTranBean.setCardno(fragmentWithdrawCardno.getText().toString());
        withDrawTranBean.setProvince(mCurrentProviceName);
        withDrawTranBean.setCity(mCurrentCityName);
        withDrawTranBean.setCardusername(fragmentWithdrawCardholder.getText().toString());
        String access_token = SPUtils.getInstance(mContext, SPUtils.SP_WP)
                .getString(SPUtils.WP_TOKEN, "");

        //保存绑定银行卡信息到自己的后台
        updateWPInfo(withDrawTranBean);

        mPresenter.putWithDrawTran(access_token, withDrawTranBean);
    }


    /**
     * 提现的时候更新对应账号的微盘信息
     */
    private void updateWPInfo(WithDrawTranBean withDrawTranBean) {
        Intent intent = new Intent(mContext, UpdateWpInfoService.class);
        intent.putExtra(UpdateWpInfoService.MOBILE
                , SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT).getString(SPUtils.PHONE, ""));
        intent.putExtra(UpdateWpInfoService.BANK_NAME, withDrawTranBean.getBankname() + withDrawTranBean.getBranchname());
        intent.putExtra(UpdateWpInfoService.BANK_CARD_NUM, withDrawTranBean.getCardno());
        intent.putExtra(UpdateWpInfoService.BANK_CARD_HOLDER_NAME, withDrawTranBean.getCardusername());
        mContext.startService(intent);

    }

    /**
     * 返回提现结果
     *
     * @param wpStateDescWrapper
     */
    @Override
    public void addWithDrawTranResult(WPStateDescWrapper wpStateDescWrapper) {
        if (TextUtils.equals(wpStateDescWrapper.state, RESULT_OK)) {
            Toast.makeText(mContext, "提现信息提交成功,请等待后台处理", Toast.LENGTH_SHORT).show();
            mContext.finish();
        } else {
            if (TextUtils.equals(wpStateDescWrapper.desc, "out_of_trade_time")
                    && TextUtils.equals(wpStateDescWrapper.state, RESULT_ERROR)) {
                Toast.makeText(mContext, "非出金时间", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(mContext, wpStateDescWrapper.data, Toast.LENGTH_SHORT).show();

        }
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
