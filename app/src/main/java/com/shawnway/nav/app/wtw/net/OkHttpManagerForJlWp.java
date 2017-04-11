package com.shawnway.nav.app.wtw.net;


import com.shawnway.nav.app.wtw.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


public class OkHttpManagerForJlWp {
    private static OkHttpClient okHttpClient;

    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            synchronized (OkHttpManagerForJlWp.class) {
                if (okHttpClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    if (BuildConfig.LOG_DEBUG) {
                        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        builder.addInterceptor(interceptor);
                    }
                    //超时时间
                    builder.connectTimeout(15, TimeUnit.SECONDS);//15S连接超时
                    builder.readTimeout(20, TimeUnit.SECONDS);//20s读取超时
                    builder.writeTimeout(20, TimeUnit.SECONDS);//20s写入超时
                    //错误重连
                    builder.retryOnConnectionFailure(true);
                    okHttpClient = builder.build();
                }
            }
        }
        return okHttpClient;
    }
}
