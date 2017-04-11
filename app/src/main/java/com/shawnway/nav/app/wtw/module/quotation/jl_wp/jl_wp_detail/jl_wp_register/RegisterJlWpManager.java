package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_register;

import com.shawnway.nav.app.wtw.base.BaseWpResult;
import com.shawnway.nav.app.wtw.bean.SendSmsCodeResult;
import com.shawnway.nav.app.wtw.module.quotation.wp.WPLoginResult;
import com.shawnway.nav.app.wtw.net.JlWPRetrofitClient;
import com.shawnway.nav.app.wtw.net.JlWpApi;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/8.
 */

public class RegisterJlWpManager {

    /**
     * 发送验证码
     * @param phone 手机号码
     * @return
     */
    public Observable<SendSmsCodeResult> sendSmsCode(String phone,int token,String code){
        return JlWPRetrofitClient
                .getInstance()
                .api()
                .sendWPCode(phone,token,code,"default")
                .compose(SchedulersCompat.<SendSmsCodeResult>applyIoSchedulers());
    }

    /**
     * 注册微盘
     * @param phone 手机号码
     * @param pwd 密码
     * @param code 验证码
     * @return
     */
    public Observable<BaseWpResult> registerWP(String phone, String pwd, String code,String nickName){

        Map<String,String> header = new HashMap<>();
        header.put("Content-Type","application/json;charset=UTF-8");
        return JlWPRetrofitClient
                .getInstance()
                .api()
                .register(header,phone,pwd,code,nickName)
                .compose(SchedulersCompat.<BaseWpResult>applyIoSchedulers());
    }

    /**
     * 登录微盘
     * @param phone 手机号码
     * @param pwd 密码
     * @return
     */
    public Observable<Response<WPLoginResult>> loginWP(String phone,String pwd){
        return JlWPRetrofitClient
                .getInstance()
                .api()
                .loginWP("app","app_secure","password", JlWpApi.APPID,phone,pwd)
                .compose(SchedulersCompat.<Response<WPLoginResult>>applyIoSchedulers());
    }
}
