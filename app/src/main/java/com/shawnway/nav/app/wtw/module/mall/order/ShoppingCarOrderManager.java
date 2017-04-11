package com.shawnway.nav.app.wtw.module.mall.order;

import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/11/18.
 */

@SuppressWarnings("unchecked")
public class ShoppingCarOrderManager {


    /**
     * 获取默认收货地址
     *
     * @return
     */
    public Observable getAddressAndPoint() {
        Observable getDefaultAddress = RetrofitClient
                .getInstance()
                .api()
                .getDefaultAdress();

        Observable getUserPoint = RetrofitClient
                .getInstance()
                .api()
                .getUserPoint();

        return Observable.merge(getDefaultAddress, getUserPoint)
                .compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 兑换商品
     *
     * @param jsonContent
     * @return
     */
    public Observable exchangeProduct(String jsonContent) {
        return RetrofitClient
                .getInstance()
                .api()
                .exchangeForProduct(JsonRequestBody.getInstance().convertJsonContent(jsonContent))
                .compose(SchedulersCompat.applyIoSchedulers());
    }

}
