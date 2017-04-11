package com.shawnway.nav.app.wtw.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.orhanobut.logger.Logger;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.base.BaseWpResult;
import com.shawnway.nav.app.wtw.bean.WpInfoResult;
import com.shawnway.nav.app.wtw.module.quotation.wp.WPLoginResult;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.net.WPRetrofitClient;
import com.shawnway.nav.app.wtw.net.WpApi;
import com.shawnway.nav.app.wtw.tool.PasswordUtil;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import retrofit2.Response;

/**
 * Created by Cicinnus on 2016/12/8.
 * 重置粤国际微盘的密码
 */

public class ResetWpPwdService extends IntentService {

    private static final String NAME = "RESET_WP_PWD_SERVICE";

    private static final String PHONE = "phone";
    private static final String NEW_PWD = "newPwd";

    private String phone;
    private String newPwd;
    private String oldPwd;

    public static void start(Context context, String phone, String pwd) {
        Intent starter = new Intent(context, ResetWpPwdService.class);
        starter.putExtra(PHONE, phone);
        starter.putExtra(NEW_PWD, pwd);
        context.startService(starter);
    }

    public ResetWpPwdService() {
        super(NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        phone = intent.getStringExtra(PHONE);
        newPwd = intent.getStringExtra(NEW_PWD);
        getWpInfo();
    }

    /**
     * 获取微盘注册信息
     */
    private void getWpInfo() {
        RetrofitClient
                .getInstance()
                .api()
                .getWpInfo()
                .compose(SchedulersCompat.<WpInfoResult>applyIoSchedulers())
                .subscribe(new BaseSubscriber<WpInfoResult>() {
                    @Override
                    public void onSuccess(WpInfoResult wpInfoResult) {
                        if ("200".equals(wpInfoResult.getState())) {
                            oldPwd = wpInfoResult.getPassword();
                            String ase_pwd = wpInfoResult.getPassword();
                            String pwd = "";
                            try {
                                pwd = PasswordUtil.aesDecrypt(ase_pwd, PasswordUtil._key);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            loginWp(phone, pwd, true);
                        }
                    }
                });
    }


    /**
     * 登录微盘获取token
     *
     * @param phone 手机号
     * @param pwd   密码
     */
    private void loginWp(final String phone, String pwd, final boolean isFirst) {
        WPRetrofitClient
                .getInstance()
                .api()
                .loginWP("app", "app_secure", "password", WpApi.APPID, phone, pwd)
                .compose(SchedulersCompat.<Response<WPLoginResult>>applyIoSchedulers())
                .subscribe(new BaseSubscriber<Response<WPLoginResult>>() {
                    @Override
                    public void onSuccess(Response<WPLoginResult> response) {
                        if (response.code() == 200) {
                            SPUtils.getInstance(ResetWpPwdService.this.getApplicationContext(), SPUtils.SP_WP)
                                    .putString(SPUtils.WP_TOKEN, response.body().getAccess_token())
                                    .putString(SPUtils.WP_REFRESH_TOKEN, response.body().getRefresh_token())
                                    .apply();
                            if (isFirst) {
                                updateWpPwd(oldPwd, newPwd, response.body().getAccess_token());
                            } else {
                                SPUtils.getInstance(ResetWpPwdService.this.getApplicationContext(), SPUtils.SP_UPDATE_INFO)
                                        .putBoolean(SPUtils.RESET_PWD, false)
                                        .apply();
                                Intent intent = new Intent(ResetWpPwdService.this.getApplicationContext(), UpdateWpInfoService.class);
                                intent.putExtra(UpdateWpInfoService.MOBILE, phone);
                                intent.putExtra(UpdateWpInfoService.PWD, newPwd);
                                startService(intent);
                            }
                        }
                    }
                });
    }


    /**
     * 修改微盘密码
     *
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @param token  token
     */
    private void updateWpPwd(String oldPwd, final String newPwd, String token) {
        WPRetrofitClient
                .getInstance()
                .api()
                .updateWpPwd(oldPwd, newPwd, token)
                .compose(SchedulersCompat.<BaseWpResult>applyIoSchedulers())
                .subscribe(new BaseSubscriber<BaseWpResult>() {
                    @Override
                    public void onSuccess(BaseWpResult baseWpResult) {
                        if ("200".equals(baseWpResult.getState())) {
                            Logger.d("修改微盘密码成功");
                            //修改完密码后登录
                            loginWp(phone, newPwd, false);
                        }
                    }
                });
    }

}
