package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_trade_history;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;

/**
 * Created by Cicinnus on 2016/12/12.
 */

public class WpTradeHistoryPresenter extends BasePresenter<WpTradeHistoryContract.IWpTradeHistoryView> implements WpTradeHistoryContract.IWpTradeHistoryPresenter {

    private final WpTradeHistoryManager wpTradeHistoryManager;
    private boolean tradeHistory = false;
    private boolean tradeTotal = false;

    public WpTradeHistoryPresenter(Activity activity, WpTradeHistoryContract.IWpTradeHistoryView view) {
        super(activity, view);
        wpTradeHistoryManager = new WpTradeHistoryManager();
    }

    @Override
    public void getTradeHistory(int pageNum, String token) {
        mView.showLoading();
        addSubscribe(wpTradeHistoryManager.getWoTradeHistory(pageNum, token)
                .subscribe(new BaseSubscriber<WpTradeHistoryBean>() {
                    @Override
                    public void onSuccess(WpTradeHistoryBean wpTradeHistoryBean) {
                        tradeHistory = true;
                        loadAllSuccess();
                        mView.addTradeHistory(wpTradeHistoryBean.getData().getList());

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError("获取失败，请稍后重试");
                    }
                }));
    }

    @Override
    public void getTradeTotal(String token) {
        addSubscribe(wpTradeHistoryManager.getWpTotal(token)
                .subscribe(new BaseSubscriber<WpTradeTotalBean>() {
                    @Override
                    public void onSuccess(WpTradeTotalBean wpTradeTotalBean) {
                        tradeTotal = true;
                        loadAllSuccess();
                        mView.addTradeTotal(wpTradeTotalBean.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError("获取交易统计失败");
                    }
                }));
    }

    @Override
    public void loadAllSuccess() {
        if (tradeHistory && tradeTotal) {
            mView.showContent();
        }
    }
}
