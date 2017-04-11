package com.shawnway.nav.app.wtw.module.mall.address;

import com.shawnway.nav.app.wtw.module.mall.address.bean.DeleteAddressResultBean;
import com.shawnway.nav.app.wtw.module.mall.address.bean.SetDefaultAddrResultBean;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/11/16.
 */

public class AddressListManager {


    public Observable getAddressList() {
        return RetrofitClient
                .getInstance()
                .api()
                .getAddressList()
                .compose(SchedulersCompat.applyIoSchedulers());
    }

    public Observable setDefaultAddress(int id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RetrofitClient
                .getInstance()
                .api()
                .setDefaultAddress(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<SetDefaultAddrResultBean>applyIoSchedulers());
    }

    public Observable deleteAddress(int id){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RetrofitClient
                .getInstance()
                .api()
                .deleteAddress(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<DeleteAddressResultBean>applyIoSchedulers());

    }

}
