package com.shawnway.nav.app.wtw.module.user.ticket.ticket;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.tool.Utils;

/**
 * Created by Cicinnus on 2016/12/19.
 */

public class TicketAdapter extends BaseQuickAdapter<TicketBean.ListBean> {
    public TicketAdapter() {
        super(R.layout.item_ticket, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, TicketBean.ListBean ticketBean) {
        baseViewHolder
                .setText(R.id.coupon_sum, String.format("¥ %s", ticketBean.getCouponSum()))
                .setText(R.id.item_myticket_time, String.format("使用期至：%s", ticketBean.getEndDate()))
                .setBackgroundRes(R.id.rl_wp_coupon,R.drawable.cas_qh);

        RelativeLayout rl_wp = baseViewHolder.getView(R.id.rl_wp_coupon);
        Bitmap bitMap = Utils.decodeSampledBitmapFromResource(mContext.getResources(), R.drawable.cas_qh, 100, 100);
        BitmapDrawable bd = new BitmapDrawable(mContext.getResources(), bitMap);
        rl_wp.setBackground(bd);
    }
}
