package com.shawnway.nav.app.wtw.module.mall.exchange_list;

import android.content.Context;
import android.content.Intent;
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
import com.shawnway.nav.app.wtw.module.mall.bean.MallOrders;
import com.shawnway.nav.app.wtw.view.DividerItemDecoration;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Cicinnus on 2016/11/29.
 */

public class ExchangeListActivity extends BaseActivity<ExchangeListPresenter> implements ExchangeListContract.IExchangeListView {


    public static void start(Context context) {
        Intent starter = new Intent(context, ExchangeListActivity.class);
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
    @BindView(R.id.rv_Exchange_lis)
    RecyclerView rvExchangeLis;

    private LoadingView loadingView;
    private int page;
    private ExchangeListAdapter listAdapter;
    private List<MallOrders.ProductOrderTransactionsBean.ProductOrderEntitiesBean> dataList = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_my_exchange_list;
    }

    @Override
    protected ExchangeListPresenter getPresenter() {
        return new ExchangeListPresenter(mContext, this);
    }

    @OnClick(R.id.top_back)
    void onClick(View v) {
        finish();
    }

    @Override
    protected void initEventAndData() {
        loadingView = new LoadingView(mContext);
        topTextCenter.setText("我的兑换");
        setVisiable(topBack, topTextCenter, toolbar);
        rvExchangeLis.setLayoutManager(new LinearLayoutManager(mContext));
        listAdapter = new ExchangeListAdapter();
        View emptyView = getLayoutInflater().inflate(R.layout.layout_no_data, (ViewGroup) rvExchangeLis.getParent(), false);
        ((TextView) emptyView.findViewById(R.id.no_data_text)).setText("没有兑换记录");
        listAdapter.setEmptyView(emptyView);
        rvExchangeLis.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        rvExchangeLis.setAdapter(listAdapter);
        listAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getExchangeList(page);
            }
        });
        mPresenter.getExchangeList(page);
    }

    @Override
    public void addExchangeList(MallOrders body) {
        if (body.getProductOrderTransactions().size() > 0) {
            if (page > 0) {
                dataList.clear();
            }
            for (int i = 0; i < body.getProductOrderTransactions().size(); i++) {
                for (int j = 0; j < body.getProductOrderTransactions().get(i).getProductOrderEntities().size(); j++) {
                    body.getProductOrderTransactions().get(i).getProductOrderEntities().get(j)
                            .setOrderDate(body.getProductOrderTransactions().get(i).getOrderDate());
                    dataList.add(body.getProductOrderTransactions().get(i).getProductOrderEntities().get(j));
                }
            }
            page += 1;
            listAdapter.addData(dataList);
        } else {
            listAdapter.loadComplete();
            if (listAdapter.getData().size() >= 8) {
                View footer = View.inflate(mContext, R.layout.layout_footer_no_data, null);
                listAdapter.removeAllFooterView();
                listAdapter.addFooterView(footer);
            }
        }
    }

    @Override
    public void showLoading() {
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
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getExchangeList(page);
            }
        };
        progressLayout.showError(click);
    }
}
