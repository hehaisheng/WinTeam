package com.shawnway.nav.app.wtw.application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.shawnway.nav.app.wtw.BuildConfig;
import com.shawnway.nav.app.wtw.service.InitializeService;
import com.umeng.socialize.PlatformConfig;

public class WinApplication extends android.support.multidex.MultiDexApplication {

    private static WinApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //Logger
        Logger.init().logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);
        InitializeService.start(this);
    }

    public static WinApplication getInstance() {
        return instance;
    }

    //各个平台的配置
    static {
        //微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin("wx57a5483a03dee3e7", "0812d448ac1f91576d93104aa92d2a07");
        //新浪微博
        PlatformConfig.setSinaWeibo("1472484729", "fd7911159b70802377c93925a07b1973");
        //QQ空间
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }


}
