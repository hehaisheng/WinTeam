package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_money_history;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;

/**
 * Created by Cicinnus on 2016/12/11.
 */

public class WpMoneyHistoryPresenter extends BasePresenter<WpMoneyHistoryContract.IWpMoneyHistoryView> implements WpMoneyHistoryContract.IWpMoneyHistoryPresenter {

    private final WpMoneyHistoryManager wpMoneyHistoryManager;

    public WpMoneyHistoryPresenter(Activity activity, WpMoneyHistoryContract.IWpMoneyHistoryView view) {
        super(activity, view);
        wpMoneyHistoryManager = new WpMoneyHistoryManager();
    }

    @Override
    public void getWpMoneyHistory(int page, String token) {
        mView.showLoading();
        addSubscribe(wpMoneyHistoryManager.getWpMoneyHistory(page, token)
                .subscribe(new BaseSubscriber<WpMoneyHistoryBean>() {
                    @Override
                    public void onSuccess(WpMoneyHistoryBean wpMoneyHistoryBean) {
                        if ("200".equals(wpMoneyHistoryBean.getState())) {
                            mView.showContent();
                            mView.addWpMoneyHistory(wpMoneyHistoryBean.getData().getList());
                        }else {
                            mView.showError("获取收支明细失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError("获取失败，请重试");
                    }
                }));
    }
}
