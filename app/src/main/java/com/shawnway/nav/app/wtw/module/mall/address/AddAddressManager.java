package com.shawnway.nav.app.wtw.module.mall.address;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Cicinnus on 2016/11/16.
 */

public class AddAddressManager {


    public void updateAddress(String address,String cellPhone,String consignee,int def,int id){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("address",address);
            jsonObject.put("cellphone",cellPhone);
            jsonObject.put("consignee",consignee);
            jsonObject.put("defaultAddress",def);
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
