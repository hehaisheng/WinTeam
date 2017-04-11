package com.shawnway.nav.app.wtw.module.mall.productDetail;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.base.IPresenter;
import com.shawnway.nav.app.wtw.module.mall.shopping_car.bean.ShoppingCarResultBean;

/**
 * Created by Cicinnus on 2016/11/14.
 */

public class ProductDetailContract {

    public interface IProductDetailView extends ILoadingView{
        void addProDetail(ProDetailBean proDetailBean);

        void adding();

        void addShoppingCarSuccess(ShoppingCarResultBean bean);

        void jumpLogin();
    }

    public interface ProductDetailPresenter  extends IPresenter{
        void getProDeteail(int id);

        void addShoppingCarProduct(int proId,int quantity);
    }
}
