package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.tran.transfer;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.base.IPresenter;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.WPStateDescWrapper;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.WithDrawTranBean;

/**
 * Created by Kevin on 2016/12/12
 */

public class JlTransferTranConstract {
    public interface ITransferTranView extends ILoadingView {
        //添加用户绑定银行卡信息
        void addUserBalanceInfo(JlWithDrawBean jlWithDrawBean);

        //添加用户资金信息（可用余额）
        void addUserAccountInfo(UserAccountBean accountBean);

        //微盘提现结果
        void addWithDrawTranResult(WPStateDescWrapper wpStateDescWrapper);

        void sendSuccess();
    }

    public interface ITransferPresenter extends IPresenter {
        //获取用户绑定银行卡信息
        void getUserBalanceInfo(String access_token);

        //获取用户资金信息（可用余额）
        void getUserAccountInfo(String access_token);

        //微盘提现
        void putWithDrawTran(String access_token, WithDrawTranBean withDrawTranBean);

        void sendSmsCode(String token);
    }
}
