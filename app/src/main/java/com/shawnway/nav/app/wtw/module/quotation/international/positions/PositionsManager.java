package com.shawnway.nav.app.wtw.module.quotation.international.positions;

import com.orhanobut.logger.Logger;
import com.shawnway.nav.app.wtw.module.quotation.international.InternationalListBean;
import com.shawnway.nav.app.wtw.module.quotation.international.international_detail.UserAccountInfo;
import com.shawnway.nav.app.wtw.module.quotation.international.international_detail.UserAccountList;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Cicinnus on 2016/11/25.
 */

public class PositionsManager {


    /**
     * 获取持仓
     *
     * @return
     */
    public Observable getPositions() {
        return RetrofitClient
                .getInstance()
                .api()
                .getAllPositions()
                .compose(SchedulersCompat.applyIoSchedulers());
    }


    /**
     * 获取市场价格
     *
     * @return
     */
    public Observable getMarketPrice() {
        return RetrofitClient
                .getInstance()
                .api()
                .getInternationalList()
                .compose(SchedulersCompat.<InternationalListBean>applyIoSchedulers());
    }

    /**
     * 获取用户余额
     *
     * @return
     */
    public Observable getUserAmount() {
        return RetrofitClient
                .getInstance()
                .api()
                .getUserAccountList()
                .flatMap(new Func1<Response<UserAccountList>, Observable<UserAccountInfo>>() {
                    @Override
                    public Observable<UserAccountInfo> call(Response<UserAccountList> response) {
                        if (response.code()==200) {
                            for (int i = 0; i < response.body().getList().size(); i++) {
                                if (response.body().getList().get(i).getIsRealTrading() == 1) {
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("tradingAccountId", response.body()
                                                .getList().get(i).getTradingAccountId());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    return RetrofitClient.getInstance()
                                            .api()
                                            .getUserAccountInfo(new JsonRequestBody().convertJsonContent(jsonObject.toString()));
                                }
                            }
                        }
                        return Observable.error(new Exception("获取账号信息失败"));
                    }

                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 平仓
     *
     * @param bean 平仓的对象
     * @return
     */
    public Observable order(PositionsBean.WareHouseInfosBean bean) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("securityCode", bean.getProdCode());
            jsonObject.put("tradeType", bean.getOrderType());
            jsonObject.put("qty", bean.getExecutionQuantity());
            jsonObject.put("actionType", bean.getOrderType());
            jsonObject.put("orderType", 5);
            jsonObject.put("actionSide", bean.getOrderSide());
            jsonObject.put("needOffsetOrderId", bean.getOrderId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Logger.d(jsonObject.toString());
        return RetrofitClient
                .getInstance()
                .api()
                .order(new JsonRequestBody().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.applyIoSchedulers());
    }

    public Observable getPointValue() {
        return RetrofitClient
                .getInstance()
                .api()
                .getPointValue()
                .compose(SchedulersCompat.<PointValueResultBean>applyIoSchedulers());
    }
}
