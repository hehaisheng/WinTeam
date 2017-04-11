package com.shawnway.nav.app.wtw.module.mall.user_orders;

import com.shawnway.nav.app.wtw.module.mall.bean.MallOrders;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by Kevin on 2016/11/17
 */

public class UserOrdersManager {
    /**
     * 获取用户积分商城所有订单
     *
     * @return
     */
    public Observable<Response<MallOrders>> getMallOrders(MallOrders mallOrders) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pageNo", mallOrders.getPageNo());
            jsonObject.put("pageSize", mallOrders.getPageSize());
            jsonObject.put("tradeStatus", mallOrders.getTradeStatus());
            jsonObject.put("signStatus", mallOrders.getSignStatus());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RetrofitClient.getInstance()
                .api()
                .getMallOrders(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<Response<MallOrders>>applyIoSchedulers());
    }

    /**
     * 确认收货
     *
     * @param id
     * @return
     */
    public Observable<MallOrders> confirmRceipt(String id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RetrofitClient.getInstance()
                .api()
                .confirmRceipt(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<MallOrders>applyIoSchedulers());
    }

    /**
     * 删除订单
     *
     * @param id
     * @return
     */
    public Observable<MallOrders> deleteOrder(String id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RetrofitClient.getInstance()
                .api()
                .deleteOrder(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<MallOrders>applyIoSchedulers());

    }
}
