package com.shawnway.nav.app.wtw.module.mall.productDetail;

import android.app.Activity;

import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.module.mall.shopping_car.bean.ShoppingCarResultBean;

import retrofit2.Response;
import rx.functions.Action1;

/**
 * Created by Cicinnus on 2016/11/14.
 */

@SuppressWarnings("unchecked")
public class ProductDetailPresenter extends BasePresenter<ProductDetailContract.IProductDetailView> implements ProductDetailContract.ProductDetailPresenter {


    private final ProductDetailManager productDetailManager;

    public ProductDetailPresenter(Activity activity, ProductDetailContract.IProductDetailView view) {
        super(activity, view);
        productDetailManager = new ProductDetailManager();
    }

    @Override
    public void getProDeteail(int id) {
        mView.showLoading();
        addSubscribe(productDetailManager.getProDetail(id)
                .subscribe(new Action1<ProDetailBean>() {
                    @Override
                    public void call(ProDetailBean proDetailBean) {
                        mView.showContent();
                        mView.addProDetail(proDetailBean);
                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        handleError(throwable);
                        mView.showError(throwable.getMessage());
                    }
                }));
    }

    @Override
    public void addShoppingCarProduct(int proId, int quantity) {
        mView.adding();
        addSubscribe(productDetailManager.addShoppingCarProduct(proId, quantity)
                .subscribe(new Action1<Response<ShoppingCarResultBean>>() {
                    @Override
                    public void call(Response<ShoppingCarResultBean> response) {
                        if (response.code()==200) {
                            mView.addShoppingCarSuccess(response.body());
                        }else if(response.code()==401){
                            mView.jumpLogin();
                        }
                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        handleError(throwable);
                        mView.showError(throwable.getMessage());
                    }
                }));
    }
}
