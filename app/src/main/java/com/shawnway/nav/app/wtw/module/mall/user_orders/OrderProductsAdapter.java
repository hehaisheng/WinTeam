package com.shawnway.nav.app.wtw.module.mall.user_orders;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.module.mall.bean.MallOrders;
import com.shawnway.nav.app.wtw.module.mall.productDetail.ProductDetailActivity;
import com.shawnway.nav.app.wtw.tool.GlideManager;

import java.util.List;

/**
 * Created by Kevin on 2016/11/17
 */

public class OrderProductsAdapter extends BaseQuickAdapter<MallOrders.ProductOrderTransactionsBean.ProductOrderEntitiesBean> {
    public OrderProductsAdapter(int layoutResId, List<MallOrders.ProductOrderTransactionsBean.ProductOrderEntitiesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final MallOrders.ProductOrderTransactionsBean.ProductOrderEntitiesBean productOrderEntitiesBean) {
        baseViewHolder.setText(R.id.mall_order_orderRemark, productOrderEntitiesBean.getProName())
                .setText(R.id.mall_order_productPoint, "积分" + productOrderEntitiesBean.getProDiscCsptPoint())
                .setText(R.id.mall_orders_productAmount, "x" + productOrderEntitiesBean.getProQuantity())
        ;
        GlideManager.loadImage(mContext, productOrderEntitiesBean.getProImg(), (ImageView) baseViewHolder.getView(R.id.iv_pro_image));

        baseViewHolder.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailActivity.start(mContext,productOrderEntitiesBean.getProId());
            }
        });
    }

}
