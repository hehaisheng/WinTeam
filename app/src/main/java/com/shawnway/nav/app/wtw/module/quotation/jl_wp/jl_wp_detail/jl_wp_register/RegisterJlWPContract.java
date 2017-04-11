package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_register;

import com.shawnway.nav.app.wtw.base.ILoadingView;

/**
 * Created by Cicinnus on 2016/12/8.
 */

public class RegisterJlWPContract {
    public interface IRegisterWpView extends ILoadingView{
        void sendSmsCodeSuccess();

        void registerWPSuccess();
    }
    public interface IRegisterWpPresenter{
        void sendSmsCode(String phone,int token,String code);

        void registerWP(String phone, String pwd, String code,String nickName);

        void loginWP(String phone, String pwd);

    }
}
