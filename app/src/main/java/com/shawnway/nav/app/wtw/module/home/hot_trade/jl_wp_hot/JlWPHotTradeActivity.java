package com.shawnway.nav.app.wtw.module.home.hot_trade.jl_wp_hot;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.TranGoodsWrapper;
import com.shawnway.nav.app.wtw.net.JlWPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.LoadingView;

import java.util.ArrayList;
import java.util.List;

/**
 * 吉交所微盘选中页
 */
public class JlWPHotTradeActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = JlWPHotTradeActivity.class.getSimpleName();

    private RecyclerView selected_items_list;
    private List<TranGoodsWrapper.TranGoodsBean> newList = new ArrayList<>();
    private SelectedAdapter selectedAdapter;

    public static void getInstance(Context context) {
        Intent intent = new Intent(context, JlWPHotTradeActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_hot_trade;
    }

    @Override
    protected void initEventAndData() {
        initToolbar();
        selected_items_list = (RecyclerView) findViewById(R.id.selected_items_list);
        initData();
        FetchAllGoods();
    }

    private void initToolbar() {
        findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        TextView centerText = (TextView) findViewById(R.id.top_text_center);
        centerText.setVisibility(View.VISIBLE);
        centerText.setText("热门交易");
        TextView confirm = (TextView) findViewById(R.id.top_text_right);
        confirm.setVisibility(View.VISIBLE);
        confirm.setText("保存");
        confirm.setOnClickListener(this);
        ImageButton backButton = (ImageButton) findViewById(R.id.top_back);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.top_text_right:
                //保存两个列表的内容到数据库
                saveListDataToSP();
                ToastUtil.showShort(this, "保存成功");
                finish();
                break;
        }
    }

    private void saveListDataToSP() {
        for (int i = 0; i < newList.size(); i++) {
            if (newList.get(i).isChecked()) {
                SPUtils.getInstance(mContext, SPUtils.SP_JL_WP_HOT_SELECTED)
                        .putString(newList.get(i).getProductNo(), newList.get(i).getProductName())
                        .apply();
            } else {
                SPUtils.getInstance(mContext, SPUtils.SP_JL_WP_HOT_SELECTED)
                        .putString(newList.get(i).getProductNo(), "")
                        .apply();
            }
        }
    }


    /**
     * 加载已选择的和全部
     */
    private void initData() {

        selectedAdapter = new SelectedAdapter();
        selected_items_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        selected_items_list.setAdapter(selectedAdapter);

    }


    /**
     * 从网络获取所有的货物列表
     */
    private void FetchAllGoods() {
        final LoadingView loadingGif = new LoadingView(mContext);
        loadingGif.show();
        JlWPRetrofitClient
                .getInstance()
                .api()
                .getTranGoods()
                .compose(SchedulersCompat.<TranGoodsWrapper>applyIoSchedulers())
                .subscribe(new BaseSubscriber<TranGoodsWrapper>() {
                    @Override
                    public void onSuccess(TranGoodsWrapper bean) {
                        loadingGif.dismiss();
                        newList.addAll(bean.getData());
                        for (int i = 0; i < newList.size(); i++) {
                            for (int j = newList.size() - 1; j > i; j--) {
                                if (newList.get(i).getProductName().equals(newList.get(j).getProductName())) {
                                    newList.remove(j);
                                }
                            }
                        }
                        for (int i = 0; i < newList.size(); i++) {
                            if (!SPUtils.getInstance(mContext, SPUtils.SP_JL_WP_HOT_SELECTED)
                                    .getString(newList.get(i).getProductNo(), "").equals("")) {
                                TranGoodsWrapper.TranGoodsBean newBean = newList.get(i);
                                newBean.setChecked(true);
                                newList.set(i,newBean);
                            } else {
                                TranGoodsWrapper.TranGoodsBean newBean = newList.get(i);
                                newList.set(i,newBean);
                            }
                        }

                        selectedAdapter.setNewData(newList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Logger.e(e.getMessage());
                        ToastUtil.showShort(mContext, "获取列表失败");
                    }
                });
    }


    static class SelectedAdapter extends BaseQuickAdapter<TranGoodsWrapper.TranGoodsBean> {


        public SelectedAdapter() {
            super(R.layout.activity_hot_item, null);
        }

        @Override
        protected void convert(final BaseViewHolder baseViewHolder, final TranGoodsWrapper.TranGoodsBean bean) {
            baseViewHolder.setText(R.id.activity_hot_tv, bean.getProductName());

            final CheckBox cb_choice = baseViewHolder.getView(R.id.cb_choice);
            cb_choice.setChecked(bean.isChecked());

            baseViewHolder.convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cb_choice.isChecked()) {
                        bean.setChecked(false);
                    } else {
                        bean.setChecked(true);
                    }
                    notifyItemChanged(baseViewHolder.getAdapterPosition());
                }
            });
        }

    }
}
