package com.shawnway.nav.app.wtw.module.user.message_center;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.NewsBean;
import com.shawnway.nav.app.wtw.net.ErrorHanding;

/**
 * Created by Cicinnus on 2016/11/29.
 */

@SuppressWarnings("unchecked")
public class SystemMessagePresenter extends BasePresenter<SystemMessageContract.ISystemMessageView> implements SystemMessageContract.ISystemMessagePresenter {

    private final SystemMessageManager systemMessageManager;

    public SystemMessagePresenter(Activity activity, SystemMessageContract.ISystemMessageView view) {
        super(activity, view);
        systemMessageManager = new SystemMessageManager();
    }

    @Override
    public void getSystemMessage(int page) {
        mView.showLoading();
        addSubscribe(systemMessageManager.getSystemMessage(page)
                .subscribe(new BaseSubscriber<NewsBean[]>() {
                    @Override
                    public void onSuccess(NewsBean[] newsBeen) {
                        mView.showContent();
                        mView.addSystemMessage(newsBeen);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError(ErrorHanding.handleError(e));
                    }
                }));
    }
}
