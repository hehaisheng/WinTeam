package com.shawnway.nav.app.wtw.module.mall.shopping_car;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.module.mall.shopping_car.bean.ShoppingCarListBean;
import com.shawnway.nav.app.wtw.module.mall.shopping_car.bean.ShoppingCarResultBean;

import java.io.IOException;

import retrofit2.Response;
import rx.functions.Action1;

/**
 * Created by Cicinnus on 2016/11/16.
 */

@SuppressWarnings("unchecked")
public class ShoppingCarPresenter extends BasePresenter<ShoppingCarContract.IShoppingCarView> implements ShoppingCarContract.IShoppingCarPresenter {


    private final ShoppingCarManager shoppingCarManager;

    public ShoppingCarPresenter(Activity activity, ShoppingCarContract.IShoppingCarView view) {
        super(activity, view);
        shoppingCarManager = new ShoppingCarManager();
    }

    @Override
    public void getShoppingCarList() {
        mView.showLoading();
        addSubscribe(shoppingCarManager.getShoppingCarList()
                .subscribe(new BaseSubscriber<Response<ShoppingCarListBean>>() {
                    @Override
                    public void onSuccess(Response<ShoppingCarListBean> response) {
                        if (response.code() == 200) {
                            mView.showContent();
                            mView.addShoppingCarList(response.body().getShoppingEntityList());
                        } else if (response.code() == 401) {
                            mView.jumpLogin();
                        } else {
                            try {
                                mView.showError(response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError("");
                    }
                }));
    }

    @Override
    public void updateShoppingCarProduct(int proId, int quantity) {
        mView.showLoading();
        addSubscribe(shoppingCarManager.updateShoppingCarProduct(proId, quantity)
                .subscribe(new Action1<ShoppingCarResultBean>() {
                    @Override
                    public void call(ShoppingCarResultBean bean) {

                        mView.updateShoppingCarSuccess(bean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        handleError(throwable);
                    }
                }));
    }

    @Override
    public void deleteShoppingCarProduct(int id) {
        mView.showLoading();
        addSubscribe(shoppingCarManager.deleteShoppingCarProduct(id)
                .subscribe(new Action1<ShoppingCarResultBean>() {
                    @Override
                    public void call(ShoppingCarResultBean bean) {
                        mView.deleteProDuctSuccess(bean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        handleError(throwable);
                    }
                }));
    }
}
