package com.shawnway.nav.app.wtw.module.quotation.wp.wp_list;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;

/**
 * Created by Cicinnus on 2016/12/23.
 */

public class WpListPresenter extends BasePresenter<WpListContract.IWpListView> implements WpListContract.IWpPresenter {

    private final WpListManager wpListManager;

    public WpListPresenter(Activity activity, WpListContract.IWpListView view) {
        super(activity, view);
        wpListManager = new WpListManager();
    }

    @Override
    public void getList() {
        mView.showLoading();
        addSubscribe(wpListManager.getWpList()
                .subscribe(new BaseSubscriber<QuotationsWPBean[]>() {
                    @Override
                    public void onSuccess(QuotationsWPBean[] quotationsWPBeen) {
                        mView.showContent();
                        mView.addList(quotationsWPBeen);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError("获取微盘列表失败");
                    }
                }));
    }
}
