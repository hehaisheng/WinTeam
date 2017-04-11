package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_positions;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.base.BaseWpResult;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;

/**
 * Created by Cicinnus on 2016/12/10.
 */

public class JlWpPositionPresenter extends BasePresenter<JlWpPositionContract.IWpPositionView> implements JlWpPositionContract.IWpPositionPresenter {

    private final JlWpPositionManager jlWpPositionManager;
    private boolean positions = false;
    private boolean balance = false;

    public JlWpPositionPresenter(Activity activity, JlWpPositionContract.IWpPositionView view) {
        super(activity, view);
        jlWpPositionManager = new JlWpPositionManager();
    }

    @Override
    public void getPositions(String token) {
        mView.showLoading();
        addSubscribe(jlWpPositionManager.getWpPositions(token)
                .subscribe(new BaseSubscriber<JlWpPositionsBean>() {
                    @Override
                    public void onSuccess(JlWpPositionsBean jlWpPositionsBean) {
                        mView.addPositions(jlWpPositionsBean);
                        positions = true;
                        loadSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        positions = true;
                        loadSuccess();
                    }
                }));
    }

    @Override
    public void getWpUserAccountBalance(String token) {
        addSubscribe(jlWpPositionManager.getWpUserAccountBalance(token)
                .subscribe(new BaseSubscriber<UserAccountBean>() {
                    @Override
                    public void onSuccess(UserAccountBean userAccountBean) {
                        mView.addWpUserBalance(userAccountBean.data);
                        balance = true;
                        loadSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        balance = true;
                        loadSuccess();
                    }
                }));
    }

    @Override
    public void liquidate(String orderId, String token) {
        mView.showLoading();
        addSubscribe(jlWpPositionManager.liquidate(orderId, token)
                .subscribe(new BaseSubscriber<BaseWpResult>() {
                    @Override
                    public void onSuccess(BaseWpResult baseWpResult) {
                        if ("200".equals(baseWpResult.getState())) {
                            mView.showContent();
                            mView.liquidateSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        showToast("平仓失败，请稍后重试");
                    }
                }));
    }

    @Override
    public void liquidateAll(String token) {
        mView.showContent();
        addSubscribe(jlWpPositionManager.liquidateAll(token)
                .subscribe(new BaseSubscriber<BaseWpResult>() {
                    @Override
                    public void onSuccess(BaseWpResult baseWpResult) {
                        if ("200".equals(baseWpResult.getState())) {
                            mView.showContent();
                            mView.liquidateAllSuccess();
                        }else {
                            mView.showError("一键平仓失败，请稍后再试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError("一键平仓失败，请稍后再试");
                    }
                }));
    }

    @Override
    public void loadSuccess() {
        if (positions && balance) {
            mView.showContent();
        }
    }
}
