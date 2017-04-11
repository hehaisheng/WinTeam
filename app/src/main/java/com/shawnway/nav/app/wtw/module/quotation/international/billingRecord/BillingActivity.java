package com.shawnway.nav.app.wtw.module.quotation.international.billingRecord;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shawnway.nav.app.wtw.R.id.top_text_right;

/**
 * Created by Administrator on 2016/9/27.
 * 结算页面
 */

public class BillingActivity extends BaseActivity<BillingPresenter> implements BillingContract.IBillingView {


    @BindView(R.id.progress_layout)
    ProgressLayout progressLayout;
    @BindView(R.id.rv_billing)
    RecyclerView rvBilling;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.userUsageMoney)
    TextView userUsageMoney;
    private BillingAdapter billingAdapter;
    private View errorView;
    private LoadingView loadingView;

    @Override
    protected int getLayout() {
        return R.layout.activity_billing;
    }

    @Override
    protected BillingPresenter getPresenter() {
        return new BillingPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        loadingView = new LoadingView(mContext);
        initTopBar();
        billingAdapter = new BillingAdapter();
        View emptyView = getLayoutInflater().inflate(R.layout.layout_no_data, (ViewGroup) rvBilling.getParent(), false);
        ((TextView) emptyView.findViewById(R.id.no_data_text)).setText("暂无持仓历史记录");
        billingAdapter.setEmptyView(emptyView);
        rvBilling.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        errorView = getLayoutInflater().inflate(R.layout.layout_error, (ViewGroup) rvBilling.getParent(), false);
        rvBilling.setAdapter(billingAdapter);
        swipe.setColorSchemeColors(getResources().getColor(R.color.lightred));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                mPresenter.getBilling();
                mPresenter.getUserMoney();
            }
        });
        mPresenter.getBilling();
        mPresenter.getUserMoney();
    }

    private void initTopBar() {
        toolbar.setVisibility(View.VISIBLE);
        topTextCenter.setText("结算");
        topTextCenter.setVisibility(View.VISIBLE);
        topBack.setVisibility(View.VISIBLE);

    }


    @OnClick({R.id.top_back, top_text_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_back:
                finish();
                break;
        }
    }

    @Override
    public void addBilling(List<BillRecordBean.WareHouseInfosBean> wareHouseInfos) {
        billingAdapter.setNewData(wareHouseInfos);
    }

    @Override
    public void addMoney(Number tradingAccountUsableAmount) {
        userUsageMoney.setText(String.format("账户余额：%s", tradingAccountUsableAmount));
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
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
        if (!progressLayout.isContent()) {
            progressLayout.showContent();
        }
       loadingView.dismiss();
    }

    @Override
    public void showError(String errorMsg) {
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getBilling();
                mPresenter.getUserMoney();
            }
        };
        progressLayout.showError(click);
    }
}
