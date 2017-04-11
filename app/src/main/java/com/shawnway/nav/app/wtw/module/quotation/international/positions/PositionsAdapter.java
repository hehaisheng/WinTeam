package com.shawnway.nav.app.wtw.module.quotation.international.positions;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2016/10/10.
 */

public class PositionsAdapter extends BaseQuickAdapter<PositionsBean.WareHouseInfosBean> {

    public OnSellClickListener onSellClickListener;
    private final DecimalFormat decimalFormat;
    private final SimpleDateFormat format;
    private List<PointValueResultBean.ListBean> mPointValueList;
    private double pointValue;

    public PositionsAdapter() {
        super(R.layout.item_positions_holding, null);
        mPointValueList = new ArrayList<>();
        decimalFormat = new DecimalFormat("0.00");
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    }

    public void setPointValueList(List<PointValueResultBean.ListBean> list) {
        this.mPointValueList = list;
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final PositionsBean.WareHouseInfosBean bean) {

        for (int i = 0; i < mPointValueList.size(); i++) {
            if (mPointValueList.get(i).getSecurityGroupCode().equals(bean.getSecurityGrpCode())) {
                pointValue = mPointValueList.get(i).getValue() *
                        mPointValueList.get(i).getExchangeRate() /
                        mPointValueList.get(i).getTickSize();
            }
        }
        //差价
        double realPrice;
        double sysStopLoss = ((bean.getSytemAutoStopLossPrice() -
                bean.getExecutionPrice()) * pointValue)*bean.getExecutionQuantity();
        double cusStopLoss = ((bean.getCustomerAutoStopLossPrice() -
                bean.getExecutionPrice()) * pointValue)*bean.getExecutionQuantity();
        double sysStopWin = ((bean.getSytemAutoStopWinPrice() -
                bean.getExecutionPrice()) * pointValue)*bean.getExecutionQuantity();
        double cusStopWin = ((bean.getCustomerAutoStopWinPrice() -
                bean.getExecutionPrice()) * pointValue)*bean.getExecutionQuantity();
        String orderType;
        int textColor;

        if (bean.getOrderSide() == 1) {
            orderType = "涨";
            textColor = R.color.lightred;
            realPrice = bean.getMarketPrice() - bean.getExecutionPrice();

        } else {
            orderType = "跌";
            textColor = R.color.lightgreen;
            realPrice = bean.getExecutionPrice() - bean.getMarketPrice();

        }

        baseViewHolder
                .setText(R.id.goods_name, String.format("%s", bean.getDesc()))
                .setText(R.id.executionDate, format.format(bean.getLastexecutionDate()))
                .setTextColor(R.id.order_side, mContext.getResources().getColor(textColor))
                .setText(R.id.order_side, String.format("买%s%s手", orderType, bean.getExecutionQuantity()))
                .setText(R.id.stopWin, String.format("止盈%s元",
                        bean.getCustomerAutoStopWinPrice() == 0 ?
                                decimalFormat.format(sysStopWin) :
                                decimalFormat.format(cusStopWin)))
                .setText(R.id.stopLoss, String.format("止损%s元",
                        bean.getCustomerAutoStopLossPrice() == 0 ?
                                decimalFormat.format(sysStopLoss) :
                                decimalFormat.format(cusStopLoss)))
                .setText(R.id.executionPrice, String.format("成交价:%s点", bean.getExecutionPrice()))
                .setText(R.id.orderId, bean.getOrderId())
                .setTextColor(R.id.marketPrice, realPrice > 0 ?
                        mContext.getResources().getColor(R.color.lightred) :
                        mContext.getResources().getColor(R.color.lightgreen))
                .setText(R.id.currentPrice, String.format("当前价:%s点", bean.getMarketPrice()));


        /**
         * 动态盈亏
         */
        if (bean.getMarketPrice() != 0  && bean.getExecutionPrice() > 0 && pointValue != 0) {
            baseViewHolder.setText(R.id.marketPrice, String.format("%s元",
                    decimalFormat.format(realPrice * pointValue * bean.getExecutionQuantity())));
        }


        baseViewHolder.getView(R.id.sellProduct)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onSellClickListener != null) {
                            onSellClickListener.onClick(baseViewHolder.getAdapterPosition(), bean);
                        }
                    }
                });
    }


    public void setOnSellClickListener(OnSellClickListener onSellClickListener) {
        this.onSellClickListener = onSellClickListener;
    }

    public interface OnSellClickListener {
        void onClick(int position, PositionsBean.WareHouseInfosBean bean);
    }
}
