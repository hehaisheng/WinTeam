package com.shawnway.nav.app.wtw.net;


import com.shawnway.nav.app.wtw.BuildConfig;
import com.shawnway.nav.app.wtw.application.WinApplication;
import com.shawnway.nav.app.wtw.tool.SPUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


public class OkHttpManager {
    private static OkHttpClient okHttpClient;

    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            synchronized (OkHttpManager.class) {
                if (okHttpClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    if (BuildConfig.LOG_DEBUG) {
                        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        builder.addInterceptor(interceptor);
                    }
                    //拦截Cookie
                    ReceivedCookiesInterceptor cookiesInterceptor = new ReceivedCookiesInterceptor();
                    builder.addInterceptor(cookiesInterceptor);
                    //设置请求头
                    Interceptor header = new Interceptor()  {
                        @Override
                        public Response intercept(Chain chain) {
                            Request request = chain.request();
                            Request.Builder requestBuilder = request.newBuilder()
                                    .addHeader("Cookie", "JSESSIONID=" + SPUtils.getInstance(WinApplication.getInstance(),SPUtils.SP_COOKIES).getString(SPUtils.JSESSIONID,"") + ";XSRF-TOKEN=" + SPUtils.getInstance(WinApplication.getInstance(),SPUtils.SP_COOKIES).getString(SPUtils.TOKEN,""))
                                    .addHeader("X-XSRF-TOKEN", SPUtils.getInstance(WinApplication.getInstance(),SPUtils.SP_COOKIES).getString(SPUtils.TOKEN,""))
                                    .addHeader("Content-Type", "application/json");
                            Request request1 = requestBuilder.build();
                            try {
                                return chain.proceed(request1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    };
                    builder.addInterceptor(header);

                    //超时时间
                    builder.connectTimeout(15, TimeUnit.SECONDS);//15S连接超时
                    builder.readTimeout(20, TimeUnit.SECONDS);//20s读取超时
                    builder.writeTimeout(20, TimeUnit.SECONDS);//20s写入超时
                    //错误重连
                    builder.retryOnConnectionFailure(true);

//                    builder.sslSocketFactory(SSLFactory.getSSLSocketFactory(WinApplication.getInstance()), new X509TrustManager() {
//                        @Override
//                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//
//                        }
//
//                        @Override
//                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//
//                        }
//
//                        @Override
//                        public X509Certificate[] getAcceptedIssuers() {
//                            return new X509Certificate[0];
//                        }
//                    });
                    okHttpClient = builder.build();
                }
            }
        }
        return okHttpClient;
    }
}
