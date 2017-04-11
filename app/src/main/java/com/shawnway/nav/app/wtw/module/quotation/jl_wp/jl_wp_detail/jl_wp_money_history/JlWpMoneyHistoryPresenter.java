package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_money_history;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;

/**
 * Created by Cicinnus on 2016/12/11.
 */

public class JlWpMoneyHistoryPresenter extends BasePresenter<JlWpMoneyHistoryContract.IWpMoneyHistoryView> implements JlWpMoneyHistoryContract.IWpMoneyHistoryPresenter {

    private final JlWpMoneyHistoryManager jlWpMoneyHistoryManager;

    public JlWpMoneyHistoryPresenter(Activity activity, JlWpMoneyHistoryContract.IWpMoneyHistoryView view) {
        super(activity, view);
        jlWpMoneyHistoryManager = new JlWpMoneyHistoryManager();
    }

    @Override
    public void getWpMoneyHistory(int page, String token) {
        mView.showLoading();
        addSubscribe(jlWpMoneyHistoryManager.getWpMoneyHistory(page, token)
                .subscribe(new BaseSubscriber<JlWpMoneyHistoryBean>() {
                    @Override
                    public void onSuccess(JlWpMoneyHistoryBean jlWpMoneyHistoryBean) {
                        if ("200".equals(jlWpMoneyHistoryBean.getState())) {
                            mView.showContent();
                            mView.addWpMoneyHistory(jlWpMoneyHistoryBean.getData().getList());
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
