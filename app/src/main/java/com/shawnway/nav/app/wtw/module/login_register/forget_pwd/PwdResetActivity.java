package com.shawnway.nav.app.wtw.module.login_register.forget_pwd;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.module.login_register.RegisterActivity;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.StringUtils;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.LoadingView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;


/**
 * 忘记密码
 * Created by Administrator on 2016/7/27 0027.
 */
public class PwdResetActivity extends BaseActivity<PwdResetPresenter> implements PwdResetContract.IPwdResetView, View.OnClickListener {
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_sendSmsCode)
    Button btnSendSmsCode;
    @BindView(R.id.et_newPwd)
    EditText etNewPwd;
    @BindView(R.id.btn_resetConfirm)
    Button btnResetConfirm;

    String phoneNum;
    String identifycode;
    String pwd;

    private LoadingView loadingGif ;

    public static void getInstance(Activity context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_password_reset;
    }

    @Override
    protected PwdResetPresenter getPresenter() {
        return new PwdResetPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        initToolbar();
    }

    private void initToolbar() {
        loadingGif = new LoadingView(mContext);
        topTextCenter.setText("密码重置");
        setVisiable(toolbar, topBack, topTextCenter);
    }

    @OnClick({R.id.top_back, R.id.btn_sendSmsCode, R.id.btn_resetConfirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.btn_sendSmsCode://获取验证码
                phoneNum = etPhone.getText().toString().trim();
                //手机号不能为空
                if (StringUtils.isEmpty(phoneNum)) {
                    ToastUtil.showShort(this, getString(R.string.phone_num_cannot_empty));
                    etPhone.requestFocus();
                    break;
                }
                mPresenter.sendSmsCode(phoneNum);
                break;
            case R.id.btn_resetConfirm://提交
                phoneNum = etPhone.getText().toString().trim();
                identifycode = etCode.getText().toString().trim();
                pwd = etNewPwd.getText().toString().trim();
                if (StringUtils.isEmpty(phoneNum)) {
                    ToastUtil.showShort(this, getString(R.string.phone_num_cannot_empty));
                    etPhone.requestFocus();
                    return;
                }
                if (StringUtils.isEmpty(identifycode)) {
                    ToastUtil.showShort(this, getString(R.string.identify_code_cannot_empty));
                    etCode.requestFocus();
                    return;
                }
                if (StringUtils.isEmpty(pwd)) {
                    ToastUtil.showShort(this, getString(R.string.loginpwd_cannot_empty));
                    etNewPwd.requestFocus();
                    return;
                }
                mPresenter.resetPwd(phoneNum, pwd, identifycode);

        }
    }


    @Override
    public void resetSuccess() {
        ToastUtil.showShort(mContext, "密码已重置");
        LoginActivity.getInstance(mContext);
        finish();
    }

    @Override
    public void sendSuccess() {
        loadingGif.dismiss();
        ToastUtil.showShort(mContext, "验证码已发送，请留意手机短信");
        Observable
                .interval(1, TimeUnit.SECONDS)
                .take(300)
                .compose(SchedulersCompat.<Long>applyIoSchedulers())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        this.unsubscribe();
                        btnSendSmsCode.setText("获取验证码");
                        btnSendSmsCode.setEnabled(true);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        btnSendSmsCode.setEnabled(false);
                        btnSendSmsCode.setText(String.format("%s", 300 - aLong));
                    }
                });
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

    }

    @Override
    public void showError(String errorMsg) {
        ToastUtil.showShort(mContext, errorMsg);
        loadingGif.dismiss();
    }

}
