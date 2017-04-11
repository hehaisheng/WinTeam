package com.shawnway.nav.app.wtw.module.quotation.international.international_list;

import android.graphics.drawable.GradientDrawable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.module.quotation.international.InternationalListBean;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/9/26.
 */

public class InternationalListAdapter extends BaseQuickAdapter<InternationalListBean.InstrumentRealmarketBean> {

    private OnItemClickListener onItemClickListener;
    private final DecimalFormat decimalFormat;
    private boolean mIsReal;

    public InternationalListAdapter(boolean real) {
        super(R.layout.item_international, null);
        decimalFormat = new DecimalFormat("0.00");
        this.mIsReal = real;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final InternationalListBean.InstrumentRealmarketBean bean) {


        double rate = bean.getLastprice() - bean.getCloseprice();
        double percent = rate / bean.getCloseprice();
        int colors;
        if (rate >= 0) {
            colors = R.color.appcolor;
        } else {
            colors = R.color.lightgreen;
        }

        baseViewHolder
                .setText(R.id.goods_name, String.format("%s", bean.getSecurityDesc()))
                .setText(R.id.change_rate, String.format("%s %%", decimalFormat.format(percent * 100)))
                .setText(R.id.latest_price, String.format("%s",bean.getLastprice()))
                .setText(R.id.goods_id,bean.getInstrumentid())
                .setTextColor(R.id.latest_price, mContext.getResources().getColor(colors));

        GradientDrawable background = (GradientDrawable) baseViewHolder.getView(R.id.change_rate).getBackground();
        background.setColor(mContext.getResources().getColor(colors));

        if (mIsReal) {
            baseViewHolder.getView(R.id.tv_isReal).setVisibility(View.GONE);
        }else {
            baseViewHolder.getView(R.id.tv_isReal).setVisibility(View.VISIBLE);
        }

        View view = baseViewHolder.getView(R.id.view_indicator);
        view.setBackgroundColor(mContext.getResources().getColor(colors));
        baseViewHolder.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(bean);
                }
            }
        });

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(InternationalListBean.InstrumentRealmarketBean bean);
    }

}
