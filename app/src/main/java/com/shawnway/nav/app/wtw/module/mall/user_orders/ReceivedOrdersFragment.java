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
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.NormalDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Kevin on 2016/11/16
 */

public class ReceivedOrdersFragment extends BaseFragment<UserOrdersPresenter> implements UserOrdersContract.IUserOrderView, BaseQuickAdapter.RequestLoadMoreListener {
    /*@BindView(R.id.swipe_orders_refresh)
    SwipeRefreshLayout swipeOrdersRefresh;*/
    private AllUserOrdersAdapter receviedOrdersAdapter;
    private int PAGE_NO = 0;

    private List<MallOrders.ProductOrderTransactionsBean> list;
    //private View loadingView;
    private MallOrders searchCondition;
    private View layoutNodata;
    private NormalDialog deleteDialog;
    private LoadingView deleteLoadingGif;


    public static ReceivedOrdersFragment newInstance() {

        Bundle args = new Bundle();

        ReceivedOrdersFragment fragment = new ReceivedOrdersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.rv_recevied_orders)
    RecyclerView rvReceviedOrders;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_received_orders;
    }

    @Override
    protected void initEventAndData() {
        iniView();
        initRecycleView();
        initListener();
        getOrdersData(PAGE_NO, Code.PAGE_SIZE, Code.TRADE_STATUS_NOHANDLE, Code.SIGN_STATUS_RECEIVED, Code.CODE_LOAD);
    }


    private void initListener() {
        receviedOrdersAdapter.setOnItemClickListener(new AllUserOrdersAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, BaseViewHolder baseViewHolder, MallOrders.ProductOrderTransactionsBean productOrderTransactionsBean) {
                final int position = baseViewHolder.getAdapterPosition();
                final String id = productOrderTransactionsBean.getId();
                switch (view.getId()) {
                    case R.id.button_del_order:
                        deleteDialog = NormalDialog.newInstance("删除订单", "删除后无法撤销", "确定");
                        deleteDialog.setOnDialogClickListener(new NormalDialog.OnConfirmClickListener() {
                            @Override
                            public void onClick() {
                                deleteLoadingGif = new LoadingView(mContext);
                                deleteLoadingGif.show();
                                mPresenter.deleteOrder(id, position);
                            }
                        });
                        deleteDialog.showDialog(getFragmentManager());

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
        //loadingView = mContext.getLayoutInflater().inflate(R.layout.layout_loading_more, (ViewGroup) rvReceviedOrders.getParent(), false);
        layoutNodata = mContext.getLayoutInflater().inflate(R.layout.layout_no_data, (ViewGroup) rvReceviedOrders.getParent(), false);
        TextView textView = (TextView) layoutNodata.findViewById(R.id.no_data_text);
        textView.setText("暂无订单信息");
    }

    /**
     * 初始化订单RV
     */
    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvReceviedOrders.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        receviedOrdersAdapter = new AllUserOrdersAdapter(R.layout.item_mall_userorders, list);
        rvReceviedOrders.setAdapter(receviedOrdersAdapter);
        //receviedOrdersAdapter.setLoadingView(loadingView);
        receviedOrdersAdapter.setEmptyView(layoutNodata);
        //swipeOrdersRefresh.setOnRefreshListener(this);
        receviedOrdersAdapter.setOnLoadMoreListener(this);
    }

    @Override
    public void onLoadMoreRequested() {
        getOrdersData(PAGE_NO, Code.PAGE_SIZE, Code.TRADE_STATUS_NOHANDLE, Code.SIGN_STATUS_RECEIVED, Code.CODE_LOAD);
    }

    @Override
    public void addMallOrders(MallOrders mallOrders) {
        if (mallOrders.getProductOrderTransactions().size() > 0) {
            if (PAGE_NO == 0 && TextUtils.equals(Code.CODE_REFRESH, mallOrders.getStatusCode())) {
                receviedOrdersAdapter.openLoadMore(Code.PAGE_SIZE);
                receviedOrdersAdapter.setNewData(mallOrders.getProductOrderTransactions());
                PAGE_NO++;
            } else if (TextUtils.equals(Code.CODE_LOAD, mallOrders.getStatusCode())) {
                receviedOrdersAdapter.addData(mallOrders.getProductOrderTransactions());
                PAGE_NO++;
            }
        } else {
            if (TextUtils.equals(Code.CODE_LOAD, mallOrders.getStatusCode()))
                receviedOrdersAdapter.loadComplete();
            else if (TextUtils.equals(Code.CODE_REFRESH, mallOrders.getStatusCode())) {
                receviedOrdersAdapter.setNewData(mallOrders.getProductOrderTransactions());
            }
        }
    }


    @Override
    public void onResult(MallOrders mallOrders) {
        Toast.makeText(mContext, mallOrders.getDesc(), Toast.LENGTH_SHORT).show();
        deleteDialog.dismiss();
        deleteLoadingGif.dismiss();
        ((UserOrdersActivity) mContext).refreshAllOrder();
        refreshAdapter();
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

    /**
     * 刷新已收货订单
     */
    public void refreshAdapter() {
        PAGE_NO = 0;
        getOrdersData(PAGE_NO, Code.PAGE_SIZE, Code.TRADE_STATUS_NOHANDLE, Code.SIGN_STATUS_RECEIVED, Code.CODE_REFRESH);
    }
}
