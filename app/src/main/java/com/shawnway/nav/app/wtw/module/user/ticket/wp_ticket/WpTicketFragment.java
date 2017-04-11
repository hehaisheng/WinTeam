package com.shawnway.nav.app.wtw.module.user.ticket.wp_ticket;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_register.RegisterWPActivity;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Cicinnus on 2016/12/19.
 */

public class WpTicketFragment extends BaseFragment<WpTicketPresenter> implements WpTicketContract.ITicketView {


    public static WpTicketFragment newInstance() {

        Bundle args = new Bundle();

        WpTicketFragment fragment = new WpTicketFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.rv_my_ticket)
    RecyclerView rvMyTicket;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.progress_layout)
    ProgressLayout progressLayout;


    private int page = 1;
    private int size = 10;
    private String token;
    private WpTicketAdapter wpTicketAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wp_ticket;
    }

    @Override
    protected WpTicketPresenter getPresenter() {
        return new WpTicketPresenter(mContext, this);
    }


    @Override
    protected void initEventAndData(Bundle savedData) {
        token = SPUtils.getInstance(mContext, SPUtils.SP_WP)
                .getString(SPUtils.WP_TOKEN, "");
        swipe.setColorSchemeResources(R.color.appcolor);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                page = 1;
                wpTicketAdapter.setNewData(new ArrayList<WpTicketBean.DataBean>());
                mPresenter.getTicket(page, size, token);
            }
        });
        wpTicketAdapter = new WpTicketAdapter();
        View emptyView = getLayoutInflater(savedData).inflate(R.layout.layout_no_data, (ViewGroup) rvMyTicket.getParent(), false);
        ((TextView) emptyView.findViewById(R.id.no_data_text)).setText("暂时没有微盘现金券");
        wpTicketAdapter.setEmptyView(emptyView);
        rvMyTicket.setLayoutManager(new LinearLayoutManager(mContext));
        rvMyTicket.setAdapter(wpTicketAdapter);

        if (!token.equals("")) {
            mPresenter.getTicket(page, size, token);
        } else {
            View layout_register = getLayoutInflater(savedData).inflate(R.layout.layout_register_wp, (ViewGroup) rvMyTicket.getParent(), false);
            ((TextView) layout_register.findViewById(R.id.tv_register_wp)).setText("还未注册粤国际微盘");
            layout_register.findViewById(R.id.register_wp).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RegisterWPActivity.start(mContext);
                }
            });
            wpTicketAdapter.addHeaderView(layout_register);
        }
    }

    @Override
    public void addTicket(List<WpTicketBean.DataBean> data) {
        wpTicketAdapter.setNewData(data);
//        if (data.size() > 0) {
//            page += 1;
//            wpTicketAdapter.addData(data);
//        } else {
//            wpTicketAdapter.loadComplete();
//        }
        swipe.setRefreshing(false);

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
    }

    @Override
    public void showError(String errorMsg) {

        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                mPresenter.getTicket(page, size, token);
            }
        };
        progressLayout.showError(click);
    }
}
