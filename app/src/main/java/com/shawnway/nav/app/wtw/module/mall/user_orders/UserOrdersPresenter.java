package com.shawnway.nav.app.wtw.module.mall.user_orders;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.module.mall.bean.MallOrders;

import retrofit2.Response;

/**
 * Created by Kevin on 2016/11/16
 */

@SuppressWarnings("unchecked")
public class UserOrdersPresenter extends BasePresenter<UserOrdersContract.IUserOrderView> implements UserOrdersContract.IUserOrderPresenter {
    private UserOrdersManager userOrdersManager;

    public UserOrdersPresenter(Activity activity, UserOrdersContract.IUserOrderView view) {
        super(activity, view);
        this.userOrdersManager = new UserOrdersManager();
    }

    /**
     * 获取用户积分商城的所有订单
     *
     * @param mallOrder
     */
    @Override
    public void getMallOrders(final MallOrders mallOrder) {
        mView.showLoading();
        addSubscribe(userOrdersManager.getMallOrders(mallOrder)
                .subscribe(new BaseSubscriber<Response<MallOrders>>() {

                               @Override
                               public void onError(Throwable e) {
                                   super.onError(e);
                                   mView.showError("");
                               }

                               @Override
                               public void onSuccess(Response<MallOrders> response) {
                                   if (response.code() == 200) {
                                       response.body().setStatusCode(mallOrder.getStatusCode());
                                       mView.showContent();
                                       mView.addMallOrders(response.body());
                                   } else if (response.code() == 401) {
                                       LoginActivity.getInstance(mActivity);
                                       mActivity.finish();
                                   }
                               }
                           }
                )
        );
    }


    /**
     * 确认收货
     *
     * @param id
     */
    @Override
    public void confirmRceipt(String id, final int position) {
        addSubscribe(userOrdersManager.confirmRceipt(id)
                .subscribe(new BaseSubscriber<MallOrders>() {
                    @Override
                    public void onSuccess(MallOrders mallOrders) {
                        mallOrders.setPosition(position);
                        mView.onResult(mallOrders);
                    }
                })
        );
    }

    /**
     * 删除订单
     *
     * @param id
     */
    @Override
    public void deleteOrder(String id, final int position) {
        addSubscribe(userOrdersManager.deleteOrder(id)
                .subscribe(new BaseSubscriber<MallOrders>() {
                    @Override
                    public void onSuccess(MallOrders mallOrders) {
                        mallOrders.setPosition(position);
                        mView.onResult(mallOrders);
                    }
                }));
    }
}
