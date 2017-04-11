package com.shawnway.nav.app.wtw.module.mall.exchange_list;

import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/11/29.
 */

public class ExchangeListManager {


    /**
     * 获取所有订单
     *
     * @return
     */
    public Observable getExchangeList(int page) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pageNo", page);
            jsonObject.put("pageSize", 8);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RetrofitClient.getInstance()
                .api()
                .getMallOrders(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.applyIoSchedulers());
    }
}
