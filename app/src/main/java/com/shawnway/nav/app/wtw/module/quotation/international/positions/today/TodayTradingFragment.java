package com.shawnway.nav.app.wtw.module.quotation.international.positions.today;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/13.
 * Administrator github = "https://github.com/Cicinnus0407"
 */

public class TodayTradingFragment extends BaseFragment<TodayPresenter> implements TodayContract.ITodayTradingView {

    public static TodayTradingFragment newInstance() {

        Bundle args = new Bundle();

        TodayTradingFragment fragment = new TodayTradingFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @BindView(R.id.progress_layout)
    ProgressLayout progressLayout;
    @BindView(R.id.rv_positions)
    RecyclerView rvPositions;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private TodayTradingAdapter tradingAdapter;


    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.getTodayTrading();
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_positions_history;
    }

    @Override
    protected TodayPresenter getPresenter() {
        return new TodayPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData(final Bundle savedBundles) {
        tradingAdapter = new TodayTradingAdapter();
        rvPositions.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvPositions.setAdapter(tradingAdapter);
        View emptyView = getLayoutInflater(savedBundles).inflate(R.layout.layout_no_data, (ViewGroup) rvPositions.getParent(), false);
        ((TextView) emptyView.findViewById(R.id.no_data_text)).setText("没有今天下单记录~~");


        tradingAdapter.setEmptyView(emptyView);
        swipe.setColorSchemeColors(getResources().getColor(R.color.lightred));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                mPresenter.getTodayTrading();
            }
        });
        mPresenter.getTodayTrading();
    }

    public void getTodayTradingData() {
        mPresenter.getTodayTrading();
    }


    @Override
    public void addTodayTrading(List<TodayTradingBean.WareHouseInfosBean> wareHouseInfos) {
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
        tradingAdapter.setNewData(wareHouseInfos);
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
        if (!progressLayout.isContent()) {
            progressLayout.showContent();
        }
    }

    @Override
    public void showError(String errorMsg) {
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
        progressLayout.showError(click);

    }
}
