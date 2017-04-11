package com.shawnway.nav.app.wtw.module.login_register.update_pwd;

import com.shawnway.nav.app.wtw.base.BaseWpResult;
import com.shawnway.nav.app.wtw.net.JlWPRetrofitClient;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.net.WPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.PasswordUtil;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/8.
 */

public class UpdatePwdManager {


    /**
     * 修改赢天下密码
     * @param phone 手机号
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return
     */
    public Observable<UpdatePwdResult> updatePwd(String phone, String oldPwd, String newPwd) {
        String aes_oldPwd = "";
        String aes_newPwd = "";
        try {
            aes_oldPwd = PasswordUtil.aesEncrypt(oldPwd, PasswordUtil._key);
            aes_newPwd = PasswordUtil.aesEncrypt(newPwd, PasswordUtil._key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cellphone", phone);
            jsonObject.put("oldPass", aes_oldPwd);
            jsonObject.put("newPass", aes_newPwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }

       return  RetrofitClient
               .getInstance().api()
               .updatePwd(JsonRequestBody.getInstance()
               .convertJsonContent(jsonObject.toString()))
               .compose(SchedulersCompat.<UpdatePwdResult>applyIoSchedulers());


    }

    /**
     * 修改微盘密码
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @param token token
     * @return
     */
    public Observable<BaseWpResult> updateWpPwd (String oldPwd,String newPwd,String token){
        return WPRetrofitClient.getInstance().api().updateWpPwd(oldPwd, newPwd, token)
                .compose(SchedulersCompat.<BaseWpResult>applyIoSchedulers());
    }


    public Observable<BaseWpResult> sendVerifyCode(String token){
        return JlWPRetrofitClient
                .getInstance()
                .api()
                .sendResetPwdVerifyCode(token,"default")
                .compose(SchedulersCompat.<BaseWpResult>applyIoSchedulers());
    }

    /**
     * 修改吉林微盤密碼
     * @param phone 手机号码
     * @param verifyCode 验证码
     * @param newPwd 新密码
     * @return
     */
    public Observable<BaseWpResult> updateJlWpPwd(String phone,String verifyCode,String newPwd){
        return JlWPRetrofitClient.getInstance().api().updateJlWpPwd(phone,verifyCode,newPwd)
                .compose(SchedulersCompat.<BaseWpResult>applyIoSchedulers());
    }


}
