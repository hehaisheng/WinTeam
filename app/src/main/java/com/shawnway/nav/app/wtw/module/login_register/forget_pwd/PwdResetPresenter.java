package com.shawnway.nav.app.wtw.module.login_register.forget_pwd;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.ResetPwdResult;
import com.shawnway.nav.app.wtw.tool.SPUtils;

import retrofit2.Response;

/**
 * Created by Cicinnus on 2016/12/8.
 */

public class PwdResetPresenter extends BasePresenter<PwdResetContract.IPwdResetView> implements PwdResetContract.IPwdResetPresemter {

    private final PwdResetManager pwdResetManager;

    public PwdResetPresenter(Activity activity, PwdResetContract.IPwdResetView view) {
        super(activity, view);
        pwdResetManager = new PwdResetManager();
    }

    @Override
    public void sendSmsCode(String phone) {
        mView.showLoading();
        addSubscribe(pwdResetManager.sendForgetCode(phone)
                .subscribe(new BaseSubscriber<Response<ForgetCodeResult>>() {
                    @Override
                    public void onSuccess(Response<ForgetCodeResult> response) {
                        if (response.code() == 200) {
                            if ("SEND".equals(response.body().getStatusCode())) {
                                mView.sendSuccess();
                            } else {
                                mView.showError("发送验证码失败,请稍后重试");
                            }
                        } else {
                            mView.showError(response.body().getErrorCode());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError("发送验证码失败,请稍后重试");
                    }
                }));
    }

    @Override
    public void resetPwd(final String phone, final String pwd, String code) {
        mView.showLoading();
        addSubscribe(pwdResetManager.resetPwd(phone, code, pwd)
                .subscribe(new BaseSubscriber<ResetPwdResult>() {
                    @Override
                    public void onSuccess(ResetPwdResult resetPwdResult) {
                        if ("200".equals(resetPwdResult.getStatusCode())) {
                            mView.resetSuccess();
                            SPUtils.getInstance(mActivity, SPUtils.SP_UPDATE_INFO)
                                    .putBoolean(SPUtils.RESET_PWD, true)
                                    .putBoolean(SPUtils.RESET_JL_WP_PWD, true)
                                    .apply();
                        } else {
                            mView.showError(resetPwdResult.getStatusCode());
                        }
                    }
                }));
    }

}
