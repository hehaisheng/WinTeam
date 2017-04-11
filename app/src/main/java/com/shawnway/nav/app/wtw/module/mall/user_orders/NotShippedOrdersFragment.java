package com.shawnway.nav.app.wtw.module.mall.user_orders;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.module.mall.bean.MallOrders;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Kevin on 2016/11/16
 */

public class NotShippedOrdersFragment extends BaseFragment<UserOrdersPresenter> implements UserOrdersContract.IUserOrderView, BaseQuickAdapter.RequestLoadMoreListener {
    /*@BindView(R.id.swipe_orders_refresh)
    SwipeRefreshLayout swipeOrdersRefresh;*/
    private AllUserOrdersAdapter allUserOrdersAdapter;
    private int PAGE_NO = 0;

    private List<MallOrders.ProductOrderTransactionsBean> list;
    //private View loadingView;
    private MallOrders searchCondition;
    private View layoutNodata;


    public static NotShippedOrdersFragment newInstance() {

        Bundle args = new Bundle();

        NotShippedOrdersFragment fragment = new NotShippedOrdersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.rv_not_shipped_orders)
    RecyclerView rvNotShippedOrders;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_not_shippedorders;
    }

    @Override
    protected void initEventAndData() {
        iniView();
        initRecycleView();
        initListener();
        getOrdersData(PAGE_NO, Code.PAGE_SIZE, Code.TRADE_STATUS_NOSHIPPED, Code.SIGN_STATUS_NOHANDLE);
    }


    private void initListener() {
        allUserOrdersAdapter.setOnLoadMoreListener(this);

    }

    @Override
    protected UserOrdersPresenter getPresenter() {
        return new UserOrdersPresenter(mContext, this);
    }

    /**
     * 获取用户积分商城所有订单
     */
    private void getOrdersData(int pageNo, int pageSize, int tradeStatus, int signStatus) {
        searchCondition = new MallOrders();
        searchCondition.setPageNo(pageNo);
        searchCondition.setPageSize(pageSize);
        searchCondition.setTradeStatus(tradeStatus);
        searchCondition.setSignStatus(signStatus);
        mPresenter.getMallOrders(searchCondition);
    }

    private void iniView() {
        //loadingView = mContext.getLayoutInflater().inflate(R.layout.layout_loading_more, (ViewGroup) rvNotShippedOrders.getParent(), false);
        layoutNodata = mContext.getLayoutInflater().inflate(R.layout.layout_no_data, (ViewGroup) rvNotShippedOrders.getParent(), false);
        TextView textView = (TextView) layoutNodata.findViewById(R.id.no_data_text);
        textView.setText("暂无订单信息");
    }

    /**
     * 初始化订单RV
     */
    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvNotShippedOrders.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        allUserOrdersAdapter = new AllUserOrdersAdapter(R.layout.item_mall_userorders,list);
        rvNotShippedOrders.setAdapter(allUserOrdersAdapter);
       // allUserOrdersAdapter.setLoadingView(loadingView);
        allUserOrdersAdapter.setEmptyView(layoutNodata);
        //swipeOrdersRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onLoadMoreRequested() {
        getOrdersData(PAGE_NO, Code.PAGE_SIZE, Code.TRADE_STATUS_NOSHIPPED, Code.SIGN_STATUS_NOHANDLE);
    }

    @Override
    public void addMallOrders(MallOrders mallOrders) {
        /*if (swipeOrdersRefresh.isRefreshing()) {
            swipeOrdersRefresh.setRefreshing(false);
        }*/
        if (mallOrders.getProductOrderTransactions().size() > 0) {
            allUserOrdersAdapter.addData(mallOrders.getProductOrderTransactions());
            PAGE_NO++;
        } else allUserOrdersAdapter.loadComplete();
    }


    @Override
    public void onResult(MallOrders mallOrders) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(String errorMsg) {

    }
}
