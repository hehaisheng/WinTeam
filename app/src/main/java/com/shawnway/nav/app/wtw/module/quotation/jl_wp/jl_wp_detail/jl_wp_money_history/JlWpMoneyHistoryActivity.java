package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_money_history;

import android.content.Context;
import android.content.Intent;
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
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.DividerItemDecoration;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Cicinnus on 2016/12/11.
 */

public class JlWpMoneyHistoryActivity extends BaseActivity<JlWpMoneyHistoryPresenter> implements JlWpMoneyHistoryContract.IWpMoneyHistoryView {

    private String token;

    public static void start(Context context) {
        Intent starter = new Intent(context, JlWpMoneyHistoryActivity.class);
        context.startActivity(starter);
    }

    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.rv)
    RecyclerView rvMoneyHistory;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.progress_layout)
    ProgressLayout progressLayout;
    private int page = 1;
    private JlWpMoneyHistoryAdapter adapter;
    private LoadingView loadingView;

    @Override
    protected int getLayout() {
        return R.layout.activity_wp_monty_history;
    }

    @Override
    protected JlWpMoneyHistoryPresenter getPresenter() {
        return new JlWpMoneyHistoryPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        loadingView = new LoadingView(mContext);
        token = SPUtils.getInstance(mContext,SPUtils.SP_JL_WP).getString(SPUtils.JL_WP_TOKEN,"");
        topTextCenter.setText("收支明细");
        setVisiable(toolbar, topBack, topTextCenter);
        adapter = new JlWpMoneyHistoryAdapter();
        View emptyView = getLayoutInflater().inflate(R.layout.layout_no_data, (ViewGroup) rvMoneyHistory.getParent(), false);
        ((TextView) emptyView.findViewById(R.id.no_data_text)).setText("暂无收支明细");
        adapter.setEmptyView(emptyView);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getWpMoneyHistory(page, token);
            }
        });
        rvMoneyHistory.setLayoutManager(new LinearLayoutManager(mContext));
        rvMoneyHistory.setAdapter(adapter);
        rvMoneyHistory.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST));
        swipe.setColorSchemeColors(getResources().getColor(R.color.appcolor));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                page = 1;
                adapter.setNewData(new ArrayList<JlWpMoneyHistoryBean.DataBean.ListBean>());
                mPresenter.getWpMoneyHistory(page, token);
            }
        });
        mPresenter.getWpMoneyHistory(page, token);
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
    public void addWpMoneyHistory(List<JlWpMoneyHistoryBean.DataBean.ListBean> list) {
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
        if (list.size() > 0) {
            page+=1;
            adapter.addData(list);
        } else {
            adapter.loadComplete();
        }
    }

    @Override
    public void showLoading() {
        if (!progressLayout.isContent()) {
            progressLayout.showContent();
        }
        loadingView.show();
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {
        loadingView.dismiss();
        if (!progressLayout.isContent()) {
            progressLayout.showContent();
        }
    }

    @Override
    public void showError(String errorMsg) {
        loadingView.dismiss();
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
        progressLayout.showError(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                token = SPUtils.getInstance(mContext,SPUtils.SP_JL_WP)
                        .getString(SPUtils.JL_WP_TOKEN,"");
                page = 1;
                mPresenter.getWpMoneyHistory(page, token);
            }
        });
        ToastUtil.showShort(mContext, errorMsg);
    }

}
