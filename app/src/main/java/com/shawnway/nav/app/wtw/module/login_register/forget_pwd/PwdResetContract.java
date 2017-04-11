package com.shawnway.nav.app.wtw.module.login_register.forget_pwd;

import com.shawnway.nav.app.wtw.base.ILoadingView;

/**
 * Created by Cicinnus on 2016/12/8.
 */

public class PwdResetContract {
    public interface IPwdResetView extends ILoadingView{
        void resetSuccess();

        void sendSuccess();

    }

    public interface IPwdResetPresemter{

        void sendSmsCode(String phone);

        void resetPwd(String phone,String pwd,String code);


    }
}
