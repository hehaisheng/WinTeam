package com.shawnway.nav.app.wtw.module.mall.filter;

import android.app.Activity;

import com.orhanobut.logger.Logger;
import com.shawnway.nav.app.wtw.base.BasePresenter;
import com.shawnway.nav.app.wtw.module.mall.filter.bean.FilterListBean;
import com.shawnway.nav.app.wtw.module.mall.filter.bean.FilterProductBean;

import rx.functions.Action1;

/**
 * Created by Cicinnus on 2016/11/9.
 */

@SuppressWarnings("unchecked")
public class MallFilterPresenter extends BasePresenter<MallFilterContract.MallFilterView> implements MallFilterContract.MallPresenter {

    private final MallFilterManager mallFilterManager;
    private boolean filter = false;
    private boolean filterProduct = false;

    public MallFilterPresenter(Activity activity, MallFilterContract.MallFilterView view) {
        super(activity, view);
        mallFilterManager = new MallFilterManager();
    }

    @Override
    public void getFilterList() {
        mView.showLoading();
        addSubscribe(mallFilterManager.getMallFilterList()
                .subscribe(new Action1<FilterListBean>() {
                    @Override
                    public void call(FilterListBean allProductTypeBean) {
                        mView.addFilterList(allProductTypeBean.getAllProductType());
                        filter = true;
                        loadAll();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("获取商品分类失败");
                        Logger.e("获取分类列表失败" + throwable.getMessage());
                    }
                }));
    }


    @Override
    public void queryOrSortProduct(final int page, String productName, int typeId, int sortPointId, int sortQuantityId) {
        addSubscribe(mallFilterManager.queryOrSortProduct(page, productName, typeId, sortPointId, sortQuantityId)
                .subscribe(new Action1<FilterProductBean>() {
                    @Override
                    public void call(FilterProductBean filterProductBean) {
                        mView.addSortProduct(filterProductBean.getAllProductEntity());
                        filterProduct = true;
                        loadAll();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("查询失败");
                    }
                }));
    }

    @Override
    public void sortByPoint(int page, String productName, int typeId, int sortPointId) {
        addSubscribe(mallFilterManager.sortByPoint(page, productName, typeId, sortPointId)
                .subscribe(new Action1<FilterProductBean>() {
                    @Override
                    public void call(FilterProductBean filterProductBean) {
                        mView.addPointSort(filterProductBean.getAllProductEntity());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        handleError(throwable);
                    }
                }));
    }

    @Override
    public void sortByQuantity(int page, String productName, int typeId, int sortQuantityId) {
        addSubscribe(mallFilterManager.sortByQuantity(page, productName, typeId, sortQuantityId)
                .subscribe(new Action1<FilterProductBean>() {
                    @Override
                    public void call(FilterProductBean filterProductBean) {
                        mView.addQuantitySort(filterProductBean.getAllProductEntity());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        handleError(throwable);
                    }
                }));
    }

    @Override
    public void sortByNormal(int page, String productName, int typeId) {
        addSubscribe(mallFilterManager.sortByNormal(page, productName, typeId)
                .subscribe(new Action1<FilterProductBean>() {
                    @Override
                    public void call(FilterProductBean filterProductBean) {
                        mView.addNormalSort(filterProductBean.getAllProductEntity());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        handleError(throwable);
                    }
                }));
    }

    @Override
    public void searchProduct(int page, String keyWords, int typeId) {
        addSubscribe(mallFilterManager.searchProduct(page,keyWords,typeId)
        .subscribe(new Action1<FilterProductBean>() {
            @Override
            public void call(FilterProductBean filterProductBean) {
                mView.addSearchProduct(filterProductBean.getAllProductEntity());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                handleError(throwable);
            }
        }));
    }

    @Override
    public void loadAll() {
        if (filter && filterProduct) {
            mView.loadAllSuccess();
        }
    }
}
