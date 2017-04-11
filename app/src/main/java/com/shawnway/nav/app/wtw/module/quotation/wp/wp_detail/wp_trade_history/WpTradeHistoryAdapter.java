package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_trade_history;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.tool.TimeUtils;
import com.shawnway.nav.app.wtw.tool.WpUtils;

/**
 * Created by Cicinnus on 2016/12/12.
 */

public class WpTradeHistoryAdapter extends BaseQuickAdapter<WpTradeHistoryBean.DataBean.ListBean> {
    public WpTradeHistoryAdapter() {
        super(R.layout.item_wp_trade_history, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final WpTradeHistoryBean.DataBean.ListBean listBean) {
        baseViewHolder.setText(R.id.productName, String.format("%s", listBean.getProductName()))
                .setText(R.id.tradeType, String.format("（%s%s手）", WpUtils.getTradeType(listBean.getTradeType()), listBean.getAmount()))
                .setText(R.id.liquidateType,WpUtils.getLiquiDateType(listBean.getLiquidateType()
                ))
                .setText(R.id.profit, String.format("%s", listBean.getProfitOrLoss()))
                .setText(R.id.buildTime, String.format("%s", TimeUtils.dateYMDHM(listBean.getBuildTime())))
                .setTextColor(R.id.profit, listBean.getActualProfitLoss() > 0 ?
                        mContext.getResources().getColor(R.color.appcolor) :
                        mContext.getResources().getColor(R.color.lightgreen));

        baseViewHolder.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WpTradeHistoryDetailActivity.start(mContext,listBean);
            }
        });
    }
}
