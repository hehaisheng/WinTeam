package com.shawnway.nav.app.wtw.module.user.message_center;

import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/11/29.
 */

public class SystemMessageManager {


    public Observable getSystemMessage(int page) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page", page);
            jsonObject.put("size", 10);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RetrofitClient
                .getInstance()
                .api()
                .get30SystemMessages(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.applyIoSchedulers());
    }
}
