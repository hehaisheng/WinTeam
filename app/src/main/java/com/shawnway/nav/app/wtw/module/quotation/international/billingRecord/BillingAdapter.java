package com.shawnway.nav.app.wtw.module.quotation.international.billingRecord;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.tool.InternationalOrderUtil;
import com.shawnway.nav.app.wtw.tool.TimeUtils;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/10/14.
 * Administrator github = "https://github.com/Cicinnus0407"
 */

public class BillingAdapter extends BaseQuickAdapter<BillRecordBean.WareHouseInfosBean> {

    private final DecimalFormat decimalFormat;

    public BillingAdapter() {
        super(R.layout.item_billing, null);
        decimalFormat = new DecimalFormat("0.00");

    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, BillRecordBean.WareHouseInfosBean bean) {
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
                .setText(R.id.goods_name, String.format("%s", bean.getDesc()))
                .setText(R.id.executionDate, TimeUtils.dateYMDHM(bean.getOrderDate()))
                .setText(R.id.order, String.format("买%s%s手", orderType, bean.getExecutionQuantity()))
                .setTextColor(R.id.order, mContext.getResources().getColor(textColor))
                .setText(R.id.executionPrice, String.format("建仓价：%s",
                        bean.getExecutionPrice()))
                .setText(R.id.conclusionPirce, String.format("平仓价：%s",
                        bean.getClosePositionexecutionPrice()))
                .setText(R.id.earn, String.format("%s元(RMB)", bean.getOrderPl()))
                .setText(R.id.conclusionDate, TimeUtils.dateYMDHM(bean.getLastexecutionDate()))
                .setTextColor(R.id.earn, bean.getOrderPl() <= 0 ?
                        mContext.getResources().getColor(R.color.lightgreen)
                        : mContext.getResources().getColor(R.color.lightred))
                .setText(R.id.orderType, InternationalOrderUtil.getOrderType(bean.getOrderType()));

    }
}
