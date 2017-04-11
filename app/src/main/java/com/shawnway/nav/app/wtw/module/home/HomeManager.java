package com.shawnway.nav.app.wtw.module.home;

import com.shawnway.nav.app.wtw.bean.BannerPicBean;
import com.shawnway.nav.app.wtw.bean.SignInResult;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import retrofit2.Response;
import rx.Observable;

import static com.shawnway.nav.app.wtw.tool.SchedulersCompat.applyIoSchedulers;

/**
 * Created by Cicinnus on 2016/11/23.
 */

public class HomeManager {

    public Observable getBanner() {
        return RetrofitClient
                .getInstance()
                .api()
                .getBannerUrl()
                .compose(SchedulersCompat.<BannerPicBean>applyIoSchedulers());
    }

    public Observable getPrizesList() {
        return RetrofitClient
                .getInstance()
                .api()
                .getPrizesList()
                .compose(applyIoSchedulers());
    }

    public Observable<Response<SignInResult>> signIn() {
        return RetrofitClient
                .getInstance()
                .api()
                .signIn()
                .compose(SchedulersCompat.<Response<SignInResult>>applyIoSchedulers());
    }

}
