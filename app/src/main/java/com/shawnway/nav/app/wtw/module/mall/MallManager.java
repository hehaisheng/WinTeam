package com.shawnway.nav.app.wtw.module.mall;

import com.shawnway.nav.app.wtw.bean.SignInResult;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/26.
 * Administrator github = "https://github.com/Cicinnus0407"
 * 真正进行联网操作的地方
 */

public class MallManager {




    public Observable<Response<SignInResult>> signIn(){
        return RetrofitClient
                .getInstance()
                .api()
                .signIn()
                .compose(SchedulersCompat.<Response<SignInResult>>applyIoSchedulers());
    }


    /**
     * 获取用户积分
     *
     * @return Observable
     */
    public Observable getUserPoint() {
        return RetrofitClient
                .getInstance()
                .api()
                .getUserPoint()
                .compose(SchedulersCompat.applyIoSchedulers());

    }

    /**
     * 获取抽奖奖励
     *
     * @return observable
     */
    public Observable getPrizesList() {
        return RetrofitClient
                .getInstance()
                .api()
                .getPrizesList()
                .compose(SchedulersCompat.applyIoSchedulers());
    }



    /**
     * 获取最新商品和推荐商品
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public Observable getHomeProduct() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page", 0);
            jsonObject.put("size", 6);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Observable getRecommend = RetrofitClient.getInstance().api()
                .getRecommendProduct(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()));
        Observable getNewest = RetrofitClient.getInstance().api()
                .getNewestProduct(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()));

        return Observable.merge(getRecommend, getNewest)
                .compose(SchedulersCompat.applyIoSchedulers());
    }

}
