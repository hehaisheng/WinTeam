package com.shawnway.nav.app.wtw.module.login_register.update_pwd;

import com.shawnway.nav.app.wtw.base.ILoadingView;

/**
 * Created by Cicinnus on 2016/12/8.
 */

public class UpdatePwdContract {
    public interface IUpdatePasswordView extends ILoadingView{
        void updateSuccess();

        void sendSuccess();
    }

    public interface IUpdatePasswordPresenter{
        void updatePwd(String oldPwd,String newPwd,String phone,String wp_token,String verifyCode);

        void updateWpPwd(String oldPwd,String newPwd,String token);

        void sendJlWpCode(String token);

        void updateJlWpPwd(String phone,String verifyCode,String newPwd);

        void update();

    }
}
