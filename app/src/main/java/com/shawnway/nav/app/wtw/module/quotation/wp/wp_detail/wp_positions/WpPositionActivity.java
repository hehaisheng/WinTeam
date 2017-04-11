package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_positions;

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

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_money_history.WpMoneyHistoryActivity;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_trade_history.WpTradeHistoryActivity;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.DividerItemDecoration;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.NormalDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Cicinnus on 2016/12/9.
 */

public class WpPositionActivity extends BaseActivity<WpPositionPresenter> implements WpPositionContract.IWpPositionView {


    public static void start(Context context, String token) {
        Intent starter = new Intent(context, WpPositionActivity.class);
        starter.putExtra("token", token);
        context.startActivity(starter);
    }


    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.userUsageMoney)
    TextView userUsageMoney;
    @BindView(R.id.frozenMoney)
    TextView frozenMoney;
    @BindView(R.id.totalProfitLoss)
    TextView totalProfitLoss;
    @BindView(R.id.rv_wp_position)
    RecyclerView rvWpPosition;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private WpPositionAdapter wpPositionAdapter;
    private LoadingView loadingGif;
    private String token;
    private Handler handler = new Handler();
    ;
    private GetPositionsRunnable getPositionsRunnable;
    private boolean isFirst = true;

    @Override
    protected int getLayout() {
        return R.layout.activity_wp_positions;
    }

    @Override
    protected WpPositionPresenter getPresenter() {
        return new WpPositionPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        loadingGif = new LoadingView(mContext);
        token = SPUtils.getInstance(mContext, SPUtils.SP_WP).getString(SPUtils.WP_TOKEN, "");
        topTextCenter.setText("持仓");
        setVisiable(topBack, toolbar, topTextCenter);
        swipe.setColorSchemeColors(getResources().getColor(R.color.appcolor));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                token = SPUtils.getInstance(mContext, SPUtils.SP_WP)
                        .getString(SPUtils.WP_TOKEN, "");
                mPresenter.getWpUserAccountBalance(token);
                mPresenter.getPositions(token);
            }
        });
        wpPositionAdapter = new WpPositionAdapter();
        View emptyView = getLayoutInflater().inflate(R.layout.layout_no_data, (ViewGroup) rvWpPosition.getParent(), false);
        ((TextView) emptyView.findViewById(R.id.no_data_text)).setText("暂无持仓记录");
        wpPositionAdapter.setEmptyView(emptyView);
        wpPositionAdapter.setOnLiquidateOnClickListener(new WpPositionAdapter.OnLiquidateOnClickListener() {
            @Override
            public void onClick(final String orderId) {
                final NormalDialog liquidateDialog = NormalDialog.newInstance("确定平仓", "平仓后无法恢复", "确定");
                liquidateDialog.setOnDialogClickListener(new NormalDialog.OnConfirmClickListener() {
                    @Override
                    public void onClick() {
                        liquidateDialog.dismiss();
                        mPresenter.liquidate(orderId, token);
                    }
                });
                liquidateDialog.showDialog(getSupportFragmentManager());
            }
        });
        rvWpPosition.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        rvWpPosition.setLayoutManager(new LinearLayoutManager(mContext));
        rvWpPosition.setAdapter(wpPositionAdapter);
        getPositionsRunnable = new GetPositionsRunnable();


    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(getPositionsRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(getPositionsRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(getPositionsRunnable);
    }

    @OnClick({R.id.top_back, R.id.moneyHistory, R.id.tradeHistory, R.id.liquidateAll})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.moneyHistory:
                WpMoneyHistoryActivity.start(mContext);
                break;
            case R.id.tradeHistory:
                WpTradeHistoryActivity.start(mContext);
                break;
            case R.id.liquidateAll:
                if (wpPositionAdapter.getData().size() > 0) {
                    final NormalDialog liquidateAll =
                            NormalDialog.newInstance("一键平仓", "所有持仓将会被平仓", "确定");
                    liquidateAll.setOnDialogClickListener(new NormalDialog.OnConfirmClickListener() {
                        @Override
                        public void onClick() {
                            liquidateAll.dismiss();
                            mPresenter.liquidateAll(token);
                        }
                    });
                    liquidateAll.showDialog(getSupportFragmentManager());
                } else {
                    ToastUtil.showShort(mContext, "暂无持仓");
                }
                break;
        }
    }

    @Override
    public void addPositions(WpPositionsBean list) {
        swipe.setRefreshing(false);
        wpPositionAdapter.setNewData(list.getData().getList());
        totalProfitLoss.setText(String.format("%s", list.getData().getTotalProfitLoss()));

    }

    @Override
    public void addWpUserBalance(UserAccountBean.AccBanBean useableBalance) {
        frozenMoney.setText(String.format("%s", useableBalance.frozenBalance));
        userUsageMoney.setText(String.format("%s", useableBalance.useableBalance));
    }

    @Override
    public void liquidateSuccess() {
        mPresenter.getWpUserAccountBalance(token);
        mPresenter.getPositions(token);
    }

    @Override
    public void liquidateAllSuccess() {
        mPresenter.getWpUserAccountBalance(token);
        mPresenter.getPositions(token);
    }

    @Override
    public void showLoading() {
        if (isFirst) {
            loadingGif.show();
        }
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
        ToastUtil.showShort(mContext, errorMsg);
        swipe.setRefreshing(false);
        loadingGif.dismiss();

    }

    class GetPositionsRunnable implements Runnable {
        @Override
        public void run() {
            if (isFirst) {
                mPresenter.getWpUserAccountBalance(token);
                mPresenter.getPositions(token);
                handler.postDelayed(this, 4000);
                isFirst = false;
            } else {
                if (wpPositionAdapter.getData().size() > 0) {
                    mPresenter.getPositions(token);
                    mPresenter.getWpUserAccountBalance(token);
                    handler.postDelayed(this, 4000);
                }
            }

        }
    }

}
