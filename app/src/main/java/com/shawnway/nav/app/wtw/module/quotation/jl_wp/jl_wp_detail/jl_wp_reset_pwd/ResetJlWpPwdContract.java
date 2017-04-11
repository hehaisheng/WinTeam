package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_reset_pwd;

import com.shawnway.nav.app.wtw.base.ILoadingView;

/**
 * Created by Cicinnus on 2017/1/10.
 */

public class ResetJlWpPwdContract {
    public interface IResetJlWpPwdView extends ILoadingView{
        void resetSuccess();

        void sendSmsCodeSuccess();

        void loginSuccess();
    }
    public interface IResetJlWpPwdPresenter{
        void sendSmsCode(int token,String phone,String code);

        void resetPwd(String phone,String code,String newPwd);

        void loginJlWp(String phone,String pwd);

        void updateResetSuccess(String phone,String pwd);
    }
}
