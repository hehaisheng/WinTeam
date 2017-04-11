package com.shawnway.nav.app.wtw.base;

import android.app.Activity;

import com.shawnway.nav.app.wtw.net.ErrorHanding;
import com.shawnway.nav.app.wtw.tool.ToastUtil;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;



public class BasePresenter<T> implements IPresenter {


    protected Activity mActivity;
    protected T mView;

    protected CompositeSubscription mCompositeSubscription;


    public BasePresenter(Activity activity, T view) {
        this.mActivity = activity;
        this.mView = view;
    }


    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }


    protected void addSubscribe(Subscription subscription) {

        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void detachView() {
        this.mView = null;
        
        unSubscribe();
    }


    protected void handleError(Throwable throwable) {
        ToastUtil.showShort(mActivity, ErrorHanding.handleError(throwable));
    }

    public void showToast(String msg){
        ToastUtil.showShort(mActivity,msg);
    }

}
