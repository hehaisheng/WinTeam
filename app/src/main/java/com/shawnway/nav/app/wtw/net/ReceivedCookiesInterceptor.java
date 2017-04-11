package com.shawnway.nav.app.wtw.net;

import com.shawnway.nav.app.wtw.application.WinApplication;
import com.shawnway.nav.app.wtw.tool.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/10/21.
 * 拦截Cookie
 */

public class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain)  {
        Response originalResponse = null;
        try {
            originalResponse = chain.proceed(chain.request());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (originalResponse != null && !originalResponse.headers("Set-Cookie").isEmpty()) {
            Observable
                    .from(originalResponse.headers("Set-Cookie"))
                    .map(new Func1<String, String>() {
                        @Override
                        public String call(String s) {
                            String[] cookieArray = s.split(";");
                            for (String str : cookieArray) {
                                String[] cookies = str.split("=");
                                if (cookies[0].equals("XSRF-TOKEN")) {
                                    SPUtils.getInstance(WinApplication.getInstance(),SPUtils.SP_COOKIES)
                                            .putString(SPUtils.TOKEN,cookies[1])
                                            .apply();
                                }
                                if (cookies[0].equals("JSESSIONID")) {
                                    SPUtils.getInstance(WinApplication.getInstance(),SPUtils.SP_COOKIES)
                                            .putString(SPUtils.JSESSIONID,cookies[1])
                                            .apply();
                                }
                            }
                            return cookieArray[0];
                        }
                    })
                    .subscribe();

        }
        return originalResponse;
    }


}
