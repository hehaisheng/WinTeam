package com.shawnway.nav.app.wtw.module.mall.order;

import android.app.Activity;

import com.orhanobut.logger.Logger;
import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.bean.UserPointResult;
import com.shawnway.nav.app.wtw.module.mall.address.bean.DefaultAddressResultBean;

import retrofit2.Response;
import rx.functions.Action1;

/**
 * Created by Cicinnus on 2016/11/18.
 */

@SuppressWarnings("unchecked")
public class ShoppingCarOrderPresenter extends BasePresenter<ShoppingCarOrderContract.IShoppingCarOrderView> implements ShoppingCarOrderContract.IShoppingCarOrderPresenter {

    private final ShoppingCarOrderManager shoppingCarOrderManager;

    public ShoppingCarOrderPresenter(Activity activity, ShoppingCarOrderContract.IShoppingCarOrderView view) {
        super(activity, view);
        shoppingCarOrderManager = new ShoppingCarOrderManager();
    }

    @Override
    public void getAddressAndPoint() {
        mView.showLoading();
        addSubscribe(shoppingCarOrderManager.getAddressAndPoint()
                .subscribe(new Action1<Response>() {
                    @Override
                    public void call(Response response) {
                        if (response.code() == 200) {
                            mView.showContent();
                            if (response.body() instanceof DefaultAddressResultBean) {
                                mView.addDefaultAddress((DefaultAddressResultBean) response.body());
                            } else if (response.body() instanceof UserPointResult) {
                                mView.addUserPoint((UserPointResult) response.body());
                            }
                        }else if(response.code()==401){
                            mView.jumpLogin();
                        }
                    }


                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("获取用户信息失败，请重试");
                    }
                }));
    }

    @Override
    public void exchangeProduct(String jsonContent) {
        mView.showLoading();
        addSubscribe(shoppingCarOrderManager.exchangeProduct(jsonContent)
                .subscribe(new Action1<ExChangeProductResultBean>() {
                    @Override
                    public void call(ExChangeProductResultBean exChangeProductResultBean) {
                        mView.exchangResult(exChangeProductResultBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        handleError(throwable);
                        Logger.e(throwable.getMessage());
                    }
                }));
    }
}
