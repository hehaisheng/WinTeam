package com.shawnway.nav.app.wtw.module.mall.express;

import com.shawnway.nav.app.wtw.module.mall.bean.ExpressDeliver;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import rx.Observable;

/**
 * Created by Kevin on 2016/11/24
 */

public class ExpressManager {

    /**
     * 通过订单ID获取订单物流信息
     *
     * @param orderId
     * @return
     */
    public Observable<ExpressDeliver> getExpressByOrderId(String orderId) {
        return RetrofitClient.getInstance()
                .api()
                .getOrderExpressByOrderId(orderId)
                .compose(SchedulersCompat.<ExpressDeliver>applyIoSchedulers());
    }

}
