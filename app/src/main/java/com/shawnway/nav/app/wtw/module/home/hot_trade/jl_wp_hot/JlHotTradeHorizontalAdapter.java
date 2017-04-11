package com.shawnway.nav.app.wtw.module.home.hot_trade.jl_wp_hot;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;
import com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.JlWpDetailActivity;
import com.shawnway.nav.app.wtw.tool.SPUtils;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/9/14.
 */

public class JlHotTradeHorizontalAdapter extends BaseQuickAdapter<QuotationsWPBean> {

    private double chgNum;//差值
    private double chg;//涨跌
    private boolean up;//上升或下降
    private DecimalFormat dlf = new DecimalFormat("0.00");
    private OnHotTradeClickListener onHotTradeClickListener;

    public JlHotTradeHorizontalAdapter() {
        super(R.layout.item_hot_trade, null);

    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final QuotationsWPBean quotationsWPBean) {
        chgNum = quotationsWPBean.getLatestPrice() - quotationsWPBean.getPriceAtEndLastday();//差值
        chg = chgNum / quotationsWPBean.getPriceAtEndLastday();//涨跌幅
        up = chgNum > 0;
        final String productName = SPUtils.getInstance(mContext, SPUtils.SP_JL_WP_HOT_SELECTED)
                .getString(quotationsWPBean.getProductContract(), "");
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        LinearLayout linearLayout = baseViewHolder.getView(R.id.ll_hot_trade_item);
        linearLayout.setMinimumWidth(width / 3);
        int colors;
        if (up) {
            colors = mContext.getResources().getColor(R.color.lightred);
        } else {
            colors = mContext.getResources().getColor(R.color.lightgreen);
        }

        baseViewHolder
                .setTextColor(R.id.tv_hot_title, colors)
                .setTextColor(R.id.tv_hot_price, colors)
                .setTextColor(R.id.change_price, colors)
                .setTextColor(R.id.chang_rate, colors)
                .setText(R.id.tv_hot_title, productName)
                .setText(R.id.tv_hot_price, dlf.format(quotationsWPBean.getLatestPrice()) + "")
                .setText(R.id.change_price, String.format("+%s", dlf.format(chgNum)))
                .setText(R.id.chang_rate, String.format("(%s%%)", dlf.format(chg * 100)));


        baseViewHolder.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JlWpDetailActivity.getInstance(mContext, quotationsWPBean);
            }
        });


    }

    public void setOnHotTradeClickListener(OnHotTradeClickListener onHotTradeClickListener) {
        this.onHotTradeClickListener = onHotTradeClickListener;
    }


    public interface OnHotTradeClickListener {
        void onClick(QuotationsWPBean quotationsWPBean);

    }
}
