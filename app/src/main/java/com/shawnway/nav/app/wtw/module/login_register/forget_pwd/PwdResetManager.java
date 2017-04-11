package com.shawnway.nav.app.wtw.module.login_register.forget_pwd;

import com.shawnway.nav.app.wtw.bean.LoginResult;
import com.shawnway.nav.app.wtw.bean.ResetPwdResult;
import com.shawnway.nav.app.wtw.module.login_register.update_pwd.UpdateWpInfoResult;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.PasswordUtil;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/8.
 */

public class PwdResetManager {


    /**
     * 发送验证码
     * @param phone 手机号码
     * @return
     */
    public Observable<Response<ForgetCodeResult>> sendForgetCode(String phone) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cellphoneNumber", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RetrofitClient
                .getInstance()
                .api()
                .sendForgetCode(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<Response<ForgetCodeResult>>applyIoSchedulers());
    }

    /**
     * 重置赢天下密码
     * @param phone 手机号码
     * @param code 验证码
     * @param newPwd 新密码
     * @return
     */
    public Observable<ResetPwdResult> resetPwd(String phone, String code, String newPwd) {
        String pwd = "";
        try {
            pwd = PasswordUtil.aesEncrypt(newPwd, PasswordUtil._key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cellphone", phone);
            jsonObject.put("verificationCode", code);
            jsonObject.put("newPass", pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RetrofitClient
                .getInstance()
                .api()
                .resetPassword(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<ResetPwdResult>applyIoSchedulers());

    }

    /**
     * 登录
     * @param phone
     * @param newPwd
     * @return
     */
    public Observable<Response<LoginResult>> login(String phone,String newPwd){
        String pwd = "";
        try {
            pwd = PasswordUtil.aesEncrypt(newPwd, PasswordUtil._key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RetrofitClient
                .getInstance()
                .api()
                .login(phone,pwd,1)
                .compose(SchedulersCompat.<Response<LoginResult>>applyIoSchedulers());
    }

    public Observable<UpdateWpInfoResult> updateWpResetPwd(String jsonBody){
        return RetrofitClient.getInstance()
                .api()
                .updateJlWpInfo(JsonRequestBody.getInstance().convertJsonContent(jsonBody))
                .compose(SchedulersCompat.<UpdateWpInfoResult>applyIoSchedulers());
    }
}
