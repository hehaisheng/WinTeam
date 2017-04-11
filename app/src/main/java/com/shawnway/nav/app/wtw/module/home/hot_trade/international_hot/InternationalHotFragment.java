package com.shawnway.nav.app.wtw.module.home.hot_trade.international_hot;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.module.quotation.international.InternationalListBean;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.NetWorkUtil;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.Utils;
import com.shawnway.nav.app.wtw.view.DividerItemDecoration;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Cicinnus on 2016/11/1.
 * 首页热门交易国际期货模块
 */

public class InternationalHotFragment extends BaseFragment {

    @BindView(R.id.rv_hot)
    RecyclerView rv_hot;
    private IntHorizontalAdapter intHorizontalAdapter;//横向的rvAdapter
    private List<InternationalListBean.InstrumentRealmarketBean> selectedList = new ArrayList<>();
    private Handler handler;
    private IntHotTradeRunnable intHotTradeRunnable;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot;
    }

    @Override
    protected void initEventAndData() {
        intHorizontalAdapter = new IntHorizontalAdapter();
        rv_hot.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_hot.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL_LIST));
        rv_hot.setAdapter(intHorizontalAdapter);
        handler = new Handler();
        intHotTradeRunnable = new IntHotTradeRunnable();
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.post(intHotTradeRunnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(intHotTradeRunnable);
    }

    private void FetchIntData() {

        RetrofitClient
                .getInstance()
                .api()
                .getInternationalList()
                .compose(SchedulersCompat.<InternationalListBean>applyIoSchedulers())
                .subscribe(new BaseSubscriber<InternationalListBean>() {
                    @Override
                    public void onSuccess(InternationalListBean bean) {
                        selectedList.clear();
                        for (int i = 0; i < bean.getInstrumentRealmarket().size(); i++) {
                            if (!SPUtils.getInstance(mContext, SPUtils.SP_INT_HOT_SELECTED)
                                    .getString(bean.getInstrumentRealmarket().get(i).getSecurityGrpCode(), "").equals("")) {
                                selectedList.add(bean.getInstrumentRealmarket().get(i));
                                intHorizontalAdapter.notifyItemChanged(i);
                            }
                        }
                        if (selectedList.size() > 0) {
                            intHorizontalAdapter.setNewData(selectedList);
                        } else {
                            handler.removeCallbacks(intHotTradeRunnable);
                            selectedList.clear();
                            intHorizontalAdapter.setNewData(selectedList);
                            View view = View.inflate(mContext, R.layout.layout_no_selected_hot_trade, null);
                            LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_no_selected);
                            DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
                            int width = dm.widthPixels;
                            ll.setMinimumWidth(width);
                            ImageView plus = (ImageView) view.findViewById(R.id.iv_plus);
                            plus.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(mContext, InternationalHotTradeActivity.class));
                                }
                            });
                            intHorizontalAdapter.setEmptyView(view);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        handler.removeCallbacks(intHotTradeRunnable);
                        selectedList.clear();
                        intHorizontalAdapter.setNewData(selectedList);
                        View view = View.inflate(mContext, R.layout.layout_no_selected_hot_trade, null);
                        LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_no_selected);
                        WindowManager wm = (WindowManager) mContext
                                .getSystemService(Context.WINDOW_SERVICE);
                        int width = wm.getDefaultDisplay().getWidth() - Utils.dp2px(mContext, 26);
                        ll.setMinimumWidth(width);
                        ImageView plus = (ImageView) view.findViewById(R.id.iv_plus);
                        plus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(mContext, InternationalHotTradeActivity.class));
                            }
                        });
                        intHorizontalAdapter.setEmptyView(view);
                    }
                });

    }

    private class IntHotTradeRunnable implements Runnable {
        @Override
        public void run() {
            if (NetWorkUtil.isNetworkConnected(mContext)) {
                FetchIntData();
                handler.postDelayed(this, 4000);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            handler.post(intHotTradeRunnable);
        } else {
            handler.removeCallbacks(intHotTradeRunnable);
        }
    }

    private static class IntHorizontalAdapter extends BaseQuickAdapter<InternationalListBean.InstrumentRealmarketBean> {

        private DecimalFormat decimalFormat;
        private OnIntHotTradeClickListener onIntHotTradeClickListener;

        public IntHorizontalAdapter() {
            super(R.layout.item_hot_trade, null);
            decimalFormat = new DecimalFormat("0.00");

        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, final InternationalListBean.InstrumentRealmarketBean bean) {


            double rate = bean.getLastprice() - bean.getCloseprice();
            double percent = rate / bean.getCloseprice();

            DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
            int width = dm.widthPixels;
            LinearLayout linearLayout = baseViewHolder.getView(R.id.ll_hot_trade_item);
            linearLayout.setMinimumWidth(width / 3);

            int colors;
            if (rate >= 0) {
                colors = R.color.lightred;
            } else {
                colors = R.color.lightgreen;
            }

            baseViewHolder.setText(R.id.tv_hot_title, String.format("%s", bean.getSecurityDesc()))
                    .setText(R.id.chang_rate, String.format("(%s %%)", decimalFormat.format(percent * 100)))
                    .setText(R.id.change_price, String.format("%s", decimalFormat.format(rate)))
                    .setText(R.id.tv_hot_price, String.format("%s", bean.getLastprice()))
                    .setTextColor(R.id.tv_hot_title, mContext.getResources().getColor(colors))
                    .setTextColor(R.id.tv_hot_price, mContext.getResources().getColor(colors))
                    .setTextColor(R.id.change_price, mContext.getResources().getColor(colors))
                    .setTextColor(R.id.chang_rate, mContext.getResources().getColor(colors));

            baseViewHolder.convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onIntHotTradeClickListener != null) {
                        onIntHotTradeClickListener.onclick(bean);
                    }
                }
            });
        }

        public void setOnIntHotTradeClickListener(OnIntHotTradeClickListener onIntHotTradeClickListener) {
            this.onIntHotTradeClickListener = onIntHotTradeClickListener;
        }

        public interface OnIntHotTradeClickListener {
            void onclick(InternationalListBean.InstrumentRealmarketBean bean);
        }
    }
}
