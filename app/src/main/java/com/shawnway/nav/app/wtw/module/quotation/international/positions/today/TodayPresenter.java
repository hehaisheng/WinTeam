package com.shawnway.nav.app.wtw.module.quotation.international.positions.today;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;

/**
 * Created by Cicinnus on 2016/11/28.
 */

@SuppressWarnings("unchecked")
public class TodayPresenter extends BasePresenter<TodayContract.ITodayTradingView> implements TodayContract.ITodayTradingPresenter {

    private final TodayTradingManager todayTradingManager;

    public TodayPresenter(Activity activity, TodayContract.ITodayTradingView view) {
        super(activity, view);
        todayTradingManager = new TodayTradingManager();
    }

    @Override
    public void getTodayTrading() {
        mView.showLoading();
        addSubscribe(todayTradingManager.getTodayTrading()
                .subscribe(new BaseSubscriber<TodayTradingBean>() {
                    @Override
                    public void onSuccess(TodayTradingBean todayTradingBean) {
                        mView.showContent();
                        mView.addTodayTrading(todayTradingBean.getWareHouseInfos());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError(e.getMessage());
                    }
                }));
    }
}
