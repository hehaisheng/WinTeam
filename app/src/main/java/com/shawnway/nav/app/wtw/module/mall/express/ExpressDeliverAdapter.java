package com.shawnway.nav.app.wtw.module.mall.express;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.module.mall.bean.ExpressDeliver;

import java.util.List;

/**
 * Created by Kevin on 2016/11/24
 */

public class ExpressDeliverAdapter extends BaseQuickAdapter<ExpressDeliver.ExpressTracesBean> {
    public ExpressDeliverAdapter(int layoutResId, List<ExpressDeliver.ExpressTracesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ExpressDeliver.ExpressTracesBean expressTracesBean) {
        baseViewHolder.setText(R.id.acceptStation, expressTracesBean.getAcceptStation())
                .setText(R.id.acceptTime, expressTracesBean.getAcceptTime());
    }
}
