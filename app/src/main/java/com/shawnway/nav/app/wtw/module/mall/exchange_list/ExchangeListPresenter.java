package com.shawnway.nav.app.wtw.module.mall.exchange_list;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.module.mall.bean.MallOrders;
import com.shawnway.nav.app.wtw.net.ErrorHanding;

import retrofit2.Response;

/**
 * Created by Cicinnus on 2016/11/29.
 */

public class ExchangeListPresenter extends BasePresenter<ExchangeListContract.IExchangeListView> implements ExchangeListContract.IExchangePresenter {

    private final ExchangeListManager exchangeListManager;

    public ExchangeListPresenter(Activity activity, ExchangeListContract.IExchangeListView view) {
        super(activity, view);

        exchangeListManager = new ExchangeListManager();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getExchangeList(int page) {
        mView.showLoading();
        addSubscribe(exchangeListManager.getExchangeList(page)
                .subscribe(new BaseSubscriber<Response<MallOrders>>() {
                    @Override
                    public void onSuccess(Response<MallOrders> response) {
                        if (response.code() == 200) {
                            mView.showContent();
                            mView.addExchangeList(response.body());
                        } else if (response.code() == 401) {
                            mActivity.finish();
                            LoginActivity.getInstance(mActivity);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError(ErrorHanding.handleError(e));
                    }
                }));
    }
}
