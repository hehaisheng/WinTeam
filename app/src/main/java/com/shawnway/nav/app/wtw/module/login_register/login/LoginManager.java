package com.shawnway.nav.app.wtw.module.login_register.login;

import com.shawnway.nav.app.wtw.bean.BindAccountResponse;
import com.shawnway.nav.app.wtw.bean.LoginResult;
import com.shawnway.nav.app.wtw.module.quotation.wp.WPLoginResult;
import com.shawnway.nav.app.wtw.net.JlWPRetrofitClient;
import com.shawnway.nav.app.wtw.net.JlWpApi;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.net.WPRetrofitClient;
import com.shawnway.nav.app.wtw.net.WpApi;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/9.
 */

public class LoginManager {


    /**
     * 登录赢天下
     * @param phone 手机号码
     * @param pwd 密码
     * @return
     */
    public Observable<Response<LoginResult>> login(String phone, String pwd){
        return RetrofitClient
                .getInstance()
                .api()
                .login(phone, pwd, 1)
                .compose(SchedulersCompat.<Response<LoginResult>>applyIoSchedulers());
    }

    /**
     * 登录微盘
     * @param phone 手机号
     * @param pwd 密码（明文）
     * @return
     */
    public Observable<Response<WPLoginResult>> loginWp(String phone,String pwd){
        return WPRetrofitClient
                .getInstance()
                .api()
                .loginWP("app", "app_secure", "password", WpApi.APPID, phone, pwd)
                .compose(SchedulersCompat.<Response<WPLoginResult>>applyIoSchedulers());
    }

    /**
     * 登录吉林微盘
     * @param phone 手机号
     * @param pwd 密码（明文）
     * @return
     */
    public Observable<Response<WPLoginResult>> loginJlWp(String phone,String pwd){
        return JlWPRetrofitClient
                .getInstance()
                .api()
                .loginWP("app", "app_secure", "password", JlWpApi.APPID, phone, pwd)
                .compose(SchedulersCompat.<Response<WPLoginResult>>applyIoSchedulers());
    }

    public Observable<BindAccountResponse> bindAccount(){
        return RetrofitClient
                .getInstance()
                .api()
                .bindAccount()
                .compose(SchedulersCompat.<BindAccountResponse>applyIoSchedulers());
    }
}
