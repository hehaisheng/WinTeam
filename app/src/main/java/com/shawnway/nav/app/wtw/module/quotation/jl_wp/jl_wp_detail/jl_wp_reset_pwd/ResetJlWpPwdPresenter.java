package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_reset_pwd;

import android.app.Activity;
import android.text.TextUtils;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.base.BaseWpResult;
import com.shawnway.nav.app.wtw.module.login_register.update_pwd.UpdateWpInfoResult;
import com.shawnway.nav.app.wtw.module.quotation.wp.WPLoginResult;
import com.shawnway.nav.app.wtw.net.JlWPRetrofitClient;
import com.shawnway.nav.app.wtw.net.JlWpApi;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.PasswordUtil;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONObject;

import retrofit2.Response;

/**
 * Created by Cicinnus on 2017/1/10.
 */

public class ResetJlWpPwdPresenter extends BasePresenter<ResetJlWpPwdContract.IResetJlWpPwdView> implements ResetJlWpPwdContract.IResetJlWpPwdPresenter {


    public ResetJlWpPwdPresenter(Activity activity, ResetJlWpPwdContract.IResetJlWpPwdView view) {
        super(activity, view);

    }

    @Override
    public void sendSmsCode(int token, String phone, String code) {
        addSubscribe(JlWPRetrofitClient.getInstance().api().sendForgetPwdCode(phone, token, code, "default")
                .compose(SchedulersCompat.<BaseWpResult>applyIoSchedulers())
                .subscribe(new BaseSubscriber<BaseWpResult>() {
                    @Override
                    public void onSuccess(BaseWpResult baseWpResult) {
                        if (TextUtils.equals(baseWpResult.getState(), "200")) {
                            mView.sendSmsCodeSuccess();
                            showToast("发送成功");
                        } else {
                            String str;
                            switch (baseWpResult.getDesc()) {
                                case "code_already_gen":
                                    str = "已经生成过验证码，5 分钟内不能重复生成";
                                    break;
                                case "send_fail":
                                    str = "未知错误，请稍后再试";
                                    break;
                                default:
                                    str = "发送失败，请稍后再试";
                                    break;
                            }
                            showToast(str);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        showToast("获取验证码失败，请重试");
                    }
                }));
    }

    @Override
    public void resetPwd(final String phone, String code, final String newPwd) {
        mView.showLoading();
        addSubscribe(JlWPRetrofitClient
                .getInstance()
                .api()
                .updateJlWpPwd(phone, code, newPwd)
                .compose(SchedulersCompat.<BaseWpResult>applyIoSchedulers())
                .subscribe(new BaseSubscriber<BaseWpResult>() {
                    @Override
                    public void onSuccess(BaseWpResult baseWpResult) {
                        if ("200".equals(baseWpResult.getState())) {

                            loginJlWp(phone, newPwd);
                        } else {
                            String str;
                            switch (baseWpResult.getDesc()) {
                                case "verifycode_invalid":
                                    str = "验证码错误";
                                    break;
                                case "internal error":
                                    str = "未知错误";
                                    break;
                                default:
                                    str = "重置失败";
                                    break;
                            }
                            mView.showContent();
                            showToast(str);
                        }
                    }
                }));
    }

    @Override
    public void loginJlWp(final String phone, final String pwd) {
        addSubscribe(JlWPRetrofitClient.getInstance().api().loginWP("app", "app_secure", "password", JlWpApi.APPID, phone, pwd)
                .compose(SchedulersCompat.<Response<WPLoginResult>>applyIoSchedulers())
                .subscribe(new BaseSubscriber<Response<WPLoginResult>>() {
                    @Override
                    public void onSuccess(Response<WPLoginResult> response) {
                        if (response.code() == 200) {
                            SPUtils.getInstance(mActivity, SPUtils.SP_JL_WP)
                                    .putString(SPUtils.JL_WP_TOKEN, response.body().getAccess_token())
                                    .putString(SPUtils.JL_WP_REFRESH_TOKEN, response.body().getRefresh_token())
                                    .apply();
                            updateResetSuccess(phone,pwd);
                        }else {
                            mView.showContent();
                        }
                    }
                }));
    }

    @Override
    public void updateResetSuccess(String phone, String pwd) {
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
        addSubscribe(RetrofitClient.getInstance().api().updateJlWpInfo(
                JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<UpdateWpInfoResult>applyIoSchedulers())
                .subscribe(new BaseSubscriber<UpdateWpInfoResult>() {
                    @Override
                    public void onSuccess(UpdateWpInfoResult updateWpInfoResult) {
                        if (updateWpInfoResult.getStatusCode().equals("200")) {
                            mView.resetSuccess();
                        }else {
                            mView.showContent();
                        }
                    }
                }));
    }
}
