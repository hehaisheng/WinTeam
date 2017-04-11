package com.shawnway.nav.app.wtw.service;

import android.app.IntentService;
import android.content.Intent;

import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.WpInfoResult;
import com.shawnway.nav.app.wtw.module.login_register.update_pwd.UpdateWpInfoResult;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.PasswordUtil;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Cicinnus on 2016/12/9.
 * 修改吉交所微盘信息
 */

public class UpdateJlWpInfoService extends IntentService {

    private static final String NAME = "UpdateJlWpInfoService";

    public static final String REGISTER = "register";
    public static final String MOBILE = "mobile";
    public static final String PWD = "pwd";
    public static final String BANK_NAME = "bankName";
    public static final String BANK_CARD_NUM = "bankCardNum";
    public static final String BANK_CARD_HOLDER_NAME = "bankCardholderName";
    private String mobile = null;
    private String pwd = null;
    private String bankName = null;
    private String bankCardNum = null;
    private String aes_pwd = null;
    private String bankCardHolderName = null;
    private boolean register =false;
    public UpdateJlWpInfoService() {
        super(NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mobile = intent.getStringExtra(MOBILE);
        pwd = intent.getStringExtra(PWD);
        bankName = intent.getStringExtra(BANK_NAME);
        bankCardNum = intent.getStringExtra(BANK_CARD_NUM);
        bankCardHolderName = intent.getStringExtra(BANK_CARD_HOLDER_NAME);
        register = intent.getBooleanExtra(REGISTER,false);
        if (register) {
            updateJlWpInfo();
        }
        getJlWpInfo();
    }

    /**
     * 获取微盘注册信息，有注册则更新微盘密码
     */
    private void getJlWpInfo() {
        RetrofitClient
                .getInstance()
                .api()
                .getJlWpInfo()
                .compose(SchedulersCompat.<WpInfoResult>applyIoSchedulers())
                .subscribe(new BaseSubscriber<WpInfoResult>() {
                    @Override
                    public void onSuccess(WpInfoResult wpInfoResult) {
                        if ("200".equals(wpInfoResult.getState())) {
                            //有注册微盘
                            updateJlWpInfo();
                        }
                    }
                });
    }

    /**
     * 更新微盘信息
     */
    private void updateJlWpInfo() {
        try {
            if (pwd != null) {
                aes_pwd = PasswordUtil.aesEncrypt(pwd, PasswordUtil._key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        try {
            if (mobile != null) {
                jsonObject.put("mobile", mobile);
            }
            if (aes_pwd != null) {
                jsonObject.put("password", aes_pwd);
            }
            if (bankName != null) {
                jsonObject.put("bankName", bankName);
            }
            if (bankCardNum != null) {
                jsonObject.put("bankCardNum", bankCardNum);
            }
            if (bankCardHolderName != null) {
                jsonObject.put("bankCardholderName", bankCardHolderName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RetrofitClient
                .getInstance()
                .api()
                .updateJlWpInfo(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<UpdateWpInfoResult>applyIoSchedulers())
                .subscribe(new BaseSubscriber<UpdateWpInfoResult>() {
                    @Override
                    public void onSuccess(UpdateWpInfoResult updateWpInfoResult) {

                    }
                });
    }
}
