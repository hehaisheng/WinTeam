package com.shawnway.nav.app.wtw.net;

import com.shawnway.nav.app.wtw.application.WinApplication;
import com.shawnway.nav.app.wtw.tool.SPUtils;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by Administrator on 2017/2/13.
 */

public class TokenAuthenticator implements Authenticator {
    @Override
    public Request authenticate(Route route, Response response) throws IOException {




        return response.request().newBuilder()
                .addHeader("Cookie", "JSESSIONID=" + SPUtils.getInstance(WinApplication.getInstance(),SPUtils.SP_COOKIES).getString(SPUtils.JSESSIONID,"") + ";XSRF-TOKEN=" + SPUtils.getInstance(WinApplication.getInstance(),SPUtils.SP_COOKIES).getString(SPUtils.TOKEN,""))
                .build();
    }
}
