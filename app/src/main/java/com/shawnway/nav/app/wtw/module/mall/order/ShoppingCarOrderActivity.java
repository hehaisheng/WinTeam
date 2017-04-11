package com.shawnway.nav.app.wtw.module.mall.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.bean.UserPointResult;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.module.mall.address.AddressListActivity;
import com.shawnway.nav.app.wtw.module.mall.address.bean.DefaultAddressResultBean;
import com.shawnway.nav.app.wtw.module.mall.shopping_car.bean.ShoppingCarListBean;
import com.shawnway.nav.app.wtw.tool.GlideManager;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.tool.Utils;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Cicinnus on 2016/11/17.
 */

public class ShoppingCarOrderActivity extends BaseActivity<ShoppingCarOrderPresenter> implements ShoppingCarOrderContract.IShoppingCarOrderView {


    private ExChangeProductRequestBean rootBean;
    private int point;
    private ShoppingCarOrderListAdapter listAdapter;

    public static void start(Context context, ArrayList<ShoppingCarListBean.ShoppingEntityListBean> data) {
        Intent starter = new Intent(context, ShoppingCarOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("data", data);
        starter.putExtra("bundle", bundle);
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
    @BindView(R.id.consignee)
    TextView consignee;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.rv_order_list)
    RecyclerView rvOrderList;
    @BindView(R.id.totalPoint)
    TextView totalPoint;
    @BindView(R.id.totalProductAmount)
    TextView totalProductAmount;
    @BindView(R.id.conclusion)
    TextView conclusion;
    @BindView(R.id.orderPoint)
    TextView orderPoint;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.userPoint)
    TextView userPoint;
    @BindView(R.id.remark)
    EditText remark;
    @BindView(R.id.without_address)
    TextView without_address;

    private ArrayList<ShoppingCarListBean.ShoppingEntityListBean> dataList = new ArrayList<>();
    private ArrayList<ShoppingCarListBean.ShoppingEntityListBean> orderList = new ArrayList<>();
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.getAddressAndPoint();
        }
    };
    private LoadingView loadingGif;

    @Override
    protected int getLayout() {
        return R.layout.activity_shopping_car_order_activity;
    }

    @Override
    protected ShoppingCarOrderPresenter getPresenter() {
        return new ShoppingCarOrderPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            dataList = bundle.getParcelableArrayList("data");
        }

        topTextCenter.setText("确认订单");
        setVisiable(topBack, toolbar, topTextCenter);

        initOrderList();
        initRecyclerView();
        initPoint();
        loadingGif = new LoadingView(mContext);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getAddressAndPoint();
    }

    /**
     * 计算所需积分
     */
    private void initPoint() {
        point = 0;
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).isChecked()) {
                point += dataList.get(i).getProDiscCsptPoint() * dataList.get(i).getQuantity();
            }
        }
        orderPoint.setText(String.format("合计：%s", point));
        Spannable span = new SpannableString(orderPoint.getText());
        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor_yellow)), 3, orderPoint.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        orderPoint.setText(span);
    }

    /**
     * 下单的json
     */
    private void initOrderList() {
        rootBean = new ExChangeProductRequestBean();
        rootBean.setShoppingCart(1);
        List<ExChangeProductRequestBean.ProductOrderListBean> beanList = new ArrayList<>();
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                if (dataList.get(i).isChecked()) {
                    orderList.add(dataList.get(i));
                    ExChangeProductRequestBean.ProductOrderListBean bean = new ExChangeProductRequestBean.ProductOrderListBean();
                    bean.setProId(String.format("%s", dataList.get(i).getProId()));
                    bean.setAmount(String.format("%s", dataList.get(i).getQuantity()));
                    beanList.add(bean);
                }
            }
            rootBean.setProductOrderList(beanList);
        }

    }

    @OnClick({R.id.top_back,
            R.id.rl_defaultAddress,
            R.id.conclusion})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.rl_defaultAddress:
                AddressListActivity.start(mContext);
                break;
            case R.id.conclusion:
                if (remark.getText().length() > 0) {
                    rootBean.setRemark(remark.getText().toString().trim());
                }
                String orderJsonContent = new Gson().toJson(rootBean);
                mPresenter.exchangeProduct(orderJsonContent);
                break;
        }
    }

    /**
     * 商品列表
     */
    private void initRecyclerView() {
        listAdapter = new ShoppingCarOrderListAdapter();
        rvOrderList.setNestedScrollingEnabled(false);
        rvOrderList.setLayoutManager(new LinearLayoutManager(mContext));
        rvOrderList.setAdapter(listAdapter);
        listAdapter.setNewData(orderList);

    }


    @Override
    public void addDefaultAddress(DefaultAddressResultBean bean) {
        if (bean.getOrderAddress() == null) {
            without_address.setVisibility(View.VISIBLE);
        } else {
            without_address.setVisibility(View.GONE);
            consignee.setText(String.format("收货人：%s", bean.getOrderAddress().getConsignee()));
            phone.setText(String.format("%s", Utils.setPhoneNum(bean.getOrderAddress().getCellphone())));
            address.setText(String.format("收货地址：%s", bean.getOrderAddress().getAddress()));
        }

    }

    @Override
    public void addUserPoint(UserPointResult o) {
        userPoint.setText(String.format("剩余：%s", o.getPoint()));
        Spannable span = new SpannableString(userPoint.getText());
        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor_yellow)),
                3,
                userPoint.getText().length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        userPoint.setText(span);
    }

    @Override
    public void exchangResult(ExChangeProductResultBean bean) {
        loadingGif.dismiss();
        if ("SUCCESS".equals(bean.getStatusCode())) {
            ShoppingCarOrderSuccessActivity.start(mContext, point);
            finish();
        }
        ToastUtil.showShort(mContext, bean.getDesc());
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
        if (!progressLayout.isContent()) {
            progressLayout.showContent();
        }
    }

    @Override
    public void showError(String errorMsg) {
        loadingGif.dismiss();
        ToastUtil.showShort(mContext, errorMsg);
        progressLayout.showError(click);

    }


    private class ShoppingCarOrderListAdapter extends BaseQuickAdapter<ShoppingCarListBean.ShoppingEntityListBean> {

        public ShoppingCarOrderListAdapter() {
            super(R.layout.item_shopping_car_order_list, null);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, ShoppingCarListBean.ShoppingEntityListBean bean) {
            baseViewHolder
                    .setText(R.id.productName, String.format("%s", bean.getProName()))
                    .setText(R.id.proPoint, String.format("积分：%s", bean.getProDiscCsptPoint()))
                    .setText(R.id.proQuantity, String.format("x%s", bean.getQuantity()));

            ImageView iv = baseViewHolder.getView(R.id.productPic);
            GlideManager.loadImage(mContext, bean.getImg(), iv);
        }
    }

}
