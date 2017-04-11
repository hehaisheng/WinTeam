package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_money_history;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.tool.TimeUtils;

/**
 * Created by Cicinnus on 2016/12/11.
 */

public class JlWpMoneyHistoryAdapter extends BaseQuickAdapter<JlWpMoneyHistoryBean.DataBean.ListBean> {

    public JlWpMoneyHistoryAdapter() {
        super(R.layout.item_wp_monty_history, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, JlWpMoneyHistoryBean.DataBean.ListBean listBean) {
        String type = "";
        switch (listBean.getType()){
            case 1:
                type = "建仓";
                break;
            case 2:
                type = "平仓";
                break;
            case 3:
                type = "充值";
                break;
            case 4:
                type = "提现";
                break;
            case 5:
                type = "手动充值";
                break;
            default:
                type = "未知操作";
                break;
        }
        boolean isIncome = listBean.getIncome() != null;

        baseViewHolder.setText(R.id.type,String.format("%s",type))
                .setText(R.id.pay_income,String.format("%s",isIncome?"收入":"支出"))
                .setText(R.id.buildTime, TimeUtils.dateYMDHM(listBean.getCreateTime()))
                .setText(R.id.money,String.format("%s%s",isIncome?"+":"-",isIncome?listBean.getIncome():listBean.getPay()))
                .setTextColor(R.id.money,isIncome?mContext.getResources().getColor(R.color.appcolor):mContext.getResources().getColor(R.color.lightgreen))
                .setText(R.id.remark,String.format("（%s）",listBean.getRemark()));
    }
}
