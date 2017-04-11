package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.tran.build_tran;

import android.app.Activity;
import android.text.TextUtils;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.BuildTranBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.NotLiquidateOrder;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.TranGoodsWrapper;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.module.user.ticket.jl_wp_ticket.JlWpTicketBean;

import java.util.ArrayList;

import retrofit2.Response;

/**
 * Created by Kevin on 2016/12/8
 */

public class JlBuildTranPresenter extends BasePresenter<JlBuildTranContract.IBuildTranView> implements JlBuildTranContract.IBuildPresenter {

    private final JlBuildTranManager jlBuildTranManager;

    public JlBuildTranPresenter(Activity activity, JlBuildTranContract.IBuildTranView view) {
        super(activity, view);
        jlBuildTranManager = new JlBuildTranManager();
    }


    /**
     * 获取产品列表
     *
     * @param quotationsWPBean 通过产品的No来筛选
     */
    @Override
    public void getTranGoods(final QuotationsWPBean quotationsWPBean) {
        addSubscribe(jlBuildTranManager.getTranGoods()
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
        addSubscribe(jlBuildTranManager.getNotLiquidateOrder(access_token)
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
        addSubscribe(jlBuildTranManager.getUserTicket(access_token)
                .subscribe(new BaseSubscriber<Response<JlWpTicketBean>>() {

                    @Override
                    public void onSuccess(Response<JlWpTicketBean> jlWpTicketBeanResponse) {
                        mView.addUserTicket(jlWpTicketBeanResponse.body());
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
        addSubscribe(jlBuildTranManager.getUserAccountBalance(access_token)
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
        addSubscribe(jlBuildTranManager.createOrder(access_token, buildTranBean)
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
