package com.shawnway.nav.app.wtw.module.mall.shopping_car;

import com.google.gson.Gson;
import com.shawnway.nav.app.wtw.module.mall.shopping_car.bean.UpdateShoppingCarRequestBean;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/11/16.
 */

public class ShoppingCarManager {


    /**
     * 获取购物车内容
     * @return
     */
    public Observable getShoppingCarList() {
        return RetrofitClient
                .getInstance()
                .api()
                .getShoppingCarList()
                .compose(SchedulersCompat.applyIoSchedulers());

    }

    /**
     * 更新商品数量信息
     * @param proId 商品Id
     * @param quantity 数量
     * @return
     */
    public Observable updateShoppingCarProduct(int proId, int quantity) {
        UpdateShoppingCarRequestBean requestBean = new UpdateShoppingCarRequestBean();
        List<UpdateShoppingCarRequestBean.ListBean> listBeen = new ArrayList<>();
        UpdateShoppingCarRequestBean.ListBean bean = new UpdateShoppingCarRequestBean.ListBean();
        bean.setProId(proId);
        bean.setQuantity(quantity);
        listBeen.add(bean);
        requestBean.setList(listBeen);
        String jsonContent = new Gson().toJson(requestBean);
        return RetrofitClient
                .getInstance()
                .api()
                .updateShoppingCarProduct(JsonRequestBody.getInstance().convertJsonContent(jsonContent))
                .compose(SchedulersCompat.applyIoSchedulers());

    }

    /**
     * 删除购物车商品
     * @param proId
     * @return
     */
    public Observable deleteShoppingCarProduct(int proId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("proId", proId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RetrofitClient
                .getInstance()
                .api()
                .deleteShoppingCarProdutc(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.applyIoSchedulers());

    }


}
