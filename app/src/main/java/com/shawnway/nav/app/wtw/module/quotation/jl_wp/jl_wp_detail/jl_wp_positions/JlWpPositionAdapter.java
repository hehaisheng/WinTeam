package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_positions;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;

/**
 * Created by Cicinnus on 2016/12/10.
 */

public class JlWpPositionAdapter extends BaseQuickAdapter<JlWpPositionsBean.DataBean.ListBean> {

    private OnLiquidateOnClickListener onLiquidateOnClickListener;

    public JlWpPositionAdapter() {
        super(R.layout.item_wp_positions, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final JlWpPositionsBean.DataBean.ListBean listBean) {
        baseViewHolder
                .setText(R.id.productName, String.format("%s%s", listBean.getProductName(), listBean.getSpecifications()))
                .setText(R.id.buildPrice, String.format("%s", listBean.getBuildPositionPrice().doubleValue()))
                .setText(R.id.floatProfit, String.format("%s%s元", listBean.getProfitOrLoss() >= 0 ? "盈" : "亏", listBean.getProfitOrLoss()))
                .setText(R.id.amount, String.format("%s手", listBean.getAmount()))
                .setText(R.id.chg, String.format("%s", listBean.getTradeType().intValue() == 1 ? "买涨" : "买跌"))
                .setTextColor(R.id.floatProfit, listBean.getProfitOrLoss() >= 0 ? mContext.getResources().getColor(R.color.appcolor) : mContext.getResources().getColor(R.color.lightgreen));

        TextView chg = baseViewHolder.getView(R.id.chg);
        chg.setBackgroundColor(listBean.getTradeType().intValue() == 1 ? mContext.getResources().getColor(R.color.appcolor) : mContext.getResources().getColor(R.color.lightgreen));

        baseViewHolder.getView(R.id.sellProduct)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onLiquidateOnClickListener != null) {
                            onLiquidateOnClickListener.onClick(listBean.getId());
                        }

                    }
                });

    }

    public void setOnLiquidateOnClickListener(OnLiquidateOnClickListener onLiquidateOnClickListener) {
        this.onLiquidateOnClickListener = onLiquidateOnClickListener;
    }

    public interface OnLiquidateOnClickListener {
        void onClick(String orderId);
    }
}
