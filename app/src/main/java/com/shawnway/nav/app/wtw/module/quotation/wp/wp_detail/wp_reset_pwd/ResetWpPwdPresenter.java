package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_reset_pwd;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.base.BaseWpResult;
import com.shawnway.nav.app.wtw.module.login_register.update_pwd.UpdateWpInfoResult;
import com.shawnway.nav.app.wtw.module.quotation.wp.WPLoginResult;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.net.WPRetrofitClient;
import com.shawnway.nav.app.wtw.net.WpApi;
import com.shawnway.nav.app.wtw.tool.PasswordUtil;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONObject;

import retrofit2.Response;

/**
 * Created by Cicinnus on 2017/1/10.
 */

public class ResetWpPwdPresenter extends BasePresenter<ResetWpPwdContract.IResetWpPwdView> implements ResetWpPwdContract.IResetWpPwdPresenter {

    public ResetWpPwdPresenter(Activity activity, ResetWpPwdContract.IResetWpPwdView view) {
        super(activity, view);
    }

    @Override
    public void sendSmsCode(String phone) {
        addSubscribe(WPRetrofitClient.getInstance().api().sendResetPwdCode(phone).compose(SchedulersCompat.<BaseWpResult>applyIoSchedulers())
                .subscribe(new BaseSubscriber<BaseWpResult>() {
                    @Override
                    public void onSuccess(BaseWpResult baseWpResult) {
                        if ("200".equals(baseWpResult.getState())) {
                            mView.sendSmsSuccess();
                        } else {
                            mView.showError("获取验证码失败，请稍后再试");
                        }
                    }
                }));
    }

    @Override
    public void resetPwd(final String phone, String code, final String newPwd) {
        mView.showLoading();
        addSubscribe(WPRetrofitClient.getInstance().api().resetWpPwd(phone, code, newPwd)
                .compose(SchedulersCompat.<BaseWpResult>applyIoSchedulers())
                .subscribe(new BaseSubscriber<BaseWpResult>() {
                    @Override
                    public void onSuccess(BaseWpResult baseWpResult) {
                        if ("200".equals(baseWpResult.getState())) {
                            loginWp(phone,newPwd);
                        } else {
                            mView.showError("重置失败，请稍后再试");
                        }
                    }
                }));
    }

    @Override
    public void loginWp(final String phone, final String pwd) {

        addSubscribe(WPRetrofitClient.getInstance().api().loginWP("app", "app_secure", "password", WpApi.APPID, phone, pwd)
                .compose(SchedulersCompat.<Response<WPLoginResult>>applyIoSchedulers())
                .subscribe(new BaseSubscriber<Response<WPLoginResult>>() {
                    @Override
                    public void onSuccess(Response<WPLoginResult> response) {
                        if (response.code()==200) {
                            updateWpInfo(phone, pwd);
                        }
                    }
                }));
    }

    @Override
    public void updateWpInfo(String phone,String pwd) {
        String aes_pwd;
        JSONObject jsonObject = new JSONObject();

        try {
            aes_pwd = PasswordUtil.aesEncrypt(pwd, PasswordUtil._key);
            jsonObject.put("updated",false);
            jsonObject.put("mobile", phone);
            jsonObject.put("password", aes_pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addSubscribe(RetrofitClient.getInstance().api().updateWpInfo(
                JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<UpdateWpInfoResult>applyIoSchedulers())
                .subscribe(new BaseSubscriber<UpdateWpInfoResult>() {
                    @Override
                    public void onSuccess(UpdateWpInfoResult updateWpInfoResult) {
                        if (updateWpInfoResult.getStatusCode().equals("200")) {
                            mView.resetPwdSuccess();
                        }
                    }
                }));

    }
}
