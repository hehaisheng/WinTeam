package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_trade_history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.tool.TimeUtils;
import com.shawnway.nav.app.wtw.tool.WpUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Cicinnus on 2016/12/12.
 */

public class JlWpTradeHistoryDetailActivity extends BaseActivity {


    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.orderId)
    TextView orderId;
    @BindView(R.id.buildTime)
    TextView buildTime;
    @BindView(R.id.liquidateTime)
    TextView liquidateTime;
    @BindView(R.id.productName)
    TextView productName;
    @BindView(R.id.tradeType)
    TextView tradeType;
    @BindView(R.id.buildPrice)
    TextView buildPrice;
    @BindView(R.id.liquidatePrice)
    TextView liquidatePrice;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.depositPrice)
    TextView depositPrice;
    @BindView(R.id.tradeFee)
    TextView tradeFee;
    @BindView(R.id.actualProfit)
    TextView actualProfit;
    @BindView(R.id.liquidateType)
    TextView liquidateType;
    @BindView(R.id.liquidateIncome)
    TextView liquidateIncome;
    private JlWpTradeHistoryBean.DataBean.ListBean bean;

    public static void start(Context context, JlWpTradeHistoryBean.DataBean.ListBean listBean) {
        Intent starter = new Intent(context, JlWpTradeHistoryDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("bean", listBean);
        starter.putExtra("bundle", bundle);
        context.startActivity(starter);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_wp_trade_history_detail;
    }

    @Override
    protected void initEventAndData() {
        bean = getIntent().getBundleExtra("bundle").getParcelable("bean");
        topTextCenter.setText("订单详情");
        setVisiable(toolbar,topBack,topTextCenter);
        if (bean != null) {
            orderId.setText(bean.getOrderNo());
            buildTime.setText(TimeUtils.dateYMDHM(bean.getBuildTime()));
            liquidateTime.setText(TimeUtils.dateYMDHM(bean.getLiquidateTime()));
            productName.setText(bean.getProductName());
            tradeType.setText(WpUtils.getTradeType(bean.getTradeType()));
            buildPrice.setText(String.format("%s元",bean.getBuildPositionPrice()));
            liquidatePrice.setText(String.format("%s元",bean.getLiquidatePositionPrice()));
            amount.setText(String.format("%s手",bean.getAmount()));
            depositPrice.setText(String.format("%s元",bean.getTradeDeposit()));
            tradeFee.setText(String.format("%s元",bean.getTradeFee()));
            actualProfit.setText(String.format("%s元",bean.getProfitOrLoss()));
            actualProfit.setTextColor(bean.getActualProfitLoss()>=0?getResources().getColor(R.color.appcolor):getResources().getColor(R.color.lightgreen));
            liquidateType.setText(WpUtils.getLiquiDateType(bean.getLiquidateType()));
            liquidateIncome.setText(String.format("%s元",bean.getLiquidateIncome()));
        }
    }

    @OnClick({R.id.top_back})
    void onClick(View view){
        switch (view.getId()){
            case R.id.top_back:
                finish();
                break;
        }
    }

}
