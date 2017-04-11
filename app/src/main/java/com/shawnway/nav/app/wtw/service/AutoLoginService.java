package com.shawnway.nav.app.wtw.service;

import android.app.IntentService;
import android.content.Intent;

import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.LoginResult;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.module.quotation.international.international_detail.UserAccountList;
import com.shawnway.nav.app.wtw.module.quotation.wp.WPLoginResult;
import com.shawnway.nav.app.wtw.net.JlWPRetrofitClient;
import com.shawnway.nav.app.wtw.net.JlWpApi;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.net.RetryWhenProcess;
import com.shawnway.nav.app.wtw.net.WPRetrofitClient;
import com.shawnway.nav.app.wtw.net.WpApi;
import com.shawnway.nav.app.wtw.tool.PasswordUtil;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.ToastUtil;

import retrofit2.Response;
import rx.functions.Action1;

/**
 * Created by Cicinnus on 2016/12/1.
 * 自动登录服务
 */

public class AutoLoginService extends IntentService {
    private static final String TAG = "AutoLoginService";


    private boolean isFirst;
    private String phone;
    private String truePwd;

    public AutoLoginService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        phone = SPUtils.getInstance(getApplicationContext(), SPUtils.SP_ACCOUNT).getString(SPUtils.PHONE, "");
        truePwd = SPUtils.getInstance(getApplicationContext(), SPUtils.SP_ACCOUNT).getString(SPUtils.PWD, "");
        if (!phone.equals("")) {
            autoLogin(truePwd, phone);
//            autoLoginWP(truePwd, phone);
            autoLoginJlWp(truePwd, phone);
        }else {
            stopSelf();
        }
    }


    /**
     * 登录赢天下
     *
     * @param truePwd 密码
     * @param phone   手机号
     */
    private void autoLogin(final String truePwd, final String phone) {
        RetrofitClient
                .getInstance()
                .api()
                .login(phone, truePwd, 1)
                .compose(SchedulersCompat.<Response<LoginResult>>applyIoSchedulers())
                .subscribe(new BaseSubscriber<Response<LoginResult>>() {
                    @Override
                    public void onSuccess(Response<LoginResult> loginResultResponse) {

                        if (loginResultResponse.code() == 200) {
                            getUserInfo();
                        }
                    }
                });
    }

    private void getUserInfo() {
        RetrofitClient
                .getInstance()
                .api()
                .getUserAccountList()
                .compose(SchedulersCompat.<Response<UserAccountList>>applyIoSchedulers())
                .subscribe(new Action1<Response<UserAccountList>>() {
                    @Override
                    public void call(Response<UserAccountList> userAccountListResponse) {
                        if (userAccountListResponse.code() == 401) {
                            if (isFirst) {
                                autoLogin(truePwd, phone);
                                isFirst = false;
                            } else {
                                SPUtils.getInstance(AutoLoginService.this.getApplicationContext(), SPUtils.SP_ACCOUNT)
                                        .deleteAllSPData();
                                ToastUtil.showShort(AutoLoginService.this.getApplicationContext(), "登录已过期,请重新登录");
                                LoginActivity.getInstance(AutoLoginService.this.getApplicationContext());
                            }
                        }

                    }
                });
    }

    /**
     * 登录粤国际微盘
     * @param truePwd 密码
     * @param phone 手机号
     */
    private void autoLoginWP(String truePwd, String phone) {
        String password = "";
        try {
            password = PasswordUtil.aesDecrypt(truePwd, PasswordUtil._key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        WPRetrofitClient
                .getInstance()
                .api()
                .loginWP("app", "app_secure", "password", WpApi.APPID, phone, password)
                .compose(SchedulersCompat.<Response<WPLoginResult>>applyIoSchedulers())
                .retryWhen(new RetryWhenProcess(5))
                .subscribe(new BaseSubscriber<Response<WPLoginResult>>() {
                    @Override
                    public void onSuccess(Response<WPLoginResult> response) {
                        if (response.code() == 200) {
                            SPUtils.getInstance(AutoLoginService.this.getApplicationContext(), SPUtils.SP_WP)
                                    .putString(SPUtils.WP_TOKEN, response.body().getAccess_token())
                                    .putString(SPUtils.WP_REFRESH_TOKEN, response.body().getRefresh_token())
                                    .apply();
                        }
                    }
                });
    }

    /**
     * 登录吉林微盘
     * @param truePwd
     * @param phone
     */
    private void autoLoginJlWp(String truePwd,String phone){
        String password = "";
        try {
            password = PasswordUtil.aesDecrypt(truePwd, PasswordUtil._key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JlWPRetrofitClient
                .getInstance()
                .api()
                .loginWP("app", "app_secure", "password", JlWpApi.APPID, phone, password)
                .compose(SchedulersCompat.<Response<WPLoginResult>>applyIoSchedulers())
                .retryWhen(new RetryWhenProcess(5))
                .subscribe(new BaseSubscriber<Response<WPLoginResult>>() {
                    @Override
                    public void onSuccess(Response<WPLoginResult> response) {
                        if (response.code() == 200) {
                            SPUtils.getInstance(AutoLoginService.this.getApplicationContext(), SPUtils.SP_JL_WP)
                                    .putString(SPUtils.JL_WP_TOKEN, response.body().getAccess_token())
                                    .putString(SPUtils.JL_WP_REFRESH_TOKEN, response.body().getRefresh_token())
                                    .apply();
                        }
                    }
                });
    }

}
