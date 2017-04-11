package com.shawnway.nav.app.wtw.module.quotation.international.billingRecord;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.module.quotation.international.international_detail.UserAccountInfo;

import retrofit2.Response;

/**
 * Created by Cicinnus on 2016/12/5.
 */

public class BillingPresenter extends BasePresenter<BillingContract.IBillingView> implements BillingContract.IBillingPresenter {

    private BillingManager billingManager;
    private boolean billing = false;
    private boolean money = false;

    public BillingPresenter(Activity activity, BillingContract.IBillingView view) {
        super(activity, view);
        billingManager = new BillingManager();
    }

    @Override
    public void getBilling() {
        mView.showLoading();
        addSubscribe(billingManager.getHistoryBilling()
                .subscribe(new BaseSubscriber<Response<BillRecordBean>>() {
                    @Override
                    public void onSuccess(Response<BillRecordBean> response) {
                        if (response.code() == 200) {
                            billing = true;
                            loadAll();
                            mView.addBilling(response.body().getWareHouseInfos());
                        } else if (response.code() == 401) {
                            mActivity.finish();
                            LoginActivity.getInstance(mActivity);
                        }
                    }
                }));
    }

    @Override
    public void getUserMoney() {
        addSubscribe(billingManager.getUserAccount()
                .subscribe(new BaseSubscriber<UserAccountInfo>() {
                    @Override
                    public void onSuccess(UserAccountInfo userAccountInfo) {
                        money = true;
                        mView.addMoney(userAccountInfo.getCustomerTradingAccount().getTradingAccountUsableAmount());
                        loadAll();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        loadAll();
                        money = true;
                        mView.showError("获取余额失败");
                    }
                }));
    }

    @Override
    public void loadAll() {
        if (billing && money) {
            mView.showContent();
        }
    }
}
