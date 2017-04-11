package com.shawnway.nav.app.wtw.module.quotation.international.positions.today;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.tool.InternationalOrderUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.shawnway.nav.app.wtw.R.id.goods_name;

/**
 * Created by Administrator on 2016/10/13.
 */

public class TodayTradingAdapter extends BaseQuickAdapter<TodayTradingBean.WareHouseInfosBean> {


    private final SimpleDateFormat format;
    private final DecimalFormat decimalFormat;

    public TodayTradingAdapter() {
        super(R.layout.item_positions, null);
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        decimalFormat = new DecimalFormat("0.00");
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, TodayTradingBean.WareHouseInfosBean bean) {


        String orderType;
        int textColor;
        if (bean.getOrderSide() == 1) {
            orderType = "涨";
            textColor = R.color.lightred;
        } else {
            orderType = "跌";
            textColor = R.color.lightgreen;
        }

            baseViewHolder
                    .setText(goods_name, String.format("%s",bean.getDesc()))
                    .setText(R.id.executionDate, format.format(bean.getOrderDate()))
                    .setTextColor(R.id.order_side, mContext.getResources().getColor(textColor))
                    .setText(R.id.order_side, String.format("买%s%s手", orderType, bean.getOrderQuantity()))
                    .setText(R.id.executionPrice, String.format("下单价      ：%s", decimalFormat.format(bean.getExecutionPrice())))
                    .setText(R.id.orderStatus, String.format("订单状态  ：%s", InternationalOrderUtil.getOrderStatus(bean.getOrderStatus())))
                    .setText(R.id.orderId, bean.getOrderId());


    }
}
