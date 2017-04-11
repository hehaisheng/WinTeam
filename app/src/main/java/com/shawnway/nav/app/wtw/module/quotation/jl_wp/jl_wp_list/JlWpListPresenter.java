package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_list;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;

/**
 * Created by Cicinnus on 2016/12/23.
 */

public class JlWpListPresenter extends BasePresenter<JlWpListContract.IWpListView> implements JlWpListContract.IWpPresenter {

    private final JlWpListManager jlWpListManager;

    public JlWpListPresenter(Activity activity, JlWpListContract.IWpListView view) {
        super(activity, view);
        jlWpListManager = new JlWpListManager();
    }

    @Override
    public void getList() {
        mView.showLoading();
        addSubscribe(jlWpListManager.getWpList()
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
