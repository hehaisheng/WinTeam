package com.shawnway.nav.app.wtw.net;

import com.shawnway.nav.app.wtw.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/10/13.
 */

public class RetrofitClient {
    private static RetrofitClient mInstance;
    private static Retrofit retrofit;

    //#define Base154_URL @"http://120.76.246.29"//生产环境
    // #define Base154_URL @"http://120.76.153.154:9088"//测试环境
    private RetrofitClient(){
        //  .baseUrl(BuildConfig.DEBUG?"http://122.112.213.33:9088/":Api.BASE_URL)
        //BuildConfig.DEBUG?"http://122.112.213.33:9088/":Api.BASE_URL这可以正常注册
        //赢天下，但是获取不了重要消息
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.DEBUG?"http://122.112.213.33:9088/":Api.BASE_URL)
                .client(OkHttpManager.getInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }

    public static RetrofitClient getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitClient.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitClient();
                }
            }
        }
        return mInstance;
    }

    private <T> T create(Class<T> clz) {
        return retrofit.create(clz);
    }

    public Api api(){
        return RetrofitClient.getInstance().create(Api.class);
    }
}
