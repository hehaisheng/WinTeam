package com.shawnway.nav.app.wtw.net;

import com.shawnway.nav.app.wtw.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Cicinnus on 2016/12/7.
 */

public class JlWPRetrofitClient {
    private static JlWPRetrofitClient mInstance;
    private static Retrofit retrofit;

    private JlWPRetrofitClient(){
        //baseUrl(BuildConfig.DEBUG?"http://uat.thor.opensdk.4001889177.com/":JlWpApi.BASE_URL)
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.DEBUG?"http://uat.thor.opensdk.4001889177.com/":JlWpApi.BASE_URL)
                .client(OkHttpManagerForJlWp.getInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }

    public static JlWPRetrofitClient getInstance() {
        if (mInstance == null) {
            synchronized (JlWPRetrofitClient.class) {
                if (mInstance == null) {
                    mInstance = new JlWPRetrofitClient();
                }
            }
        }
        return mInstance;
    }

    private <T> T create(Class<T> clz) {
        return retrofit.create(clz);
    }

    public JlWpApi api(){
        return JlWPRetrofitClient.getInstance().create(JlWpApi.class);
    }
}
