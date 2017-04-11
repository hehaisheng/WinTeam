package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_trade_history;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Cicinnus on 2016/12/12.
 */

public class WpTradeHistoryActivity extends BaseActivity<WpTradeHistoryPresenter> implements WpTradeHistoryContract.IWpTradeHistoryView {

    public static void start(Context context) {
        Intent starter = new Intent(context, WpTradeHistoryActivity.class);
        context.startActivity(starter);
    }

    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.rv)
    RecyclerView rvTradeHistory;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.progress_layout)
    ProgressLayout progressLayout;
    @BindView(R.id.totalProfitLoss)
    TextView totalProfitLoss;
    @BindView(R.id.totalCount)
    TextView totalCount;
    @BindView(R.id.totalAmount)
    TextView totalAmount;

    private LoadingView loadingGif;
    private Handler handler = new Handler();
    private int pageNum = 1;
    private String token;
    private WpTradeHistoryAdapter historyAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_wp_trade_history;
    }


    @Override
    protected WpTradeHistoryPresenter getPresenter() {
        return new WpTradeHistoryPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        loadingGif = new LoadingView(mContext);
        token = SPUtils.getInstance(mContext,SPUtils.SP_WP).getString(SPUtils.WP_TOKEN,"");
        topTextCenter.setText("交易明细");
        setVisiable(topBack, toolbar, topTextCenter);
        swipe.setColorSchemeColors(getResources().getColor(R.color.appcolor));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                pageNum = 1;
                historyAdapter.setNewData(new ArrayList<WpTradeHistoryBean.DataBean.ListBean>());
                mPresenter.getTradeHistory(pageNum, token);
                mPresenter.getTradeTotal(token);
            }
        });

        historyAdapter = new WpTradeHistoryAdapter();
        View emptyView = getLayoutInflater().inflate(R.layout.layout_no_data, (ViewGroup) rvTradeHistory.getParent(), false);
        ((TextView) emptyView.findViewById(R.id.no_data_text)).setText("暂无交易明细");
        historyAdapter.setEmptyView(emptyView);
        historyAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getTradeHistory(pageNum, token);
            }
        });
        rvTradeHistory.setLayoutManager(new LinearLayoutManager(mContext));
        rvTradeHistory.setAdapter(historyAdapter);
//        rvTradeHistory.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST));

        //获取交易明细和总计
        mPresenter.getTradeTotal(token);
        mPresenter.getTradeHistory(pageNum, token);
    }

    @OnClick({R.id.top_back})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_back:
                finish();
                break;
        }
    }

    @Override
    public void addTradeHistory(List<WpTradeHistoryBean.DataBean.ListBean> list) {
        if (list.size() > 0) {
            pageNum += 1;
            historyAdapter.addData(list);
        } else {
            historyAdapter.loadComplete();
        }
    }

    @Override
    public void addTradeTotal(final WpTradeTotalBean.DataBean data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                totalProfitLoss.setText(String.format("%s", data.getTotalprofitLoss()));
                totalCount.setText(String.format("%s", data.getCount()));
                totalAmount.setText(String.format("%s", data.getAmount()));
                totalProfitLoss.setTextColor(data.getTotalprofitLoss()>=0?getResources().getColor(R.color.appcolor):getResources().getColor(R.color.lightgreen));
            }
        });
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
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
        if (!progressLayout.isContent()) {
            progressLayout.showContent();
        }
    }

    @Override
    public void showError(String errorMsg) {
        loadingGif.dismiss();
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
        progressLayout.showError(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNum = 1;
                token = SPUtils.getInstance(mContext, SPUtils.SP_WP)
                        .getString(SPUtils.WP_TOKEN, "");
                mPresenter.getTradeHistory(pageNum, token);
                mPresenter.getTradeTotal(token);
            }
        });
    }

}
