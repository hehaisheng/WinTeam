package com.shawnway.nav.app.wtw.module.mall.user_orders;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.base.IPresenter;
import com.shawnway.nav.app.wtw.module.mall.bean.MallOrders;

/**
 * Created by Kevin on 2016/11/16
 */

public class UserOrdersContract {
    public interface IUserOrderPresenter extends IPresenter {
        void getMallOrders(MallOrders mallOrders);

        void confirmRceipt(String id, int position);

        void deleteOrder(String id, int position);
    }

    public interface IUserOrderView extends ILoadingView {
        void addMallOrders(MallOrders mallOrders);

        void onResult(MallOrders mallOrders);
    }
}
