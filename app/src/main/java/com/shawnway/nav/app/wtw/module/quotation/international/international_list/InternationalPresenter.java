package com.shawnway.nav.app.wtw.module.quotation.international.international_list;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.module.quotation.international.InternationalListBean;
import com.shawnway.nav.app.wtw.net.ErrorHanding;

/**
 * Created by Cicinnus on 2016/12/5.
 */

public class InternationalPresenter extends BasePresenter<InternationListContract.IInternationalListView> implements InternationListContract.IInternationalPresenter {

    private final InternationalManager internationalManager;


    public InternationalPresenter(Activity activity, InternationListContract.IInternationalListView view) {
        super(activity, view);
        internationalManager = new InternationalManager();
    }


    @Override
    public void getInternationalList() {
        mView.showLoading();
        addSubscribe(internationalManager.getInternationalList()
                .subscribe(new BaseSubscriber<InternationalListBean>() {
                    @Override
                    public void onSuccess(InternationalListBean internationalListBean) {
                        mView.showContent();
                        mView.addInternationalList(internationalListBean.getInstrumentRealmarket());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError(ErrorHanding.handleError(e));
                    }
                }));
    }
}
