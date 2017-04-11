package com.shawnway.nav.app.wtw.module.mall.shopping_car;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.module.mall.shopping_car.bean.ShoppingCarListBean;
import com.shawnway.nav.app.wtw.tool.GlideManager;

/**
 * Created by Cicinnus on 2016/11/17.
 */

public class ShoppingCarListAdapter extends BaseQuickAdapter<ShoppingCarListBean.ShoppingEntityListBean> {

    private OnAddQuantityClickListener onAddQuantityClickListener;
    private OnReduceQuantityClickListener onReduceQuantityClickListener;
    private OnDeleteShoppingCarProductClickListener onDeleteShoppingCarProductClickListener;
    private OnShoppingProductCheckedChangedListener onShoppingProductCheckedChangedListener;
    private OnProductImgClickListener onProductImgClickListener;
    private OnProductNameClickListener onProductNameClickListener;

    public ShoppingCarListAdapter() {
        super(R.layout.item_shopping_car_list, null);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final ShoppingCarListBean.ShoppingEntityListBean bean) {
        baseViewHolder
                .setText(R.id.productName, bean.getProName())
                .setText(R.id.proPoint, String.format("积分：%s", bean.getProDiscCsptPoint()));

        final TextView tv_quantity = baseViewHolder.getView(R.id.proQuantity);
        tv_quantity.setText(String.format("%s", bean.getQuantity()));

        ImageView iv = baseViewHolder.getView(R.id.proImg);
        GlideManager.loadImage(mContext, bean.getImg(), iv);


        ImageView add = baseViewHolder.getView(R.id.addQuantity);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onAddQuantityClickListener != null) {
                    onAddQuantityClickListener.onClick(bean);
                }
            }
        });

        ImageView reduce = baseViewHolder.getView(R.id.reduceQuantity);
        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReduceQuantityClickListener != null) {
                    onReduceQuantityClickListener.onClick(bean);

                }
            }
        });

        ImageView delete = baseViewHolder.getView(R.id.deletePro);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteShoppingCarProductClickListener != null) {
                    onDeleteShoppingCarProductClickListener.onClick(bean.getProId());
                }
            }
        });

        CheckBox cb_choice = baseViewHolder.getView(R.id.cb_choice);
        cb_choice.setChecked(bean.isChecked());
        cb_choice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onShoppingProductCheckedChangedListener != null) {
                    onShoppingProductCheckedChangedListener.onChanged(baseViewHolder.getAdapterPosition(), isChecked, bean);
                }
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProductImgClickListener != null) {
                    onProductImgClickListener.onClick(bean.getProId());
                }

            }
        });
        baseViewHolder.getView(R.id.productName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProductNameClickListener != null) {
                    onProductNameClickListener.onClick(bean.getProId());
                }
            }
        });

    }

    public void setOnAddQuantityClickListener(OnAddQuantityClickListener onAddQuantityClickListener) {
        this.onAddQuantityClickListener = onAddQuantityClickListener;
    }

    public void setOnDeleteShoppingCarProductClickListener(OnDeleteShoppingCarProductClickListener onDeleteShoppingCarProductClickListener) {
        this.onDeleteShoppingCarProductClickListener = onDeleteShoppingCarProductClickListener;
    }

    public void setOnReduceQuantityClickListener(OnReduceQuantityClickListener onReduceQuantityClickListener) {
        this.onReduceQuantityClickListener = onReduceQuantityClickListener;
    }

    public void setOnShoppingProductCheckedChangedListener(OnShoppingProductCheckedChangedListener onShoppingProductCheckedChangedListener) {
        this.onShoppingProductCheckedChangedListener = onShoppingProductCheckedChangedListener;
    }

    public void setOnProductImgClickListener(OnProductImgClickListener onProductImgClickListener) {
        this.onProductImgClickListener = onProductImgClickListener;
    }

    public void setOnProductNameClickListener(OnProductNameClickListener onProductNameClickListener) {
        this.onProductNameClickListener = onProductNameClickListener;
    }

    public interface OnAddQuantityClickListener {
        void onClick(ShoppingCarListBean.ShoppingEntityListBean bean);
    }

    public interface OnReduceQuantityClickListener {
        void onClick(ShoppingCarListBean.ShoppingEntityListBean bean);
    }

    public interface OnDeleteShoppingCarProductClickListener {
        void onClick(int proId);
    }

    public interface OnShoppingProductCheckedChangedListener {
        void onChanged(int position, boolean checked, ShoppingCarListBean.ShoppingEntityListBean bean);
    }

    public interface OnProductImgClickListener{
        void onClick(int proId);
    }

    public interface OnProductNameClickListener{
        void onClick(int proId);
    }
}
