package com.shawnway.nav.app.wtw.module.mall.user_orders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.module.mall.bean.MallOrders;
import com.shawnway.nav.app.wtw.tool.TimeUtils;

import java.util.List;

/**
 * Created by Kevin on 2016/11/17
 */

public class AllUserOrdersAdapter extends BaseQuickAdapter<MallOrders.ProductOrderTransactionsBean> {
    private OnItemClickListener onItemClickListener;


    public AllUserOrdersAdapter(int layoutResId, List<MallOrders.ProductOrderTransactionsBean> datas) {
        super(layoutResId, datas);

    }

    /**
     * 初始化一个订单的商品种类
     */
    private void initOrderProductsRV(RecyclerView recyclerView, List<MallOrders.ProductOrderTransactionsBean.ProductOrderEntitiesBean> datas) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        OrderProductsAdapter orderProductsAdapter = new OrderProductsAdapter(R.layout.item_order_products, datas);
        recyclerView.setAdapter(orderProductsAdapter);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final MallOrders.ProductOrderTransactionsBean productOrderTransactionsBean) {
        baseViewHolder.setText(R.id.mall_order_orderTime, TimeUtils.dateYMDHM(productOrderTransactionsBean.getOrderDate()))
                .setText(R.id.mall_order_total_productAmount, "共" + productOrderTransactionsBean.getQuantity() + "件商品")
                .setText(R.id.mall_order_total_product_point, productOrderTransactionsBean.getPoint() + "积分")
                .setText(R.id.mall_order_tradeStatus, productOrderTransactionsBean.getDesc())
                .addOnClickListener(R.id.button_received)
                .addOnClickListener(R.id.button_del_order)
                .addOnClickListener(R.id.button_check_Logistics);


        RelativeLayout layout_order_button = baseViewHolder.getView(R.id.layout_order_button);
        TextView button_del_order = baseViewHolder.getView(R.id.button_del_order);
        TextView button_recevied = baseViewHolder.getView(R.id.button_received);
        TextView button_check_Logistics = baseViewHolder.getView(R.id.button_check_Logistics);

        button_recevied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(v, baseViewHolder, productOrderTransactionsBean);
            }
        });

        button_del_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(v, baseViewHolder, productOrderTransactionsBean);
            }
        });

        button_check_Logistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(v, baseViewHolder, productOrderTransactionsBean);
            }
        });

        if (productOrderTransactionsBean.getStatus() == Code.STATUS_NOSHIPPED) {
            layout_order_button.setVisibility(View.GONE);
        } else if (productOrderTransactionsBean.getStatus() == Code.STATUS_SHIPPED) {
            layout_order_button.setVisibility(View.VISIBLE);
            button_recevied.setVisibility(View.VISIBLE);
            button_check_Logistics.setVisibility(View.VISIBLE);
            button_del_order.setVisibility(View.GONE);
        } else if (productOrderTransactionsBean.getStatus() == Code.STATUS_RECEVIED) {
            layout_order_button.setVisibility(View.VISIBLE);
            button_del_order.setVisibility(View.VISIBLE);
            button_recevied.setVisibility(View.GONE);
            button_check_Logistics.setVisibility(View.GONE);
        }
        RecyclerView rvOrderProducts = baseViewHolder.getView(R.id.rv_mall_order_products);

        initOrderProductsRV(rvOrderProducts, productOrderTransactionsBean.getProductOrderEntities());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(View view, BaseViewHolder baseViewHolder, MallOrders.ProductOrderTransactionsBean productOrderTransactionsBean);
    }

}
