package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.transfer_tran;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserBalanceBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.WPStateDescWrapper;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.WithDrawTranBean;

/**
 * Created by Kevin on 2016/12/12
 */

public class TransferTranPresenter extends BasePresenter<TransferTranConstract.ITransferTranView> implements TransferTranConstract.ITransferPresenter {

    private final TransferTranManager transferTranManager;

    public TransferTranPresenter(Activity activity, TransferTranConstract.ITransferTranView view) {
        super(activity, view);
        transferTranManager = new TransferTranManager();
    }

    /**
     * 获取用户绑定银行卡信息
     *
     * @param access_token
     */
    @Override
    public void getUserBalanceInfo(String access_token) {
        addSubscribe(transferTranManager.getUserBalanceInfo(access_token)
                .subscribe(new BaseSubscriber<UserBalanceBean>() {
                    @Override
                    public void onSuccess(UserBalanceBean userBalanceBean) {
                        mView.addUserBalanceInfo(userBalanceBean);
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
        addSubscribe(transferTranManager.getUserAccountBalance(access_token)
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
        addSubscribe(transferTranManager.putWithDrawTran(access_token, withDrawTranBean)
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
}
