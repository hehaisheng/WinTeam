package com.shawnway.nav.app.wtw.module.mall.shopping_car;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.module.mall.order.ShoppingCarOrderActivity;
import com.shawnway.nav.app.wtw.module.mall.productDetail.ProductDetailActivity;
import com.shawnway.nav.app.wtw.module.mall.shopping_car.bean.ShoppingCarListBean;
import com.shawnway.nav.app.wtw.module.mall.shopping_car.bean.ShoppingCarResultBean;
import com.shawnway.nav.app.wtw.view.DividerItemDecoration;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Cicinnus on 2016/11/16.
 */

public class ShoppingCarActivity extends BaseActivity<ShoppingCarPresenter> implements ShoppingCarContract.IShoppingCarView, ShoppingCarListAdapter.OnShoppingProductCheckedChangedListener {


    public static void start(Context context) {
        Intent starter = new Intent(context, ShoppingCarActivity.class);
        context.startActivity(starter);
    }


    @BindView(R.id.progress_layout)
    ProgressLayout progressLayout;
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.rv_shopping_car_list)
    RecyclerView rvShoppingCarList;
    @BindView(R.id.tv_check_all)
    TextView tvCheckAll;
    @BindView(R.id.conclusion)
    TextView conclusion;
    @BindView(R.id.total_point)
    TextView totalPoint;


    private Handler handler;
    private ShoppingCarListAdapter carAdapter;
    private LoadingView loadingGif;
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.getShoppingCarList();
        }
    };
    private ArrayList<ShoppingCarListBean.ShoppingEntityListBean> dataList = new ArrayList<>();
    private ArrayList<ShoppingCarListBean.ShoppingEntityListBean> newDataList = new ArrayList<>();
    private boolean isAllCheck = true;
    private boolean isFirst = true;

    @Override
    protected int getLayout() {
        return R.layout.activity_shopping_car;
    }

    @Override
    protected ShoppingCarPresenter getPresenter() {
        return new ShoppingCarPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        topTextCenter.setText("购物车");
        setVisiable(topBack, topTextCenter, toolbar);
        initRecyclerView();
        loadingGif = new LoadingView(mContext);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataList.clear();
        mPresenter.getShoppingCarList();
    }

    private void initRecyclerView() {
        carAdapter = new ShoppingCarListAdapter();
        rvShoppingCarList.setLayoutManager(new LinearLayoutManager(mContext));
        rvShoppingCarList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        rvShoppingCarList.setAdapter(carAdapter);
        View view = getLayoutInflater().inflate(R.layout.layout_no_data, (ViewGroup) rvShoppingCarList.getParent(), false);
        ((TextView) view.findViewById(R.id.no_data_text)).setText("购物车是空的~");
        carAdapter.setEmptyView(view);

        /**
         * 添加商品数量
         */
        carAdapter.setOnAddQuantityClickListener(new ShoppingCarListAdapter.OnAddQuantityClickListener() {
            @Override
            public void onClick(ShoppingCarListBean.ShoppingEntityListBean bean) {
                mPresenter.updateShoppingCarProduct(bean.getProId(), bean.getQuantity() + 1);
            }
        });

        /**
         * 减少商品数量
         */
        carAdapter.setOnReduceQuantityClickListener(new ShoppingCarListAdapter.OnReduceQuantityClickListener() {
            @Override
            public void onClick(ShoppingCarListBean.ShoppingEntityListBean bean) {
                if (bean.getQuantity() >= 2) {
                    mPresenter.updateShoppingCarProduct(bean.getProId(), bean.getQuantity() - 1);
                }
            }
        });

        /**
         * 删除商品
         */
        carAdapter.setOnDeleteShoppingCarProductClickListener(new ShoppingCarListAdapter.OnDeleteShoppingCarProductClickListener() {
            @Override
            public void onClick(int proId) {
                mPresenter.deleteShoppingCarProduct(proId);
            }
        });

        /**
         * 选中商品
         */
        carAdapter.setOnShoppingProductCheckedChangedListener(this);

        /**
         * 跳转商品详情页
         */
        carAdapter.setOnProductImgClickListener(new ShoppingCarListAdapter.OnProductImgClickListener() {
            @Override
            public void onClick(int proId) {
                ProductDetailActivity.start(mContext, proId);
                finish();
            }
        });
        carAdapter.setOnProductNameClickListener(new ShoppingCarListAdapter.OnProductNameClickListener() {
            @Override
            public void onClick(int proId) {
                ProductDetailActivity.start(mContext, proId);
                finish();
            }
        });


    }

    @OnClick({R.id.top_back, R.id.conclusion, R.id.tv_check_all})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.conclusion://结算
                ShoppingCarOrderActivity.start(mContext, dataList);
                break;
            case R.id.tv_check_all:
                if (isAllCheck) {
                    for (int i = 0; i < dataList.size(); i++) {
                        dataList.get(i).setChecked(true);
                    }
                    carAdapter.notifyDataSetChanged();
                    Drawable drawable = getResources().getDrawable(R.drawable.sel);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tvCheckAll.setCompoundDrawables(drawable, null, null, null);

                } else {
                    for (int i = 0; i < dataList.size(); i++) {
                        dataList.get(i).setChecked(false);
                    }
                    carAdapter.notifyDataSetChanged();
                    Drawable drawable = getResources().getDrawable(R.drawable.uns);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    tvCheckAll.setCompoundDrawables(drawable, null, null, null);
                }
                break;
        }
    }


    @Override
    public void addShoppingCarList(List<ShoppingCarListBean.ShoppingEntityListBean> beanList) {

        if (beanList.size() == 0) {
            Drawable drawable = getResources().getDrawable(R.drawable.uns);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvCheckAll.setCompoundDrawables(drawable, null, null, null);
            totalPoint.setText(String.format("合计：%s积分", 0));
            conclusion.setText(String.format("结算（%s）", 0));
        }
        if (isFirst) {
            dataList.addAll(beanList);
            carAdapter.setNewData(dataList);
        } else {
            newDataList.clear();
            for (int i = 0; i < beanList.size(); i++) {
                for (int j = 0; j < dataList.size(); j++) {
                    if (beanList.get(i).getProId() == dataList.get(j).getProId()) {
                        ShoppingCarListBean.ShoppingEntityListBean shoppingBean = new ShoppingCarListBean.ShoppingEntityListBean();
                        shoppingBean.setImg(dataList.get(j).getImg());
                        shoppingBean.setProDiscCsptPoint(dataList.get(j).getProDiscCsptPoint());
                        shoppingBean.setProName(dataList.get(j).getProName());
                        shoppingBean.setDesc(dataList.get(j).getDesc());
                        shoppingBean.setProId(dataList.get(j).getProId());
                        shoppingBean.setQuantity(beanList.get(i).getQuantity());
                        if (dataList.get(j).isChecked()) {
                            shoppingBean.setChecked(true);

                        }
                        newDataList.add(shoppingBean);
                    }
                }
            }
            dataList.clear();
            dataList.addAll(newDataList);
            carAdapter.setNewData(newDataList);
            int count = 0;
            int point = 0;
            for (int i = 0; i < newDataList.size(); i++) {
                if (newDataList.get(i).isChecked()) {
                    count += 1;
                    point += newDataList.get(i).getProDiscCsptPoint() * newDataList.get(i).getQuantity();
                }
            }
            totalPoint.setText(String.format("合计：%s积分", point));
            conclusion.setText(String.format("结算（%s）", count));
        }
    }

    @Override
    public void updateShoppingCarSuccess(ShoppingCarResultBean bean) {
        if ("200".equals(bean.getStatus())) {
            mPresenter.getShoppingCarList();
            isFirst = false;
        }
    }

    @Override
    public void deleteProDuctSuccess(ShoppingCarResultBean bean) {
        if ("200".equals(bean.getStatus())) {
            mPresenter.getShoppingCarList();
            isFirst = false;
        }
    }

    @Override
    public void jumpLogin() {
        LoginActivity.getInstance(mContext);
        finish();
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
        loadingGif.dismiss();
        if (progressLayout.isContent()) progressLayout.showContent();

    }

    @Override
    public void showError(String errorMsg) {
        loadingGif.dismiss();
        progressLayout.showError(click);
    }

    @Override
    public void onChanged(int position, boolean checked, ShoppingCarListBean.ShoppingEntityListBean bean) {
        if (checked) {
            dataList.get(position).setChecked(true);
        } else {
            dataList.get(position).setChecked(false);
        }
        int count = 0;
        int point = 0;
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).isChecked()) {
                count += 1;
                point += dataList.get(i).getProDiscCsptPoint() * dataList.get(i).getQuantity();
            }
        }
        if (count == dataList.size()) {
            isAllCheck = false;
            Drawable drawable = getResources().getDrawable(R.drawable.sel);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvCheckAll.setCompoundDrawables(drawable, null, null, null);
        } else {
            isAllCheck = true;
            Drawable drawable = getResources().getDrawable(R.drawable.uns);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvCheckAll.setCompoundDrawables(drawable, null, null, null);
        }
        totalPoint.setText(String.format("合计：%s积分", point));
        conclusion.setText(String.format("结算（%s）", count));
    }
}
