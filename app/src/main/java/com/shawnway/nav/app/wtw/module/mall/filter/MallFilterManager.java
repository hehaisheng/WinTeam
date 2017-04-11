package com.shawnway.nav.app.wtw.module.mall.filter;

import com.orhanobut.logger.Logger;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/11/9.
 */

public class MallFilterManager {


    /**
     * 获取商城分类列表
     *
     * @return
     */
    public Observable getMallFilterList() {
        return RetrofitClient
                .getInstance()
                .api()
                .getFilterList()
                .compose(SchedulersCompat.applyIoSchedulers());
    }


    /**
     * 搜索或排序商品
     *
     * @param page           页数
     * @param productName    商品名
     * @param typeId         分类Id
     * @param sortPointId    排序Id
     * @param sortQuantityId 排序Id
     * @return
     */
    public Observable queryOrSortProduct(int page, String productName, int typeId, int sortPointId, int sortQuantityId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page", page);
            jsonObject.put("size", 6);
            jsonObject.put("productName", productName);
            jsonObject.put("productTypeId", typeId);
            jsonObject.put("orderByPoint", sortPointId);
            jsonObject.put("orderByExchangedQuantity", sortQuantityId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Logger.d("兑换量排序" + jsonObject.toString());

        return RetrofitClient
                .getInstance()
                .api()
                .queryOrSortProduct(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.applyIoSchedulers());
    }

    public Observable sortByNormal(int page, String name, int typeId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page", page);
            jsonObject.put("size", 6);
            jsonObject.put("productName", name);
            jsonObject.put("productTypeId", typeId);
            jsonObject.put("orderByPoint", 0);
            jsonObject.put("orderByExchangedQuantity", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RetrofitClient
                .getInstance()
                .api()
                .queryOrSortProduct(JsonRequestBody.getInstance()
                        .convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.applyIoSchedulers());
    }

    public Observable sortByPoint(int page, String name, int typeId, int sortPointId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page", page);
            jsonObject.put("size", 6);
            jsonObject.put("productName", name);
            jsonObject.put("productTypeId", typeId);
            jsonObject.put("orderByPoint", sortPointId);
            jsonObject.put("orderByExchangedQuantity", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RetrofitClient
                .getInstance()
                .api()
                .queryOrSortProduct(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.applyIoSchedulers());

    }

    public Observable sortByQuantity(int page, String name, int typeId, int sortQuantityId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page", page);
            jsonObject.put("size", 6);
            jsonObject.put("productName", name);
            jsonObject.put("productTypeId", typeId);
            jsonObject.put("orderByPoint", 0);
            jsonObject.put("orderByExchangedQuantity", sortQuantityId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RetrofitClient
                .getInstance()
                .api()
                .queryOrSortProduct(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.applyIoSchedulers());
    }

    public Observable searchProduct(int page, String name, int typeId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page", page);
            jsonObject.put("size", 6);
            jsonObject.put("productName", name);
            jsonObject.put("productTypeId", typeId);
            jsonObject.put("orderByPoint", 0);
            jsonObject.put("orderByExchangedQuantity", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RetrofitClient
                .getInstance()
                .api()
                .queryOrSortProduct(JsonRequestBody.getInstance()
                        .convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.applyIoSchedulers());
    }
}
