package com.shawnway.nav.app.wtw.module.mall.filter;

import com.shawnway.nav.app.wtw.base.ILoadingView;
import com.shawnway.nav.app.wtw.base.IPresenter;
import com.shawnway.nav.app.wtw.module.mall.filter.bean.FilterListBean;
import com.shawnway.nav.app.wtw.module.mall.filter.bean.FilterProductBean;

import java.util.List;

/**
 * Created by Cicinnus on 2016/11/9.
 */

public class MallFilterContract {

    interface MallFilterView extends ILoadingView {
        void addFilterList(List<FilterListBean.AllProductTypeBean> filterList);

        void addSortProduct(List<FilterProductBean.AllProductEntityBean> allProductEntityBeanList);

        void loadAllSuccess();

        void addNormalSort(List<FilterProductBean.AllProductEntityBean> normalSortList);

        void addPointSort(List<FilterProductBean.AllProductEntityBean> firstProductList);

        void addQuantitySort(List<FilterProductBean.AllProductEntityBean> firstProductList);

        void addSearchProduct(List<FilterProductBean.AllProductEntityBean> searchProductList);


    }

    interface MallPresenter extends IPresenter {
        void getFilterList();

        void queryOrSortProduct(int page,String productName,int typeId,int sortPointId,int sortQuantityId);

        void sortByPoint(int page,String productName,int typeId,int sortPointId);

        void sortByQuantity(int page,String productName,int typeId,int sortQuantityId);

        void sortByNormal(int page,String productName,int typeId);

        void searchProduct(int page,String keyWords,int typeId);

        void loadAll();

    }
}
