package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_register;

import com.shawnway.nav.app.wtw.base.ILoadingView;

/**
 * Created by Cicinnus on 2016/12/8.
 */

public class RegisterWPContract {
    public interface IRegisterWpView extends ILoadingView{
        void sendSmsCodeSuccess();

        void registerWPSuccess();
    }
    public interface IRegisterWpPresenter{
        void sendSmsCode(String phone);

        void registerWP(String phone,String pwd,String code);

        void loginWP(String phone,String pwd);

    }
}
