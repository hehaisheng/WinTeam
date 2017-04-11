package com.shawnway.nav.app.wtw.module.quotation.international.billingRecord;

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
 * Created by Cicinnus on 2016/12/5.
 */

public class BillingManager {

    public Observable<Response<BillRecordBean>> getHistoryBilling(){
        return RetrofitClient
                .getInstance()
                .api()
                .getHistoryBilling()
                .compose(SchedulersCompat.<Response<BillRecordBean>>applyIoSchedulers());
    }

    public Observable<UserAccountInfo> getUserAccount(){
        return RetrofitClient
                .getInstance()
                .api()
                .getUserAccountList()
                .flatMap(new Func1<Response<UserAccountList>, Observable<UserAccountInfo>>() {
                    @Override
                    public Observable<UserAccountInfo> call(Response<UserAccountList> response) {
                        if (response.code() == 200) {
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
                        return Observable.error(new Exception("没有登录"));
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
