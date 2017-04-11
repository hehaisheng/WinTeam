package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_trade_history;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.tool.TimeUtils;
import com.shawnway.nav.app.wtw.tool.WpUtils;

/**
 * Created by Cicinnus on 2016/12/12.
 */

public class JlWpTradeHistoryAdapter extends BaseQuickAdapter<JlWpTradeHistoryBean.DataBean.ListBean> {
    public JlWpTradeHistoryAdapter() {
        super(R.layout.item_wp_trade_history, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final JlWpTradeHistoryBean.DataBean.ListBean listBean) {
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
                JlWpTradeHistoryDetailActivity.start(mContext,listBean);
            }
        });
    }
}
