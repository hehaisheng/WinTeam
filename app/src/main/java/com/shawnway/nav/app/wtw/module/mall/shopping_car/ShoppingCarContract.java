package com.shawnway.nav.app.wtw.module.mall.shopping_car;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.module.mall.shopping_car.bean.ShoppingCarListBean;
import com.shawnway.nav.app.wtw.module.mall.shopping_car.bean.ShoppingCarResultBean;

import java.util.List;

/**
 * Created by Cicinnus on 2016/11/16.
 */

public class ShoppingCarContract {
    public interface IShoppingCarView extends ILoadingView{
        void addShoppingCarList(List<ShoppingCarListBean.ShoppingEntityListBean> shoppingEntityListBeanList);

        void updateShoppingCarSuccess(ShoppingCarResultBean bean);

        void deleteProDuctSuccess(ShoppingCarResultBean bean);

        void jumpLogin();
    }
    public interface IShoppingCarPresenter{
        void getShoppingCarList();

        void updateShoppingCarProduct(int proId,int quantity);

        void deleteShoppingCarProduct(int id);
    }

}
