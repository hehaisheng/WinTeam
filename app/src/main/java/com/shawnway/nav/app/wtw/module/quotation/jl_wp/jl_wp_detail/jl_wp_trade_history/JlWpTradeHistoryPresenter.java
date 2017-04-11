package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_trade_history;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;

/**
 * Created by Cicinnus on 2016/12/12.
 */

public class JlWpTradeHistoryPresenter extends BasePresenter<JlWpTradeHistoryContract.IWpTradeHistoryView> implements JlWpTradeHistoryContract.IWpTradeHistoryPresenter {

    private final JlWpTradeHistoryManager jlWpTradeHistoryManager;
    private boolean tradeHistory = false;
    private boolean tradeTotal = false;

    public JlWpTradeHistoryPresenter(Activity activity, JlWpTradeHistoryContract.IWpTradeHistoryView view) {
        super(activity, view);
        jlWpTradeHistoryManager = new JlWpTradeHistoryManager();
    }

    @Override
    public void getTradeHistory(int pageNum, String token) {
        mView.showLoading();
        addSubscribe(jlWpTradeHistoryManager.getWoTradeHistory(pageNum, token)
                .subscribe(new BaseSubscriber<JlWpTradeHistoryBean>() {
                    @Override
                    public void onSuccess(JlWpTradeHistoryBean jlWpTradeHistoryBean) {
                        mView.showContent();
                        tradeHistory = true;
//                        loadAllSuccess();
                        mView.addTradeHistory(jlWpTradeHistoryBean.getData().getList());

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
//        addSubscribe(jlWpTradeHistoryManager.getWpTotal(token)
//                .subscribe(new BaseSubscriber<JlWpTradeTotalBean>() {
//                    @Override
//                    public void onSuccess(JlWpTradeTotalBean jlWpTradeTotalBean) {
//                        tradeTotal = true;
//                        loadAllSuccess();
//                        mView.addTradeTotal(jlWpTradeTotalBean.getData());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
////                        mView.showError("获取交易统计失败");
//                    }
//                }));
    }

    @Override
    public void loadAllSuccess() {
//        if (tradeHistory && tradeTotal) {
//            mView.showContent();
//        }
    }
}
