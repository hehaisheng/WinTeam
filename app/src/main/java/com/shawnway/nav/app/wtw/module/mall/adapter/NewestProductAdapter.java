package com.shawnway.nav.app.wtw.module.mall.adapter;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.module.mall.bean.NewestProductBean;
import com.shawnway.nav.app.wtw.module.mall.productDetail.ProductDetailActivity;
import com.shawnway.nav.app.wtw.tool.GlideManager;

/**
 * Created by Administrator on 2016/10/26.
 * Administrator github = "https://github.com/Cicinnus0407"
 */

public class NewestProductAdapter extends BaseQuickAdapter<NewestProductBean.AllProductEntityBean> {



    public NewestProductAdapter() {
        super(R.layout.item_mal_products,null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final NewestProductBean.AllProductEntityBean bean) {
        baseViewHolder
                .setText(R.id.product_name,bean.getProName())
                .setText(R.id.productPrice,String.format("积分%s",bean.getProDiscCsptPoint()))
                .setText(R.id.changedAmount,String.format("已兑换%s",bean.getProExchangedAmount()));
        ImageView product_pic = baseViewHolder.getView(R.id.product_pic);
        GlideManager.loadImage(mContext,bean.getProImg1(),product_pic);

        TextView realPrice = baseViewHolder.getView(R.id.marketPrice);
        realPrice.setText(String.format("¥%s",bean.getProPrice()));
        realPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        baseViewHolder.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailActivity.start(mContext, bean.getProId());
            }
        });
    }


}
