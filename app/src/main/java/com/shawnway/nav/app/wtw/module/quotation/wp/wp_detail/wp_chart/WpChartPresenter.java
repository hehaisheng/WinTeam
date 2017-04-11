package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_chart;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.net.ErrorHanding;

/**
 * Created by Cicinnus on 2016/12/7.
 */

public class WpChartPresenter extends BasePresenter<WpChartContract.IWpChartView> implements WpChartContract.IWpChartPresenter {

    private final WpChartManager chartManager;
    private boolean data = false;
    private boolean money = false;

    public WpChartPresenter(Activity activity, WpChartContract.IWpChartView view) {
        super(activity, view);
        chartManager = new WpChartManager();
    }

    @Override
    public void getData() {
        mView.showLoading();
        addSubscribe(chartManager.getData()
                .subscribe(new BaseSubscriber<QuotationsWPBean[]>() {
                    @Override
                    public void onSuccess(QuotationsWPBean[] quotationsWPBeen) {
                        data = true;
                        loadData();
                        mView.addData(quotationsWPBeen);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        data = true;
                        loadData();
                        mView.showError(ErrorHanding.handleError(e));
                    }
                }));
    }

    @Override
    public void getUseageMoney(String token) {
        addSubscribe(chartManager.getWpUserAccountBalance(token)
                .subscribe(new BaseSubscriber<UserAccountBean>() {
                    @Override
                    public void onSuccess(UserAccountBean userAccountBean) {
                        money = true;
                        loadData();
                        mView.addMoney(userAccountBean.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        money = true;
                        loadData();
                    }
                }));
    }

    @Override
    public void loadData() {
        if(money&&data){
            mView.showContent();
        }
    }
}
