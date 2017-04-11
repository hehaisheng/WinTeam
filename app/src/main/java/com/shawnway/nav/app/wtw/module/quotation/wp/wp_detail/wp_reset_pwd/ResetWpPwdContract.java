package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_reset_pwd;

import com.shawnway.nav.app.wtw.base.ILoadingView;

/**
 * Created by Cicinnus on 2017/1/10.
 */

public class ResetWpPwdContract {
    public interface IResetWpPwdView extends ILoadingView{
        void sendSmsSuccess();

        void resetPwdSuccess();
    }
    public interface IResetWpPwdPresenter{
        void sendSmsCode(String phone);

        void resetPwd(String phone,String code,String newPwd);

        void loginWp(String phone,String pwd);

        void updateWpInfo(String phone,String pwd);
    }
}
