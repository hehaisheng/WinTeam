package com.shawnway.nav.app.wtw.module.user.ticket.ticket;

import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/19.
 */

public class TicketManager {
    public Observable<TicketBean> getCoupon(int status,int pageNo){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status",status);
            jsonObject.put("pageNo",pageNo);
            jsonObject.put("pageSize",5);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RetrofitClient
                .getInstance()
                .api()
                .getCoupon(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<TicketBean>applyIoSchedulers());
    }
}
