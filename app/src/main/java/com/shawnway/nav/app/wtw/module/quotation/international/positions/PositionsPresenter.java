package com.shawnway.nav.app.wtw.module.quotation.international.positions;

import android.app.Activity;

import com.orhanobut.logger.Logger;
import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.module.quotation.international.InternationalListBean;
import com.shawnway.nav.app.wtw.module.quotation.international.order.TradeBean;

import retrofit2.Response;
import rx.functions.Action1;

/**
 * Created by Cicinnus on 2016/11/25.
 */

@SuppressWarnings("unchecked")
public class PositionsPresenter extends BasePresenter<PositionsContract.IPositionsView> implements PositionsContract.IPositionsPresenter {


    private final PositionsManager positionsManager;
    private boolean mPositions = false;
    private boolean mPointValues = false;

    public PositionsPresenter(Activity activity, PositionsContract.IPositionsView view) {
        super(activity, view);
        positionsManager = new PositionsManager();
    }

    @Override
    public void getPositions() {
        mView.showLoading();
        addSubscribe(positionsManager.getPositions()
                .subscribe(new Action1<Response<PositionsBean>>() {
                    @Override
                    public void call(Response<PositionsBean> response) {
                        if (response.code() == 200) {
                            mPositions = true;
                            mView.addPositions(response.body());
                            loadAllData();
                        } else if (response.code() == 401) {
                            mActivity.finish();
                            LoginActivity.getInstance(mActivity);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        mView.showContent();
                        mView.showError("获取失败");
                    }
                }));
    }


    @Override
    public void getMarketPrice() {
        addSubscribe(positionsManager.getMarketPrice()
                .subscribe(new Action1<InternationalListBean>() {
                    @Override
                    public void call(InternationalListBean internationalListBean) {
                        mView.addMarketPrice(internationalListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                    }
                }));
    }

    @Override
    public void order(PositionsBean.WareHouseInfosBean bean) {
        mView.showLoading();
        addSubscribe(positionsManager.order(bean)
                .subscribe(new Action1<Response<TradeBean>>() {
                    @Override
                    public void call(Response<TradeBean> response) {
                        if (response.code() == 200) {
                            mView.orderSuccess(response.body());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showContent();
                        Logger.e(throwable.getMessage());
                        showToast("平仓失败，请重试");
                    }
                }));
    }

    @Override
    public void getPointValue() {
        addSubscribe(positionsManager.getPointValue().subscribe(
                new BaseSubscriber<PointValueResultBean>() {
                    @Override
                    public void onSuccess(PointValueResultBean pointValueResultBean) {
                        mPointValues = true;
                        mView.addPointValue(pointValueResultBean.getList());
                        loadAllData();
                    }
                }
        ));
    }

    @Override
    public void loadAllData() {
        if (mPointValues && mPositions) {
            mView.showContent();
        }
    }
}
