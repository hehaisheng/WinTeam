package com.shawnway.nav.app.wtw.module.mall.filter.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.module.mall.filter.bean.FilterListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/27.
 * Administrator github = "https://github.com/Cicinnus0407"
 */

public class SingleChoiceAdapter extends RecyclerView.Adapter<SingleChoiceAdapter.ViewHolder> {

    private int selectedPos = -1;
    private List<FilterListBean.AllProductTypeBean> mData;
    private OnFilterListClickListener onFilterListClickListener;

    public SingleChoiceAdapter() {
        mData =new ArrayList<>();

    }
    public void addData(List<FilterListBean.AllProductTypeBean>data){
        mData.addAll(data);
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).isSelect) {
                selectedPos = i;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public SingleChoiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_single_choie_filter, parent, false));
    }

    @Override
    public void onBindViewHolder(final SingleChoiceAdapter.ViewHolder holder, int position) {
        holder.tv.setText(mData.get(holder.getAdapterPosition()).getTypeName());
        if (mData.get(holder.getAdapterPosition()).isSelect) {
            holder.tv.setBackgroundColor(Color.parseColor("#FFE67864"));
        } else {
            holder.tv.setBackgroundColor(Color.WHITE);
        }
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPos != holder.getAdapterPosition()) {
                    mData.get(selectedPos).isSelect = false;
                    notifyItemChanged(selectedPos);
                    selectedPos = holder.getAdapterPosition();
                    mData.get(selectedPos).isSelect = true;
                    notifyItemChanged(selectedPos);
                    if (onFilterListClickListener != null) {
                        onFilterListClickListener.click(mData.get(holder.getAdapterPosition()));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnFilterListClickListener(OnFilterListClickListener onFilterListClickListener) {
        this.onFilterListClickListener = onFilterListClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_classification);
        }
    }

    public interface OnFilterListClickListener{
        void click(FilterListBean.AllProductTypeBean bean);
    }
}
