package com.shawnway.nav.app.wtw.module.quotation.international.billingRecord;

import com.shawnway.nav.app.wtw.base.ILoadingView;

import java.util.List;

/**
 * Created by Cicinnus on 2016/12/5.
 */

public class BillingContract {

    public interface IBillingView extends ILoadingView {
        void addBilling(List<BillRecordBean.WareHouseInfosBean> wareHouseInfos);
        void addMoney(Number tradingAccountUsableAmount);
    }

    public interface IBillingPresenter {
        void getBilling();

        void getUserMoney();

        void loadAll();
    }
}
