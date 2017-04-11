package com.shawnway.nav.app.wtw.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.module.MainActivity;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;
import com.umeng.socialize.Config;
import com.umeng.socialize.UMShareAPI;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Cicinnus on 2016/12/5.
 * 功能初始化服务
 */

public class InitializeService extends IntentService {

    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.shawnway.nav.app.wtw.action.INIT";
    private static final String BUGLY_APPID = "e801d4e842";


    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    public InitializeService() {
        super("InitializeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }

    private void performInit() {
        LeakCanary.install(this.getApplication());

        //极光推送
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this.getApplicationContext()); // 初始化 JPush
//        X5浏览器内核
        initX5();
        //腾讯bugly
        initBuglyUpgrade();
        //友盟
        UMShareAPI.get(this.getApplicationContext());
        //新浪微博的回调
        Config.REDIRECT_URL = "http://a.app.qq.com/o/simple.jsp?pkgname=com.shawnway.nav.app.wtw";

    }


    private void initX5() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {

            }

            @Override
            public void onCoreInitFinished() {

            }
        };
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {

            }

            @Override
            public void onInstallFinish(int i) {

            }

            @Override
            public void onDownloadProgress(int i) {

            }
        });
        QbSdk.initX5Environment(this.getApplicationContext(), cb);
    }

    /**
     * 初始化更新
     */
    private void initBuglyUpgrade() {
        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;
        Beta.initDelay =  1000;
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Beta.largeIconId = R.mipmap.logo;
        Beta.smallIconId = R.mipmap.logo;
        Beta.showInterruptedStrategy = true;
        Beta.canShowUpgradeActs.add(MainActivity.class);
        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog;
        Bugly.init(getApplicationContext(), BUGLY_APPID, false);
    }

}
