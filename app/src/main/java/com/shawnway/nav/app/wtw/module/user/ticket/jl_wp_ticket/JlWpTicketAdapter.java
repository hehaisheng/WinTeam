package com.shawnway.nav.app.wtw.module.user.ticket.jl_wp_ticket;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.tool.Utils;

/**
 * 个人中心里面我的赢家券
 * Created by Administrator on 2016/8/10.
 */
public class JlWpTicketAdapter extends BaseQuickAdapter<JlWpTicketBean.DataBean.TicketsBean> {
    public JlWpTicketAdapter() {
        super(R.layout.item_wp_ticket, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, JlWpTicketBean.DataBean.TicketsBean bean) {
        baseViewHolder
                .setText(R.id.coupon_sum, String.format("¥ %s", bean.getSum()))
                .setText(R.id.item_myticket_time, String.format("使用期至：%s", bean.getEndDate()));
        RelativeLayout rl_wp = baseViewHolder.getView(R.id.rl_wp_coupon);
        Bitmap bitMap = Utils.decodeSampledBitmapFromResource(mContext.getResources(), R.drawable.cas_wp, 100, 100);
        BitmapDrawable bd = new BitmapDrawable(mContext.getResources(), bitMap);
        rl_wp.setBackground(bd);
    }

}
