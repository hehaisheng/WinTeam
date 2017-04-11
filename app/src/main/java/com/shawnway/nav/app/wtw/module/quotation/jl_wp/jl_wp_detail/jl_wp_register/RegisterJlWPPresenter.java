package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_register;

import android.app.Activity;
import android.content.Intent;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.base.BaseWpResult;
import com.shawnway.nav.app.wtw.bean.SendSmsCodeResult;
import com.shawnway.nav.app.wtw.module.quotation.wp.WPLoginResult;
import com.shawnway.nav.app.wtw.net.ErrorHanding;
import com.shawnway.nav.app.wtw.service.UpdateJlWpInfoService;
import com.shawnway.nav.app.wtw.tool.SPUtils;

import retrofit2.Response;

/**
 * Created by Cicinnus on 2016/12/8.
 */

public class RegisterJlWPPresenter extends BasePresenter<RegisterJlWPContract.IRegisterWpView> implements RegisterJlWPContract.IRegisterWpPresenter {

    private final RegisterJlWpManager registerJlWpManager;

    public RegisterJlWPPresenter(Activity activity, RegisterJlWPContract.IRegisterWpView view) {
        super(activity, view);
        registerJlWpManager = new RegisterJlWpManager();
    }

    @Override
    public void sendSmsCode(String phone,int token,String code) {
        addSubscribe(registerJlWpManager.sendSmsCode(phone,token,code)
                .subscribe(new BaseSubscriber<SendSmsCodeResult>() {
                    @Override
                    public void onSuccess(SendSmsCodeResult baseResult) {
                        if ("200".equals(baseResult.getState())) {
                            //发送成功
                            mView.sendSmsCodeSuccess();
                        }else if("500".equals(baseResult.getState())&&"user_already_exist".equals(baseResult.getDesc())){
                            showToast("账号已存在");
                        } else if ("500".equals(baseResult.getState()) && "code_already_gen".equals(baseResult.getDesc())) {
                            //验证码已发送
                            showToast("验证码已发送，请留意手机短信");
                        } else if("500".equals(baseResult.getState())&&"vcode_error".equals(baseResult.getDesc())){
                            showToast("图形验证码错误");
                        }

                    }
                }));
    }

    @Override
    public void registerWP(final String phone, final String pwd, String code,String nickName) {
        mView.showLoading();
        addSubscribe(registerJlWpManager.registerWP(phone, pwd, code,nickName)
                .subscribe(new BaseSubscriber<BaseWpResult>() {
                    @Override
                    public void onSuccess(BaseWpResult baseResult) {
                        if ("500".equals(baseResult.getState()) && "verifycode_invalid".equals(baseResult.getDesc())) {
                            mView.showContent();
                            mView.showError("验证码错误");
                        } else if ("200".equals(baseResult.getState())) {

                            //注册成功后登录，保存账号密码到服务器
                            loginWP(phone,pwd);
                            Intent intent = new Intent(mActivity,UpdateJlWpInfoService.class);
                            intent.putExtra(UpdateJlWpInfoService.REGISTER,true);
                            intent.putExtra(UpdateJlWpInfoService.MOBILE,phone);
                            intent.putExtra(UpdateJlWpInfoService.PWD,pwd);
                            mActivity.startService(intent);
                            mView.showContent();
                            mView.registerWPSuccess();
                        }else if("invalid_parameters".equals(baseResult.getDesc())){
                            mView.showError("无效验证码");
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
        addSubscribe(registerJlWpManager.loginWP(phone, pwd)
                .subscribe(new BaseSubscriber<Response<WPLoginResult>>() {
                    @Override
                    public void onSuccess(Response<WPLoginResult> response) {
                        if (response.code() == 200) {
                            //登录成功
                            SPUtils.getInstance(mActivity, SPUtils.SP_JL_WP)
                                    .putString(SPUtils.JL_WP_TOKEN, response.body().getAccess_token())
                                    .putString(SPUtils.JL_WP_REFRESH_TOKEN, response.body().getRefresh_token())
                                    .apply();
                            mActivity.finish();
                        }
                    }
                }));
    }

}
