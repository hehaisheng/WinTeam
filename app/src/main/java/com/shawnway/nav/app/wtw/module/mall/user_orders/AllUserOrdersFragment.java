package com.shawnway.nav.app.wtw.module.mall.user_orders;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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

public class AllUserOrdersFragment extends BaseFragment<UserOrdersPresenter> implements UserOrdersContract.IUserOrderView, BaseQuickAdapter.RequestLoadMoreListener {
    /*@BindView(R.id.swipe_orders_refresh)
    SwipeRefreshLayout swipeOrdersRefresh;*/
    private AllUserOrdersAdapter allUserOrdersAdapter;
    private int PAGE_NO = 0;
    private List<MallOrders.ProductOrderTransactionsBean> list;
    private LoadingView loadingGif ;
    private LoadingView actionloadingGif;
    private MallOrders searchCondition;
    private View layoutNodata;
    private boolean isFirstLoad = false;
    private NormalDialog deleteDialog;
    private NormalDialog confirmDialog;


    public static AllUserOrdersFragment newInstance() {

        Bundle args = new Bundle();

        AllUserOrdersFragment fragment = new AllUserOrdersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.rv_all_user_orders)
    RecyclerView rvAllUserOrders;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_userorders;
    }

    @Override
    protected void initEventAndData() {
        loadingGif = new LoadingView(mContext);
        actionloadingGif = new LoadingView(mContext);
        iniView();
        initRecycleView();
        initListener();
        getOrdersData(PAGE_NO, Code.PAGE_SIZE, Code.TRADE_STATUS_NOHANDLE, Code.SIGN_STATUS_NOHANDLE, Code.CODE_LOAD);
    }


    private void initListener() {
        allUserOrdersAdapter.setOnItemClickListener(new AllUserOrdersAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, BaseViewHolder baseViewHolder, MallOrders.ProductOrderTransactionsBean productOrderTransactionsBean) {
                final int position = baseViewHolder.getAdapterPosition();
                Log.d("AllUserOrdersFragment", "position:" + position);
                final String id = productOrderTransactionsBean.getId();
                String orderId = productOrderTransactionsBean.getOrderId();
                switch (view.getId()) {
                    case R.id.button_del_order:
                        deleteDialog = NormalDialog.newInstance("删除订单", "删除后无法撤销", "确定");
                        deleteDialog.setOnDialogClickListener(new NormalDialog.OnConfirmClickListener() {
                            @Override
                            public void onClick() {
                                actionloadingGif.show();
                                mPresenter.deleteOrder(id, position);
                            }
                        });
                        deleteDialog.showDialog(getFragmentManager());

                        break;
                    case R.id.button_check_Logistics:
                        ExpressDeliverActivity.start(mContext, orderId);
                        break;
                    case R.id.button_received:
                        confirmDialog = NormalDialog.newInstance("确认收货", "确认后无法撤销", "确定");
                        confirmDialog.setOnDialogClickListener(new NormalDialog.OnConfirmClickListener() {
                            @Override
                            public void onClick() {
                                actionloadingGif.show();
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
        //loadingView = mContext.getLayoutInflater().inflate(R.layout.layout_loading_more, null);
        layoutNodata = mContext.getLayoutInflater().inflate(R.layout.layout_no_data, (ViewGroup) rvAllUserOrders.getParent(), false);
        TextView textView = (TextView) layoutNodata.findViewById(R.id.no_data_text);
        textView.setText("暂无订单信息");
    }

    /**
     * 初始化订单RV
     */
    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvAllUserOrders.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        allUserOrdersAdapter = new AllUserOrdersAdapter(R.layout.item_mall_userorders, list);
        rvAllUserOrders.setAdapter(allUserOrdersAdapter);
        //allUserOrdersAdapter.setLoadingView(loadingView);
        allUserOrdersAdapter.setEmptyView(layoutNodata);
        //swipeOrdersRefresh.setOnRefreshListener(this);
        allUserOrdersAdapter.setOnLoadMoreListener(this);
    }

    @Override
    public void onLoadMoreRequested() {
        getOrdersData(PAGE_NO, Code.PAGE_SIZE, Code.TRADE_STATUS_NOHANDLE, Code.SIGN_STATUS_NOHANDLE, Code.CODE_LOAD);
    }

    @Override
    public void addMallOrders(MallOrders mallOrders) {
        if (mallOrders.getProductOrderTransactions().size() > 0) {
            if (PAGE_NO == 0 && TextUtils.equals(Code.CODE_REFRESH, mallOrders.getStatusCode())) {
                allUserOrdersAdapter.openLoadMore(Code.PAGE_SIZE);
                allUserOrdersAdapter.setNewData(mallOrders.getProductOrderTransactions());
                PAGE_NO++;
            } else if (TextUtils.equals(Code.CODE_LOAD, mallOrders.getStatusCode())) {
                allUserOrdersAdapter.addData(mallOrders.getProductOrderTransactions());
                PAGE_NO++;
            }
        } else {
            if (TextUtils.equals(Code.CODE_LOAD, mallOrders.getStatusCode()))
                allUserOrdersAdapter.loadComplete();
            else if (TextUtils.equals(Code.CODE_REFRESH, mallOrders.getStatusCode())) {
                allUserOrdersAdapter.setNewData(mallOrders.getProductOrderTransactions());
            }
        }
    }

    @Override
    public void onResult(MallOrders mallOrders) {
        actionloadingGif.dismiss();
        Toast.makeText(mContext, mallOrders.getDesc(), Toast.LENGTH_SHORT).show();
        if (TextUtils.equals(mallOrders.getDesc(), Code.DELETE_DESC)) {
            deleteDialog.dismiss();
            refreshAdapter();
            ((UserOrdersActivity) mContext).refreshReceived();
        } else if (TextUtils.equals(mallOrders.getDesc(), Code.CONFIRM_DESC)) {
            confirmDialog.dismiss();
            refreshAdapterItem(mallOrders.getPosition());
            ((UserOrdersActivity) mContext).refreshNotReceived();
            ((UserOrdersActivity) mContext).refreshReceived();
        }
    }


    @Override
    public void showLoading() {
        if (!isFirstLoad) {
            rvAllUserOrders.setVisibility(View.GONE);
        }

        loadingGif.dismiss();
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {
        if (!isFirstLoad) {
            rvAllUserOrders.setVisibility(View.VISIBLE);
            isFirstLoad = true;
        }
        loadingGif.dismiss();
    }

    @Override
    public void showError(String errorMsg) {
    }

    /**
     * 刷新某个Item
     *
     * @param position
     */
    public void refreshAdapterItem(int position) {
        list.get(position).setStatus(Code.STATUS_RECEVIED);
        allUserOrdersAdapter.notifyItemChanged(position);
    }

    /**
     * 刷新所有订单的页面
     */
    public void refreshAdapter() {
        PAGE_NO = 0;
        getOrdersData(PAGE_NO, Code.PAGE_SIZE, Code.TRADE_STATUS_NOHANDLE, Code.SIGN_STATUS_NOHANDLE, Code.CODE_REFRESH);
    }

}
