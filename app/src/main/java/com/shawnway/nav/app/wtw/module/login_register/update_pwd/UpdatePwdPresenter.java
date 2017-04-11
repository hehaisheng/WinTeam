package com.shawnway.nav.app.wtw.module.login_register.update_pwd;

import android.app.Activity;
import android.text.TextUtils;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.base.BaseWpResult;
import com.shawnway.nav.app.wtw.net.ErrorHanding;
import com.shawnway.nav.app.wtw.tool.SPUtils;

/**
 * Created by Cicinnus on 2016/12/8.
 */

public class UpdatePwdPresenter extends BasePresenter<UpdatePwdContract.IUpdatePasswordView> implements UpdatePwdContract.IUpdatePasswordPresenter {

    private final UpdatePwdManager updatePwdManager;

    private boolean update1 = false;//期货
    private boolean update2 = false;//粤国际微盘
    private boolean update3 = false;//吉林国际微盘


    public UpdatePwdPresenter(Activity activity, UpdatePwdContract.IUpdatePasswordView view) {
        super(activity, view);
        updatePwdManager = new UpdatePwdManager();
    }

    /**
     * 修改期货密码
     *
     * @param oldPwd   旧密码
     * @param newPwd   新密码
     * @param phone    手机号码
     * @param wp_token 粤国际token
     */
    @Override
    public void updatePwd(final String oldPwd, final String newPwd, final String phone, final String wp_token, final String verifyCode) {
        mView.showLoading();
        addSubscribe(updatePwdManager.updatePwd(phone, oldPwd, newPwd)
                .subscribe(new BaseSubscriber<UpdatePwdResult>() {
                    @Override
                    public void onSuccess(UpdatePwdResult updatePwdResult) {
                        if ("200".equals(updatePwdResult.getStatusCode())) {
                            update1 = true;
                            update();
                            if (!wp_token.equals("")) {
                                updateWpPwd(oldPwd, newPwd, wp_token);
                            } else {
                                update2 = true;
                                update();
                            }
                            if (!verifyCode.equals("")) {
                                updateJlWpPwd(phone, verifyCode, newPwd);
                            } else {
                                update3 = true;
                                update();
                            }
                        } else {
                            mView.showError(updatePwdResult.getDesc());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError(ErrorHanding.handleError(e) + "，请重试");
                    }
                }));
    }

    @Override
    public void updateWpPwd(String oldPwd, String newPwd, String token) {
        addSubscribe(updatePwdManager.updateWpPwd(oldPwd, newPwd, token)
                .subscribe(new BaseSubscriber<BaseWpResult>() {
                    @Override
                    public void onSuccess(BaseWpResult baseWpResult) {
                        if ("200".equals(baseWpResult.getState())) {
                            update2 = true;
                            SPUtils.getInstance(mActivity, SPUtils.SP_UPDATE_INFO)
                                    .putBoolean(SPUtils.UPDATE_PWD, true)
                                    .apply();
                            update();
                        }
                    }
                }));
    }

    @Override
    public void sendJlWpCode(String token) {
        addSubscribe(updatePwdManager.sendVerifyCode(token)
                .subscribe(new BaseSubscriber<BaseWpResult>() {
                               @Override
                               public void onSuccess(BaseWpResult baseWpResult) {
                                   if (TextUtils.equals(baseWpResult.getState(), "200")) {
                                       mView.sendSuccess();
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
                                   showToast("获取短信验证码失败，请重试");
                               }
                           }
                ));
    }

    @Override
    public void updateJlWpPwd(String phone, String verifyCode, String newPwd) {
        addSubscribe(updatePwdManager.updateJlWpPwd(phone, verifyCode, newPwd)
                .subscribe(new BaseSubscriber<BaseWpResult>() {
                    @Override
                    public void onSuccess(BaseWpResult baseWpResult) {
                        if (TextUtils.equals(baseWpResult.getState(), "200")) {
                            update3 = true;
                            update();
                        }else {
                            String str;
                            switch (baseWpResult.getDesc()) {
                                case "verifycode_invalid":
                                    str = "验证码错误";
                                    break;
                                case "invalid_parameters":
                                    str = "非法参数";
                                    break;
                                default:
                                    str = "修改失败，请重试";
                                    break;
                            }
                            showToast(str);
                            update3 = true;
                            update();
                        }

                    }
                }));
    }

    @Override
    public void update() {
        if (update1 && update2 && update3) {
            mView.updateSuccess();
        }
    }

}
