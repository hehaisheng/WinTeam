package com.shawnway.nav.app.wtw.module.mall.user_orders;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.module.mall.bean.MallOrders;
import com.shawnway.nav.app.wtw.module.mall.express.ExpressDeliverActivity;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.NormalDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Kevin on 2016/11/16
 */

public class NotReceivedOrdersFragment extends BaseFragment<UserOrdersPresenter> implements UserOrdersContract.IUserOrderView, BaseQuickAdapter.RequestLoadMoreListener {
    /*@BindView(R.id.swipe_orders_refresh)
    SwipeRefreshLayout swipeOrdersRefresh;*/
    private AllUserOrdersAdapter notReceivedOrdersAdapter;
    private int PAGE_NO = 0;
    private List<MallOrders.ProductOrderTransactionsBean> list;
    //private View loadingView;
    private LoadingView loadingGif;
    private MallOrders searchCondition;
    private View layoutNodata;
    private NormalDialog confirmDialog;
    private LoadingView confirmLoadingGif;


    public static NotReceivedOrdersFragment newInstance() {

        Bundle args = new Bundle();

        NotReceivedOrdersFragment fragment = new NotReceivedOrdersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.rv_not_receivedorders)
    RecyclerView rvNotReceivedOrders;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_not_receivedorders;
    }

    @Override
    protected void initEventAndData() {
        iniView();
        initRecycleView();
        initListener();
        getOrdersData(PAGE_NO, Code.PAGE_SIZE, Code.TRADE_STATUS_NORECEIVED, Code.SIGN_STATUS_NOHANDLE, Code.CODE_LOAD);
    }


    private void initListener() {
        notReceivedOrdersAdapter.setOnLoadMoreListener(this);
        notReceivedOrdersAdapter.setOnItemClickListener(new AllUserOrdersAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, BaseViewHolder baseViewHolder, MallOrders.ProductOrderTransactionsBean productOrderTransactionsBean) {
                final int position = baseViewHolder.getAdapterPosition();
                final String id = productOrderTransactionsBean.getId();
                String orderId = productOrderTransactionsBean.getOrderId();
                switch (view.getId()) {
                    case R.id.button_check_Logistics:
                        ExpressDeliverActivity.start(mContext, orderId);
                        break;
                    case R.id.button_received:
                        confirmDialog = NormalDialog.newInstance("确认收货", "确认后无法撤销", "确定");
                        confirmDialog.setOnDialogClickListener(new NormalDialog.OnConfirmClickListener() {
                            @Override
                            public void onClick() {
                                confirmLoadingGif = new LoadingView(mContext);
                                confirmLoadingGif.show();
                                mPresenter.confirmRceipt(id, position);
                            }
                        });
                        confirmDialog.showDialog(getFragmentManager());
                        break;
                }
            }
        });
    }

    @Override
    protected UserOrdersPresenter getPresenter() {
        return new UserOrdersPresenter(mContext, this);
    }

    /**
     * 获取用户积分商城所有订单
     */
    private void getOrdersData(int pageNo, int pageSize, int tradeStatus, int signStatus, String refreshCode) {
        searchCondition = new MallOrders();
        searchCondition.setPageNo(pageNo);
        searchCondition.setPageSize(pageSize);
        searchCondition.setTradeStatus(tradeStatus);
        searchCondition.setSignStatus(signStatus);
        searchCondition.setStatusCode(refreshCode);
        mPresenter.getMallOrders(searchCondition);
    }

    private void iniView() {
        //loadingView = mContext.getLayoutInflater().inflate(R.layout.layout_loading_more, (ViewGroup) rvNotReceivedOrders.getParent(), false);
        layoutNodata = mContext.getLayoutInflater().inflate(R.layout.layout_no_data, (ViewGroup) rvNotReceivedOrders.getParent(), false);
        TextView textView = (TextView) layoutNodata.findViewById(R.id.no_data_text);
        textView.setText("暂无订单信息");
    }

    /**
     * 初始化订单RV
     */
    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvNotReceivedOrders.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        notReceivedOrdersAdapter = new AllUserOrdersAdapter(R.layout.item_mall_userorders, list);
        rvNotReceivedOrders.setAdapter(notReceivedOrdersAdapter);
        //notReceivedOrdersAdapter.setLoadingView(loadingView);
        notReceivedOrdersAdapter.setEmptyView(layoutNodata);
        //swipeOrdersRefresh.setOnRefreshListener(this);
        notReceivedOrdersAdapter.setOnLoadMoreListener(this);
    }

    @Override
    public void onLoadMoreRequested() {
        getOrdersData(PAGE_NO, Code.PAGE_SIZE, Code.TRADE_STATUS_NORECEIVED, Code.SIGN_STATUS_NOHANDLE, Code.CODE_LOAD);
    }

    @Override
    public void addMallOrders(MallOrders mallOrders) {
        if (mallOrders.getProductOrderTransactions().size() > 0) {
            if (PAGE_NO == 0 && TextUtils.equals(Code.CODE_REFRESH, mallOrders.getStatusCode())) {
                notReceivedOrdersAdapter.openLoadMore(Code.PAGE_SIZE);
                notReceivedOrdersAdapter.setNewData(mallOrders.getProductOrderTransactions());
                setPAGE_NO(PAGE_NO + 1);
            } else if (TextUtils.equals(Code.CODE_LOAD, mallOrders.getStatusCode())) {
                notReceivedOrdersAdapter.addData(mallOrders.getProductOrderTransactions());
                setPAGE_NO(PAGE_NO + 1);
            }
        } else {
            if (TextUtils.equals(Code.CODE_LOAD, mallOrders.getStatusCode()))
                notReceivedOrdersAdapter.loadComplete();
            else if (TextUtils.equals(Code.CODE_REFRESH, mallOrders.getStatusCode())) {
                notReceivedOrdersAdapter.setNewData(mallOrders.getProductOrderTransactions());
            }
        }

    }

    @Override
    public void onResult(MallOrders mallOrders) {
        confirmDialog.dismiss();
        confirmLoadingGif.dismiss();
        Toast.makeText(mContext, mallOrders.getDesc(), Toast.LENGTH_SHORT).show();
        ((UserOrdersActivity) mContext).refreshAllOrder();
        ((UserOrdersActivity) mContext).refreshReceived();
        refreshAdapter();
    }

    @Override
    public void showLoading() {
        loadingGif = new LoadingView(mContext);
        loadingGif.show();
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {
        loadingGif.dismiss();
    }

    @Override
    public void showError(String errorMsg) {

    }

    /**
     * 刷新待收货页面
     */
    public void refreshAdapter() {
        PAGE_NO = 0;
        getOrdersData(PAGE_NO, Code.PAGE_SIZE, Code.TRADE_STATUS_NORECEIVED, Code.SIGN_STATUS_NOHANDLE, Code.CODE_REFRESH);
    }

    public void setPAGE_NO(int PAGE_NO) {
        this.PAGE_NO = PAGE_NO;
    }

}
