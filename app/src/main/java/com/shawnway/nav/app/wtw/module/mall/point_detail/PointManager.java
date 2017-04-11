package com.shawnway.nav.app.wtw.module.mall.point_detail;

import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;

/**
 * Created by Administrator on 2016/10/28.
 * Administrator github = "https://github.com/Cicinnus0407"
 */

public class PointManager  {


    public Observable getPointDetail(int page){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page",page);
            jsonObject.put("size",15);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return RetrofitClient
                .getInstance()
                .api()
                .getPointDetail(new JsonRequestBody().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.applyIoSchedulers());
    }
}
