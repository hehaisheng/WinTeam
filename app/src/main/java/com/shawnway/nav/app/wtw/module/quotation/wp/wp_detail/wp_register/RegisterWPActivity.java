package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_register;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.tool.PasswordUtil;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.LoadingView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Cicinnus on 2016/12/8.
 */

public class RegisterWPActivity extends BaseActivity<RegisterWPPresenter> implements RegisterWPContract.IRegisterWpView {


    public static void start(Context context) {
        Intent starter = new Intent(context, RegisterWPActivity.class);
        context.startActivity(starter);
    }

    @BindView(R.id.send_sms_code)
    Button sendCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;

    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.top_back)
    ImageButton topBack;

    private int SECONDS = 60 * 5 * 1000;
    private Handler handler = new Handler();
    private LoadingView loadingGif;
    private String phone;

    @Override
    protected int getLayout() {
        return R.layout.activity_register_wp;
    }

    @Override
    protected RegisterWPPresenter getPresenter() {
        return new RegisterWPPresenter(mContext,this);
    }

    @Override
    protected void initEventAndData() {
        topTextCenter.setText("注册粤国际微盘");
        setVisiable(toolbar, topBack, topTextCenter);
        phone = SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT)
                .getString(SPUtils.PHONE, "");
        loadingGif = new LoadingView(mContext);

    }

    @OnClick({R.id.send_sms_code, R.id.top_back, R.id.top_text_right,R.id.btn_register_wp})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.send_sms_code:
                mPresenter.sendSmsCode(phone);
                break;
            case R.id.btn_register_wp:
                if(etCode.getText().length()==0){
                    ToastUtil.showShort(mContext,"请输入验证码");
                    return;
                }
                String aes_pwd = SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT)
                        .getString(SPUtils.PWD, "");
                String pwd = "";
                try {
                    pwd = PasswordUtil.aesDecrypt(aes_pwd, PasswordUtil._key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mPresenter.registerWP(phone, pwd, etCode.getText().toString().trim());
                break;
        }
    }

    @Override
    public void sendSmsCodeSuccess() {
        ToastUtil.showShort(mContext, "验证码已发送");
        loadingGif.dismiss();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (SECONDS > 0) {
                    sendCode.setText(String.format("%s S", SECONDS));
                    sendCode.setTextColor(Color.WHITE);
                    sendCode.postDelayed(this, 1000);
                    SECONDS--;
                } else {
                    sendCode.setTextColor(Color.WHITE);
                    sendCode.setText("获取验证码");
                }
            }
        });
    }

    @Override
    public void registerWPSuccess() {
        ToastUtil.showShort(mContext,"注册成功");
        finish();
    }

    @Override
    public void showLoading() {
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
        loadingGif.dismiss();
        ToastUtil.showShort(mContext,errorMsg);
    }

}
