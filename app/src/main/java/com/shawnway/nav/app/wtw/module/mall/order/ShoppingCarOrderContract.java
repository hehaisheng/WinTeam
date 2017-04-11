package com.shawnway.nav.app.wtw.module.mall.order;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.bean.UserPointResult;
import com.shawnway.nav.app.wtw.module.mall.address.bean.DefaultAddressResultBean;

/**
 * Created by Cicinnus on 2016/11/18.
 */

public class ShoppingCarOrderContract {

    public interface IShoppingCarOrderView extends ILoadingView {

        void addDefaultAddress(DefaultAddressResultBean defaultAddressResultBean);

        void addUserPoint(UserPointResult o);

        void exchangResult(ExChangeProductResultBean exChangeProductResultBean);

        void jumpLogin();
    }

    public interface IShoppingCarOrderPresenter {

        void getAddressAndPoint();

        void exchangeProduct(String jsonContent);
    }
}
