package com.shawnway.nav.app.wtw.net;

import com.shawnway.nav.app.wtw.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Cicinnus on 2016/12/7.
 */

public class WPRetrofitClient {
    private static WPRetrofitClient mInstance;
    private static Retrofit retrofit;

    private WPRetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.DEBUG?"http://dev.vpan20.opensdk.zfu188.com":WpApi.BASE_URL)
                .client(OkHttpManagerForWP.getInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }

    public static WPRetrofitClient getInstance() {
        if (mInstance == null) {
            synchronized (WPRetrofitClient.class) {
                if (mInstance == null) {
                    mInstance = new WPRetrofitClient();
                }
            }
        }
        return mInstance;
    }

    private <T> T create(Class<T> clz) {
        return retrofit.create(clz);
    }

    public WpApi api(){
        return WPRetrofitClient.getInstance().create(WpApi.class);
    }
}
