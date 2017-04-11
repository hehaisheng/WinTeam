package com.shawnway.nav.app.wtw.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.RefreshTokenResult;
import com.shawnway.nav.app.wtw.net.JlWPRetrofitClient;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.net.WPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/10/24.
 * 心跳线程
 */

public class HeartBeatService extends Service {

    public static final String ACTION = "HeartBeatService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new PollingThread().start();
        return super.onStartCommand(intent, flags, startId);
    }


    class PollingThread extends Thread {
        @Override
        public void run() {
            sendHeartBeat();
//            sendWPHeartBeat();
            sendJlWPHeartBeat();
        }
    }

    /**
     * 赢天下token
     */
    private void sendHeartBeat() {
        RetrofitClient
                .getInstance()
                .api()
                .sendHeartBeat()
                .compose(SchedulersCompat.<ResponseBody>applyIoSchedulers())
                .subscribe(new BaseSubscriber<ResponseBody>() {
                    @Override
                    public void onSuccess(ResponseBody responseBody) {

                    }
                });

    }

    /**
     * 微盘token
     */
    private void sendWPHeartBeat() {
        String refreshToken = SPUtils.getInstance(getApplicationContext(), SPUtils.SP_WP)
                .getString(SPUtils.WP_REFRESH_TOKEN, "");
        if (!refreshToken.equals("")) {
            WPRetrofitClient
                    .getInstance()
                    .api()
                    .refreshToken("app",
                            "app_secure",
                            "refresh_token", refreshToken)
                    .subscribe(new BaseSubscriber<Response<RefreshTokenResult>>() {
                        @Override
                        public void onSuccess(Response<RefreshTokenResult> response) {
                            if (response.code() == 200) {
                                SPUtils.getInstance(getApplicationContext(), SPUtils.SP_WP)
                                        .putString(SPUtils.WP_TOKEN, response.body().getAccess_token())
                                        .putString(SPUtils.WP_REFRESH_TOKEN, response.body().getRefresh_token())
                                        .apply();
                            }
                        }
                    });
        }
    }

    /**
     * 吉林微盘token
     */

    private void sendJlWPHeartBeat() {
        String refreshToken = SPUtils.getInstance(getApplicationContext(), SPUtils.SP_JL_WP)
                .getString(SPUtils.JL_WP_REFRESH_TOKEN, "");
        if (!refreshToken.equals("")) {
            JlWPRetrofitClient
                    .getInstance()
                    .api()
                    .refreshToken("app",
                            "app_secure",
                            "refresh_token", refreshToken)
                    .subscribe(new BaseSubscriber<Response<RefreshTokenResult>>() {
                        @Override
                        public void onSuccess(Response<RefreshTokenResult> response) {
                            if (response.code() == 200) {
                                SPUtils.getInstance(getApplicationContext(), SPUtils.SP_JL_WP)
                                        .putString(SPUtils.JL_WP_TOKEN, response.body().getAccess_token())
                                        .putString(SPUtils.JL_WP_REFRESH_TOKEN, response.body().getRefresh_token())
                                        .apply();
                            }
                        }
                    });
        }
    }

}
