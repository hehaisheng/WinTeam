package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.build_tran;

import android.app.Activity;
import android.text.TextUtils;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.BuildTranBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.NotLiquidateOrder;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.TranGoodsWrapper;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.module.user.ticket.wp_ticket.WpTicketBean;

import java.util.ArrayList;

/**
 * Created by Kevin on 2016/12/8
 */

public class BuildTranPresenter extends BasePresenter<BuildTranContract.IBuildTranView> implements BuildTranContract.IBuildPresenter {

    private final BuildTranManager buildTranManager;

    public BuildTranPresenter(Activity activity, BuildTranContract.IBuildTranView view) {
        super(activity, view);
        buildTranManager = new BuildTranManager();
    }


    /**
     * 获取产品列表
     *
     * @param quotationsWPBean 通过产品的No来筛选
     */
    @Override
    public void getTranGoods(final QuotationsWPBean quotationsWPBean) {
        addSubscribe(buildTranManager.getTranGoods()
                .subscribe(new BaseSubscriber<TranGoodsWrapper>() {
                    @Override
                    public void onSuccess(TranGoodsWrapper tranGoodsWrapper) {
                        ArrayList<TranGoodsWrapper.TranGoodsBean> data = new ArrayList<>();
                        for (TranGoodsWrapper.TranGoodsBean tranGoodsBean : tranGoodsWrapper.getData()) {
                            if (TextUtils.equals(quotationsWPBean.getProductContract(), tranGoodsBean.getProductNo())) {
                                data.add(tranGoodsBean);
                            }
                        }
                        tranGoodsWrapper.setData(data);
                        mView.addTranGoods(tranGoodsWrapper);
                    }
                })
        );
    }

    /**
     * 获取用户实时盈亏和当前仓位
     *
     * @param access_token
     */
    @Override
    public void getNotLiquidateOrder(String access_token) {
        mView.showLoading();
        addSubscribe(buildTranManager.getNotLiquidateOrder(access_token)
                .subscribe(new BaseSubscriber<NotLiquidateOrder>() {
                               @Override
                               public void onSuccess(NotLiquidateOrder notLiquidateOrder) {
                                   if (TextUtils.equals(notLiquidateOrder.getState(), "200")) {
                                       mView.addNotLiquidateOrder(notLiquidateOrder.getData());
                                       mView.showContent();
                                   }
                               }

                               @Override
                               public void onError(Throwable e) {
                                   super.onError(e);
                                   mView.showError(e.getMessage());
                               }
                           }
                ));
    }


    /**
     * 获取用户赢家券
     *
     * @param access_token
     */
    @Override
    public void getUserTicket(String access_token) {
        addSubscribe(buildTranManager.getUserTicket(access_token)
                .subscribe(new BaseSubscriber<WpTicketBean>() {
                    @Override
                    public void onSuccess(WpTicketBean wpTicketBean) {
                        mView.addUserTicket(wpTicketBean);
                    }
                }));
    }

    /**
     * 获取用户资金信息
     *
     * @param access_token
     */
    @Override
    public void getUserAccountBalance(String access_token) {
        addSubscribe(buildTranManager.getUserAccountBalance(access_token)
                .subscribe(new BaseSubscriber<UserAccountBean>() {
                    @Override
                    public void onSuccess(UserAccountBean userAccountBean) {
                        mView.addUserAccountBalance(userAccountBean);
                    }
                })
        );
    }

    /**
     * 建仓
     *
     * @param access_token
     * @param buildTranBean
     */
    @Override
    public void createOrder(String access_token, BuildTranBean.BuildBean buildTranBean) {
        mView.showLoading();
        addSubscribe(buildTranManager.createOrder(access_token, buildTranBean)
                .subscribe(new BaseSubscriber<BuildTranBean>() {
                    @Override
                    public void onSuccess(BuildTranBean buildTranBean) {
                        mView.onCreateResult(buildTranBean);
                        mView.showContent();
                    }
                })
        );
    }
}
