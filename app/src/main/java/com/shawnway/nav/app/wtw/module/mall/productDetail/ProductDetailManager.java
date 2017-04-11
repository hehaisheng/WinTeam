package com.shawnway.nav.app.wtw.module.mall.productDetail;

import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/11/14.
 */

public class ProductDetailManager {

    public Observable getProDetail(int id) {
        return RetrofitClient
                .getInstance()
                .api()
                .getProDetail(id)
                .compose(SchedulersCompat.<ProDetailBean>applyIoSchedulers());
    }

    public Observable addShoppingCarProduct(int proId, int quantity) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("proId", proId);
            jsonObject.put("quantity", quantity);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return RetrofitClient
                .getInstance()
                .api()
                .addShoppingCarProduct(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.applyIoSchedulers());

    }
}
