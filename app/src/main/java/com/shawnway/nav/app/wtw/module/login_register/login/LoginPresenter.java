package com.shawnway.nav.app.wtw.module.login_register.login;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.BindAccountResponse;
import com.shawnway.nav.app.wtw.bean.LoginResult;
import com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.JlWpDetailActivity;
import com.shawnway.nav.app.wtw.module.quotation.wp.WPLoginResult;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.WpDetailActivity;
import com.shawnway.nav.app.wtw.net.ErrorHanding;
import com.shawnway.nav.app.wtw.tool.SPUtils;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Response;

/**
 * Created by Cicinnus on 2016/12/9.
 */

public class LoginPresenter extends BasePresenter<LoginContract.ILoginView> implements LoginContract.ILoginPresenter {


    private final LoginManager loginManager;

    public LoginPresenter(Activity activity, LoginContract.ILoginView view) {
        super(activity, view);
        loginManager = new LoginManager();
    }

    @Override
    public void login(final String phone, final String pwd) {
        mView.showLoading();
        addSubscribe(loginManager.login(phone, pwd)
                .subscribe(
                        new BaseSubscriber<Response<LoginResult>>() {
                            @Override
                            public void onSuccess(Response<LoginResult> response) {
                                if (response.code() == 200) {
                                    if (response.body().getStatusCode().equals("ACCESS_GRANTED")) {
                                        SPUtils.getInstance(mActivity, SPUtils.SP_ACCOUNT)
                                                .putString(SPUtils.PHONE, phone)
                                                .putString(SPUtils.PWD, pwd)
                                                .apply();
                                        bindAccount();
                                        mView.loginSuccess();
                                    }

                                } else if (response.code() == 401) {
                                    mView.showError("账号密码错误");
                                } else {
                                    mView.showError("登录出错，请重试"+response.code());
                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mView.showError(ErrorHanding.handleError(e) + "请重试");
                            }
                        }
                ));
    }

    @Override
    public void loginWp(String phone, String pwd) {

        addSubscribe(loginManager.loginWp(phone, pwd)
                .subscribe(new BaseSubscriber<Response<WPLoginResult>>() {
                    @Override
                    public void onSuccess(Response<WPLoginResult> response) {
                        if (response.code() == 200) {

                            SPUtils.getInstance(mActivity, SPUtils.SP_WP)
                                    .putString(SPUtils.WP_TOKEN, response.body().getAccess_token())
                                    .putString(SPUtils.WP_REFRESH_TOKEN, response.body().getRefresh_token())
                                    .apply();
                            EventBus.getDefault().post(new WpDetailActivity.LoginWpEvent());
                        }
                    }

                }));


    }

    @Override
    public void loginJlWp(String phone, String pwd) {

        addSubscribe(loginManager.loginJlWp(phone, pwd)
                .subscribe(new BaseSubscriber<Response<WPLoginResult>>() {
                    @Override
                    public void onSuccess(Response<WPLoginResult> response) {
                        if (response.code() == 200) {
                            SPUtils.getInstance(mActivity, SPUtils.SP_JL_WP)
                                    .putString(SPUtils.JL_WP_TOKEN, response.body().getAccess_token())
                                    .putString(SPUtils.JL_WP_REFRESH_TOKEN, response.body().getRefresh_token())
                                    .apply();
                            EventBus.getDefault().post(new JlWpDetailActivity.LoginJlWpEvent());
                        }
                    }
                }));

    }


    @Override
    public void bindAccount() {
        addSubscribe(loginManager.bindAccount().subscribe(new BaseSubscriber<BindAccountResponse>() {
            @Override
            public void onSuccess(BindAccountResponse response) {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showError("无法自动绑定国际期货账号，请稍后重试");

            }
        }));
    }
}
