package com.shawnway.nav.app.wtw.module.quotation.international.international_real_list;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.module.quotation.international.InternationalListBean;

/**
 * Created by Cicinnus on 2016/12/20.
 */

public class InternationalRealListPresenter extends BasePresenter<InternationalRealListContract.IInternationRealListView> implements InternationalRealListContract.IInternationalRealListPresenter {

    private final InternationalRealListManager internationalRealListManager;

    public InternationalRealListPresenter(Activity activity, InternationalRealListContract.IInternationRealListView view) {
        super(activity, view);
        internationalRealListManager = new InternationalRealListManager();
    }

    @Override
    public void getList() {
        mView.showLoading();
        addSubscribe(internationalRealListManager.getList()
                .subscribe(new BaseSubscriber<InternationalListBean>() {
                    @Override
                    public void onSuccess(InternationalListBean internationalListBean) {
                        mView.showContent();
                        mView.addList(internationalListBean.getInstrumentRealmarket());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError("获取期货列表失败，请稍后重试");
                    }
                }));
    }
}
