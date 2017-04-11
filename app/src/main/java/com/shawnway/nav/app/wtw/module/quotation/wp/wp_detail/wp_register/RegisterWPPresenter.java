package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_register;

import android.app.Activity;
import android.content.Intent;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.base.BaseWpResult;
import com.shawnway.nav.app.wtw.bean.SendSmsCodeResult;
import com.shawnway.nav.app.wtw.module.quotation.wp.WPLoginResult;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.WpDetailActivity;
import com.shawnway.nav.app.wtw.net.ErrorHanding;
import com.shawnway.nav.app.wtw.service.UpdateWpInfoService;
import com.shawnway.nav.app.wtw.tool.SPUtils;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Response;

/**
 * Created by Cicinnus on 2016/12/8.
 */

public class RegisterWPPresenter extends BasePresenter<RegisterWPContract.IRegisterWpView> implements RegisterWPContract.IRegisterWpPresenter {

    private final RegisterManager registerManager;

    public RegisterWPPresenter(Activity activity, RegisterWPContract.IRegisterWpView view) {
        super(activity, view);
        registerManager = new RegisterManager();
    }

    @Override
    public void sendSmsCode(String phone) {
        addSubscribe(registerManager.sendSmsCode(phone)
                .subscribe(new BaseSubscriber<SendSmsCodeResult>() {
                    @Override
                    public void onSuccess(SendSmsCodeResult baseResult) {
                        if ("200".equals(baseResult.getState()) && "ok".equals(baseResult.getDesc())) {
                            //发送成功
                            mView.sendSmsCodeSuccess();
                        }else if("500".equals(baseResult.getState())&&"user_already_exist".equals(baseResult.getDesc())){
                            showToast("账号已存在，请联系客服重置密码");
                        } else if ("500".equals(baseResult.getState()) && "code_already_gen".equals(baseResult.getDesc())) {
                            //验证码已发送
                            showToast("验证码已发送，请留意手机短信");
                        }else {
                            mView.showError("注册失败，请稍后重试");
                        }

                    }
                }));
    }

    @Override
    public void registerWP(final String phone, final String pwd, String code) {
        mView.showLoading();
        addSubscribe(registerManager.registerWP(phone, pwd, code)
                .subscribe(new BaseSubscriber<BaseWpResult>() {
                    @Override
                    public void onSuccess(BaseWpResult baseResult) {
                        if ("500".equals(baseResult.getState()) && "verifycode_invalid".equals(baseResult.getDesc())) {
                            mView.showError("验证码错误");
                        } else if ("200".equals(baseResult.getState()) && "ok".equals(baseResult.getDesc())) {

                            //注册成功后保存账号密码到服务器
                            loginWP(phone,pwd);
                            Intent intent = new Intent(mActivity,UpdateWpInfoService.class);
                            intent.putExtra(UpdateWpInfoService.REGISTER,true);
                            intent.putExtra(UpdateWpInfoService.MOBILE,phone);
                            intent.putExtra(UpdateWpInfoService.PWD,pwd);
                            mActivity.startService(intent);
                            mView.showContent();
                            mView.registerWPSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError("注册失败"+ ErrorHanding.handleError(e));
                    }
                }));
    }

    @Override
    public void loginWP(String phone, String pwd) {
        mView.showLoading();
        addSubscribe(registerManager.loginWP(phone, pwd)
                .subscribe(new BaseSubscriber<Response<WPLoginResult>>() {
                    @Override
                    public void onSuccess(Response<WPLoginResult> response) {
                        if (response.code() == 200) {
                            //登录成功
                            SPUtils.getInstance(mActivity, SPUtils.SP_WP)
                                    .putString(SPUtils.WP_TOKEN, response.body().getAccess_token())
                                    .putString(SPUtils.WP_REFRESH_TOKEN, response.body().getRefresh_token())
                                    .apply();
                            EventBus.getDefault().post(new WpDetailActivity.LoginWpEvent());
                            mActivity.finish();
                        }
                    }
                }));
    }

}
