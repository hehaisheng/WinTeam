package com.shawnway.nav.app.wtw.module.mall.filter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.module.mall.filter.adapter.FilterProductAdapter;
import com.shawnway.nav.app.wtw.module.mall.filter.adapter.SingleChoiceAdapter;
import com.shawnway.nav.app.wtw.module.mall.filter.bean.FilterListBean;
import com.shawnway.nav.app.wtw.module.mall.filter.bean.FilterProductBean;
import com.shawnway.nav.app.wtw.net.Api;
import com.shawnway.nav.app.wtw.view.DividerItemDecoration;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * Created by Administrator on 2016/10/27.
 * Administrator github = "https://github.com/Cicinnus0407"
 */

public class MallFilterActivity extends BaseActivity<MallFilterPresenter> implements MallFilterContract.MallFilterView, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.progress_layout)
    ProgressLayout progressLayout;
    @BindView(R.id.rv_classification)
    RecyclerView rvClassification;
    @BindView(R.id.rv_filterProduct)
    RecyclerView rvFilterProduct;
    @BindView(R.id.sort_normal)
    TextView sort_normal;
    @BindView(R.id.sort_point)
    TextView sort_point;
    @BindView(R.id.sort_quantity)
    TextView sort_quantity;
    @BindView(R.id.top_text_right)
    ImageView iv_search;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.ll_search_product)
    LinearLayout ll_search_product;
    @BindView(R.id.et_search_product)
    EditText et_search_product;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.iv_search_clear)
    ImageView iv_clear;
    private SingleChoiceAdapter singleChoiceAdapter;
    private FilterProductAdapter filterProductAdapter;
    private LoadingView loadingGif;

    //判断排序是降序还是升序
    private boolean point_down = true;
    private boolean quantity_down = true;
    //查询的页数
    private int page;
    //搜索的关键字
    private String productName = "";
    //搜索的类别
    private int typeId = 0;
    //默认不按照兑换分数排序
    private int sortPointId = 0;
    //默认不按照兑换量排序
    private int sortQuantityId = 0;

    private int currentId = -1;
    private View.OnClickListener click;


    @Override
    protected int getLayout() {
        return R.layout.activity_mall_filter;
    }


    @Override
    protected MallFilterPresenter getPresenter() {
        return new MallFilterPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        loadingGif = new LoadingView(mContext);
        initSearchView();
        initFilterList();
        initFilterProductList();
        mPresenter.getFilterList();
        click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getFilterList();
                mPresenter.queryOrSortProduct(page, productName, typeId, sortPointId, sortQuantityId);
            }
        };
        page = 0;
        mPresenter.queryOrSortProduct(page, "", 0, 0, 0);
    }

    /**
     * 对输入框的监听
     */
    private void initSearchView() {
        et_search_product.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    tv_cancel.setVisibility(View.GONE);
                    tv_search.setVisibility(View.VISIBLE);
                    iv_clear.setVisibility(View.VISIBLE);
                } else {
                    tv_cancel.setVisibility(View.VISIBLE);
                    tv_search.setVisibility(View.GONE);
                    iv_clear.setVisibility(View.GONE);
                }
            }
        });
    }


    /**
     * 初始化左侧的单选列
     */
    private void initFilterList() {
        singleChoiceAdapter = new SingleChoiceAdapter();
        rvClassification.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvClassification.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        rvClassification.setAdapter(singleChoiceAdapter);
        singleChoiceAdapter.setOnFilterListClickListener(new SingleChoiceAdapter.OnFilterListClickListener() {
            @Override
            public void click(FilterListBean.AllProductTypeBean bean) {
                typeId = bean.getTypeId();
                page = 0;
                mPresenter.queryOrSortProduct(page, "", bean.getTypeId(), 0, 0);
            }
        });
    }

    /**
     * 筛选后的产品列表
     */
    private void initFilterProductList() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2, LinearLayoutManager.VERTICAL, false);
        rvFilterProduct.setLayoutManager(gridLayoutManager);
        filterProductAdapter = new FilterProductAdapter();
        rvFilterProduct.setAdapter(filterProductAdapter);
        //加载更多
        filterProductAdapter.setOnLoadMoreListener(this);
    }

    @OnClick({
            R.id.top_back,
            R.id.sort_normal,
            R.id.sort_point,
            R.id.sort_quantity,
            R.id.top_text_right,
            R.id.tv_cancel,
            R.id.tv_search,
            R.id.iv_search_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.sort_normal:
                setSortBg(sort_normal.getId());
                break;
            case R.id.sort_point:
                setSortBg(sort_point.getId());
                break;
            case R.id.sort_quantity:
                setSortBg(sort_quantity.getId());
                break;
            case R.id.top_text_right:
                showSearchView();
                break;
            case R.id.tv_cancel:
                hideSearchView();
                break;
            case R.id.tv_search:
                page = 0;
                productName = et_search_product.getText().toString();
                sortPointId = 0;
                sortQuantityId = 0;
                mPresenter.searchProduct(page, productName, typeId);
                break;
            case R.id.iv_search_clear:
                et_search_product.setText("");
                page = 0;
                productName = et_search_product.getText().toString();
                sortPointId = 0;
                sortQuantityId = 0;
                break;

        }
    }

    /**
     * 隐藏搜索栏
     */
    private void hideSearchView() {
        Observable
                .just(ll_search_product.getVisibility())
                .map(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer == View.VISIBLE;
                    }
                })
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        ll_search_product.setVisibility(View.INVISIBLE);
                        iv_search.setVisibility(View.VISIBLE);
                        tv_cancel.setVisibility(View.GONE);
                        page = 0;
                        mPresenter.queryOrSortProduct(page, "", typeId, 0, 0);
                    }
                });

    }

    /**
     * 显示搜索框
     */
    private void showSearchView() {
        Observable
                .just(ll_search_product.getVisibility())
                .map(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer == View.INVISIBLE;
                    }
                })
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        ll_search_product.setVisibility(View.VISIBLE);
                        iv_search.setVisibility(View.GONE);
                        tv_cancel.setVisibility(View.VISIBLE);
                    }
                });
    }

    /**
     * 加载左侧分类列表
     *
     * @param filterList 分类列表
     */
    @Override
    public void addFilterList(List<FilterListBean.AllProductTypeBean> filterList) {
        List<FilterListBean.AllProductTypeBean> data = new ArrayList<>();
        FilterListBean.AllProductTypeBean bean = new FilterListBean.AllProductTypeBean();
        bean.setTypeId(0);
        bean.setAgentId(Api.AGENT_ID);
        bean.setTypeName("全部商品");
        bean.isSelect = true;
        data.add(0, bean);
        for (int i = 0; i < filterList.size(); i++) {
            FilterListBean.AllProductTypeBean filter = filterList.get(i);
            filter.setTypeName(filterList.get(i).getTypeName());
            filter.isSelect = false;
            data.add(filter);
        }
        singleChoiceAdapter.addData(data);
    }


    /**
     * 根据分类加载的数据
     *
     * @param dataList 分类数据
     */
    @Override
    public void addSortProduct(List<FilterProductBean.AllProductEntityBean> dataList) {
        if (dataList.size() > 0) {
            if (currentId != typeId||page==0) {
                currentId = typeId;
                List<FilterProductBean.AllProductEntityBean> emptyList = new ArrayList<>();
                filterProductAdapter.setNewData(emptyList);
            }
            filterProductAdapter.addData(dataList);
            page += 1;

        } else {
            if(page==0){
                filterProductAdapter.setNewData(new ArrayList<FilterProductBean.AllProductEntityBean>());
            }
            filterProductAdapter.loadComplete();

        }
    }

    /**
     * 页面加载成功
     */
    @Override
    public void loadAllSuccess() {
        if (!progressLayout.isContent()) progressLayout.showContent();
        loadingGif.dismiss();
    }

    /**
     * 默认排序数据
     *
     * @param normalSortList 默认排序
     */
    @Override
    public void addNormalSort(List<FilterProductBean.AllProductEntityBean> normalSortList) {
        if (normalSortList.size() > 0) {
            if (page == 0) {
                List<FilterProductBean.AllProductEntityBean> emptyList = new ArrayList<>();
                filterProductAdapter.setNewData(emptyList);
            }
            filterProductAdapter.addData(normalSortList);
            page += 1;

        } else {
            filterProductAdapter.loadComplete();

        }
    }

    /**
     * 积分排序数据
     *
     * @param productList 积分排序
     */
    @Override
    public void addPointSort(List<FilterProductBean.AllProductEntityBean> productList) {
        if (productList.size() > 0) {
            if (page == 0) {
                List<FilterProductBean.AllProductEntityBean> emptyList = new ArrayList<>();
                filterProductAdapter.setNewData(emptyList);
            }
            filterProductAdapter.addData(productList);
            page += 1;

        } else {
            filterProductAdapter.loadComplete();

        }
    }

    /**
     * 兑换量排序
     *
     * @param productList 兑换量
     */
    @Override
    public void addQuantitySort(List<FilterProductBean.AllProductEntityBean> productList) {
        if (productList.size() > 0) {
            if (page == 0) {
                List<FilterProductBean.AllProductEntityBean> emptyList = new ArrayList<>();
                filterProductAdapter.setNewData(emptyList);
            }
            filterProductAdapter.addData(productList);
            page += 1;

        } else {
            filterProductAdapter.loadComplete();

        }
    }

    @Override
    public void addSearchProduct(List<FilterProductBean.AllProductEntityBean> searchProductList) {
        if (searchProductList.size() > 0) {
            if (page == 0) {
                List<FilterProductBean.AllProductEntityBean> emptyList = new ArrayList<>();
                filterProductAdapter.setNewData(emptyList);
            }
            filterProductAdapter.addData(searchProductList);
            page += 1;

        } else {
            filterProductAdapter.setNewData(new ArrayList<FilterProductBean.AllProductEntityBean>());
            filterProductAdapter.loadComplete();

        }
    }


    @Override
    public void showLoading() {
        loadingGif.show();
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(String errorMsg) {
        loadingGif.dismiss();
        progressLayout.showError(click);
    }

    /**
     * 切换排序的按钮
     *
     * @param id
     */
    private void setSortBg(int id) {

        switch (id) {
            case R.id.sort_normal:
                page = 0;
                sortPointId = 0;
                sortQuantityId = 0;
                sort_normal.setTextColor(getResources().getColor(R.color.textColor_yellow));
                sort_point.setTextColor(getResources().getColor(R.color.text_gray));
                sort_quantity.setTextColor(getResources().getColor(R.color.text_gray));
                Drawable drawable_normal = getResources().getDrawable(R.drawable.ris_no);
                drawable_normal.setBounds(0, 0, drawable_normal.getMinimumWidth(), drawable_normal.getMinimumHeight());
                sort_point.setCompoundDrawables(null, null, drawable_normal, null);
                sort_quantity.setCompoundDrawables(null, null, drawable_normal, null);
                mPresenter.sortByNormal(page, productName, typeId);
                break;
            case R.id.sort_point:
                //排序
                page = 0;
                sortQuantityId = 0;
                sort_point.setTextColor(getResources().getColor(R.color.textColor_yellow));
                sort_normal.setTextColor(getResources().getColor(R.color.text_gray));
                sort_quantity.setTextColor(getResources().getColor(R.color.text_gray));
                if (point_down) {
                    point_down = false;
                    Drawable drawable = getResources().getDrawable(R.drawable.ris);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    sort_point.setCompoundDrawables(null, null, drawable, null);
                    sortPointId = 1;
                    //降序搜索
                    mPresenter.sortByPoint(page, productName, typeId, sortPointId);
                } else {
                    point_down = true;
                    Drawable drawable = getResources().getDrawable(R.drawable.dow);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    sort_point.setCompoundDrawables(null, null, drawable, null);
                    sortPointId = 2;
                    //升序搜索
                    mPresenter.sortByPoint(page, productName, typeId, sortPointId);
                }
                Drawable drawable_quantity = getResources().getDrawable(R.drawable.ris_no);
                drawable_quantity.setBounds(0, 0, drawable_quantity.getMinimumWidth(), drawable_quantity.getMinimumHeight());
                sort_quantity.setCompoundDrawables(null, null, drawable_quantity, null);
                break;
            case R.id.sort_quantity:
                page = 0;
                sortPointId = 0;
                sort_quantity.setTextColor(getResources().getColor(R.color.textColor_yellow));
                sort_normal.setTextColor(getResources().getColor(R.color.text_gray));
                sort_point.setTextColor(getResources().getColor(R.color.text_gray));
                if (quantity_down) {
                    Drawable drawable = getResources().getDrawable(R.drawable.ris);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    quantity_down = false;
                    sort_quantity.setCompoundDrawables(null, null, drawable, null);
                    sortQuantityId = 1;
                    mPresenter.sortByQuantity(page, productName, typeId, sortQuantityId);
                } else {
                    quantity_down = true;
                    Drawable drawable = getResources().getDrawable(R.drawable.dow);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    sort_quantity.setCompoundDrawables(null, null, drawable, null);
                    sortQuantityId = 2;
                    mPresenter.sortByQuantity(page, productName, typeId, sortQuantityId);
                }
                Drawable drawable_point = getResources().getDrawable(R.drawable.ris_no);
                drawable_point.setBounds(0, 0, drawable_point.getMinimumWidth(), drawable_point.getMinimumHeight());
                sort_point.setCompoundDrawables(null, null, drawable_point, null);
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.queryOrSortProduct(page, productName, typeId, sortPointId, sortQuantityId);
    }
}
