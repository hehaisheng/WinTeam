package com.shawnway.nav.app.wtw.module.integral_mall;

import com.shawnway.nav.app.wtw.bean.UserWinningResult;
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

public class UserWinningManager {

    public Observable getUserWinningResult(int page) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page", page);
            jsonObject.put("size", 15);
        } catch (JSONException e) {
            e.printStackTrace();
        }
       return RetrofitClient
                .getInstance()
                .api()
                .getUserWinningRecord(new JsonRequestBody().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<UserWinningResult>applyIoSchedulers());
    }
}
