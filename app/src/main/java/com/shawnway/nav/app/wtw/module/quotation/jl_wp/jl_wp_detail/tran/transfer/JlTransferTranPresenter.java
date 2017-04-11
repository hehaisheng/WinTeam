package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.tran.transfer;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.base.BaseWpResult;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.WPStateDescWrapper;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.WithDrawTranBean;

/**
 * Created by Kevin on 2016/12/12
 */

public class JlTransferTranPresenter extends BasePresenter<JlTransferTranConstract.ITransferTranView> implements JlTransferTranConstract.ITransferPresenter {

    private final JlTransferTranManager jlTransferTranManager;

    public JlTransferTranPresenter(Activity activity, JlTransferTranConstract.ITransferTranView view) {
        super(activity, view);
        jlTransferTranManager = new JlTransferTranManager();
    }

    /**
     * 获取用户绑定银行卡信息
     *
     * @param access_token
     */
    @Override
    public void getUserBalanceInfo(String access_token) {
        addSubscribe(jlTransferTranManager.getUserBalanceInfo(access_token)
                .subscribe(new BaseSubscriber<JlWithDrawBean>() {
                    @Override
                    public void onSuccess(JlWithDrawBean jlWithDrawBean) {
                        mView.addUserBalanceInfo(jlWithDrawBean);
                    }
                }));
    }

    /**
     * 获取用户资金信息（账号余额）
     *
     * @param access_token
     */
    @Override
    public void getUserAccountInfo(String access_token) {
        mView.showLoading();
        addSubscribe(jlTransferTranManager.getUserAccountBalance(access_token)
                .subscribe(new BaseSubscriber<UserAccountBean>() {
                    @Override
                    public void onSuccess(UserAccountBean userAccountBean) {
                        mView.addUserAccountInfo(userAccountBean);
                        mView.showContent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError(e.getMessage());
                    }
                }));
    }

    /**
     * 微盘提现
     *
     * @param access_token
     * @param withDrawTranBean
     */
    @Override
    public void putWithDrawTran(String access_token, WithDrawTranBean withDrawTranBean) {
        mView.showLoading();
        addSubscribe(jlTransferTranManager.putWithDrawTran(access_token, withDrawTranBean)
                .subscribe(new BaseSubscriber<WPStateDescWrapper>() {
                    @Override
                    public void onSuccess(WPStateDescWrapper wpStateDescWrapper) {
                        mView.addWithDrawTranResult(wpStateDescWrapper);
                        mView.showContent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError(e.getMessage());
                    }
                }));
    }

    @Override
    public void sendSmsCode(String token) {
        mView.showLoading();
        addSubscribe(jlTransferTranManager.sendSmsCode(token).subscribe(new BaseSubscriber<BaseWpResult>() {
            @Override
            public void onSuccess(BaseWpResult baseWpResult) {
                switch (baseWpResult.getDesc()) {
                    case "OK":
                        mView.sendSuccess();
                        break;
                    case "send_fail":
                        mView.showError("发送失败");
                        break;
                    case "can_not_send_again":
                        mView.showError("120秒内不能重复发送");
                        break;
                }
            }
        }));
    }
}
