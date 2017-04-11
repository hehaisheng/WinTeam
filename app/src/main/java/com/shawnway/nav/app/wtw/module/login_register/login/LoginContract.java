package com.shawnway.nav.app.wtw.module.login_register.login;

import com.shawnway.nav.app.wtw.base.ILoadingView;

/**
 * Created by Cicinnus on 2016/12/9.
 */

public class LoginContract {
    public interface ILoginView extends ILoadingView{
        void loginSuccess();
    }

    public interface ILoginPresenter{
        void login(String phone,String pwd);

        void loginWp(String phone,String pwd);

        void loginJlWp(String phone,String pwd);

        void bindAccount();

    }
}
