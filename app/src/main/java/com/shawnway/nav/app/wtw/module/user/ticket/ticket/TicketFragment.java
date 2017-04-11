package com.shawnway.nav.app.wtw.module.user.ticket.ticket;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Cicinnus on 2016/12/19.
 */

public class TicketFragment extends BaseFragment<TicketPresenter> implements TicketContract.TicketView {

    @BindView(R.id.rv)
    RecyclerView rvCoupon;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.progress_layout)
    ProgressLayout progressLayout;

    private int status = 0;
    private int pageNo = 0;
    private TicketAdapter ticketAdapter;

    public static TicketFragment newInstance() {

        Bundle args = new Bundle();

        TicketFragment fragment = new TicketFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ticket;
    }

    @Override
    protected TicketPresenter getPresenter() {
        return new TicketPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData(Bundle savedData) {
        ticketAdapter = new TicketAdapter();
        rvCoupon.setLayoutManager(new LinearLayoutManager(mContext));
        rvCoupon.setAdapter(ticketAdapter);
        ticketAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getTicket(status,pageNo);
            }
        });
        View emptyView = getLayoutInflater(savedData).inflate(R.layout.layout_no_data, (ViewGroup) rvCoupon.getParent(), false);
        ((TextView) emptyView.findViewById(R.id.no_data_text)).setText("暂时没有期货现金券");
        ticketAdapter.setEmptyView(emptyView);
        swipe.setColorSchemeColors(getResources().getColor(R.color.appcolor));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                status = 0;
                pageNo = 0;
                ticketAdapter.setNewData(new ArrayList<TicketBean.ListBean>());
                mPresenter.getTicket(status,pageNo);
            }
        });
        mPresenter.getTicket(status,pageNo);
    }

    @Override
    public void addTicket(List<TicketBean.ListBean> list) {
        if(list.size()>0){
            pageNo+=1;
            ticketAdapter.addData(list);
        }else {
            ticketAdapter.loadComplete();
        }
    }

    @Override
    public void showLoading() {
        swipe.setRefreshing(true);
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {
        if(swipe.isRefreshing()){
            swipe.setRefreshing(false);
        }
        if (!progressLayout.isContent()) {
            progressLayout.showContent();
        }
    }

    @Override
    public void showError(String errorMsg) {
        progressLayout.showError(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = 0;
                pageNo = 0;
                mPresenter.getTicket(status,pageNo);
            }
        });
    }

}
