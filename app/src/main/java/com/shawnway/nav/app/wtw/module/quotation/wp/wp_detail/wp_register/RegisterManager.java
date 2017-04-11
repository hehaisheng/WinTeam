package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_register;

import com.shawnway.nav.app.wtw.base.BaseWpResult;
import com.shawnway.nav.app.wtw.bean.SendSmsCodeResult;
import com.shawnway.nav.app.wtw.module.quotation.wp.WPLoginResult;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.WPRetrofitClient;
import com.shawnway.nav.app.wtw.net.WpApi;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/8.
 */

public class RegisterManager {

    /**
     * 发送验证码
     * @param phone 手机号码
     * @return
     */
    public Observable<SendSmsCodeResult> sendSmsCode(String phone){
        return WPRetrofitClient
                .getInstance()
                .api()
                .sendWPCode(phone)
                .compose(SchedulersCompat.<SendSmsCodeResult>applyIoSchedulers());
    }

    /**
     * 注册微盘
     * @param phone 手机号码
     * @param pwd 密码
     * @param code 验证码
     * @return
     */
    public Observable<BaseWpResult> registerWP(String phone, String pwd, String code){
        JSONObject params = new JSONObject();
        try {
            params.put("nickname", "");
            params.put("firstName", "");
            params.put("lastName", "");
            params.put("sex", "");
            params.put("country", "中国");
            params.put("province", "");
            params.put("city", "");
            params.put("age", "");
            params.put("address", "");
            params.put("userPhoto", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String,String> header = new HashMap<>();
        header.put("Content-Type","application/json;charset=UTF-8");
        return WPRetrofitClient
                .getInstance()
                .api()
                .register(header,phone,pwd,code, JsonRequestBody.getInstance().convertJsonContent(params.toString()))
                .compose(SchedulersCompat.<BaseWpResult>applyIoSchedulers());
    }

    /**
     * 登录微盘
     * @param phone 手机号码
     * @param pwd 密码
     * @return
     */
    public Observable<Response<WPLoginResult>> loginWP(String phone,String pwd){
        return WPRetrofitClient
                .getInstance()
                .api()
                .loginWP("app","app_secure","password", WpApi.APPID,phone,pwd)
                .compose(SchedulersCompat.<Response<WPLoginResult>>applyIoSchedulers());
    }
}
