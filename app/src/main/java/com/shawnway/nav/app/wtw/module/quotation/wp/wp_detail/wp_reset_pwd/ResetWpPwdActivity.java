package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_reset_pwd;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.tool.PasswordUtil;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.LoadingView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Cicinnus on 2017/1/10.
 */

public class ResetWpPwdActivity extends BaseActivity<ResetWpPwdPresenter> implements ResetWpPwdContract.IResetWpPwdView {

    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_send_sms_code)
    TextView tvSendSmsCode;
    private String phone;
    private LoadingView loadingView;


    public static void start(Context context) {
        Intent starter = new Intent(context, ResetWpPwdActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_reset_wp_pwd;
    }

    @Override
    protected ResetWpPwdPresenter getPresenter() {
        return new ResetWpPwdPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        loadingView = new LoadingView(mContext);
        topTextCenter.setText("重置粤国际微盘密码");
        setVisiable(topBack, toolbar, topTextCenter);
        phone = SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT).getString(SPUtils.PHONE, "");
    }

    @OnClick({R.id.top_back,R.id.tv_send_sms_code,R.id.btn_reset_pwd_wp})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.tv_send_sms_code:
                mPresenter.sendSmsCode(phone);
                break;
            case R.id.btn_reset_pwd_wp:
                if (etCode.getText().length() == 0) {
                    ToastUtil.showShort(mContext, "验证码不能为空");
                    return;
                }
                String pwd = SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT).getString(SPUtils.PWD, "");
                String newPwd = "";
                try {
                    newPwd = PasswordUtil.aesDecrypt(pwd, PasswordUtil._key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mPresenter.resetPwd(phone, etCode.getText().toString().trim(), newPwd);
                break;
        }

    }


    @Override
    public void sendSmsSuccess() {
        ToastUtil.showShort(mContext,"已发送验证码");
        Observable
                .interval(0,1, TimeUnit.SECONDS)
                .take(300)
                .compose(SchedulersCompat.<Long>applyIoSchedulers())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        this.unsubscribe();
                        tvSendSmsCode.setEnabled(true);
                        tvSendSmsCode.setText("获取验证码");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        tvSendSmsCode.setEnabled(false);
                        tvSendSmsCode.setText(String.format("%s",300-aLong));
                    }
                });
    }

    @Override
    public void resetPwdSuccess() {
        ToastUtil.showShort(mContext,"重置成功");
        loadingView.dismiss();
        finish();
    }

    @Override
    public void showLoading() {
        loadingView.show();
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {
        loadingView.dismiss();
    }

    @Override
    public void showError(String errorMsg) {
        loadingView.dismiss();
    }

}
