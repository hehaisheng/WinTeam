package com.shawnway.nav.app.wtw.module.mall.exchange_list;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.module.mall.bean.MallOrders;
import com.shawnway.nav.app.wtw.module.mall.productDetail.ProductDetailActivity;
import com.shawnway.nav.app.wtw.tool.GlideManager;
import com.shawnway.nav.app.wtw.tool.TimeUtils;

/**
 * Created by Cicinnus on 2016/11/29.
 */

public class ExchangeListAdapter extends BaseQuickAdapter<MallOrders.ProductOrderTransactionsBean.ProductOrderEntitiesBean> {

    public ExchangeListAdapter() {
        super(R.layout.item_exchange_list, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final MallOrders.ProductOrderTransactionsBean.ProductOrderEntitiesBean bean) {
        baseViewHolder
                .setText(R.id.productName,bean.getProName())
                .setText(R.id.time, String.format("%s",TimeUtils.dateYMDHM(bean.getOrderDate())))
                .setText(R.id.point,String.format("%s",bean.getProDiscCsptPoint()));

        GlideManager.loadImage(mContext,bean.getProImg(), (ImageView) baseViewHolder.getView(R.id.iv_pro));

        baseViewHolder.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailActivity.start(mContext,bean.getProId());
            }
        });
    }
}
