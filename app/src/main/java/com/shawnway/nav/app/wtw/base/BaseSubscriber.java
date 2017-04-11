package com.shawnway.nav.app.wtw.base;

import com.orhanobut.logger.Logger;

import rx.Subscriber;

/**
 * Created by Cicinnus on 2016/11/16
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private static final String TAG = "BaseSubscriber";

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        Logger.e(e.getMessage());
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    /**
     * 请求成功重写抽象方法
     *
     * @param t
     */

    public abstract void onSuccess(T t);
}
