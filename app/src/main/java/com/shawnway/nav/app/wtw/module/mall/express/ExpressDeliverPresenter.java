package com.shawnway.nav.app.wtw.module.mall.express;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.module.mall.bean.ExpressDeliver;

/**
 * Created by Kevin on 2016/11/24
 */

public class ExpressDeliverPresenter extends BasePresenter<ExpressContract.IExpressView> implements ExpressContract.IExpressPresenter {

    private ExpressManager expressManager;

    public ExpressDeliverPresenter(Activity activity, ExpressContract.IExpressView view) {
        super(activity, view);
        this.expressManager = new ExpressManager();
    }

    /**
     * 通过订单ID获取物流信息
     *
     * @param orderId
     */
    @Override
    public void getExpressByOrderId(String orderId) {
        mView.showLoading();
        addSubscribe(expressManager.getExpressByOrderId(orderId)
                .subscribe(new BaseSubscriber<ExpressDeliver>() {
                               @Override
                               public void onSuccess(ExpressDeliver expressDeliver) {
                                   mView.addExpressResult(expressDeliver);
                                   mView.showContent();
                               }

                               @Override
                               public void onError(Throwable e) {
                                   super.onError(e);
                                   mView.showError(e.getMessage());
                               }
                           }
                )
        );
    }

}
