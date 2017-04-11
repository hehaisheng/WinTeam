package com.shawnway.nav.app.wtw.module.home.hot_trade.jl_wp_hot;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;
import com.shawnway.nav.app.wtw.net.JlWPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.NetWorkUtil;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;


/**
 * Created by Cicinnus on 2016/11/1.
 * 吉交所微盘热门交易
 */

public class JlWPHotFragment extends BaseFragment {

    @BindView(R.id.rv_hot)
    RecyclerView rv_hot_trade;
    private JlHotTradeHorizontalAdapter jlhotTradeAdapter;
    private Handler mHandler = new Handler();
    private HotTradeCall hotTradeCall;
    private List<QuotationsWPBean> mGoods = new ArrayList<>();
    private List<QuotationsWPBean> mSelected = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot;
    }


    @Override
    protected void initEventAndData() {
        jlhotTradeAdapter = new JlHotTradeHorizontalAdapter();
        rv_hot_trade.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        rv_hot_trade.setAdapter(jlhotTradeAdapter);
        //分割线
        rv_hot_trade.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL_LIST));

        View view = View.inflate(mContext, R.layout.layout_no_selected_hot_trade, null);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_no_selected);
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        ll.setMinimumWidth(width);
        ImageView plus = (ImageView) view.findViewById(R.id.iv_plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, JlWPHotTradeActivity.class));
            }
        });
        jlhotTradeAdapter.setEmptyView(view);
        hotTradeCall = new HotTradeCall();
    }

    @Override
    public void onResume() {
        super.onResume();

        mHandler.post(hotTradeCall);

    }

    /**
     * 从网络获取数据
     */
    private void fetchHotTradeData() {
        jlhotTradeAdapter.isLoading();
        JlWPRetrofitClient
                .getInstance()
                .api()
                .getWPPrice()
                .compose(SchedulersCompat.<QuotationsWPBean[]>applyIoSchedulers())
                .subscribe(new BaseSubscriber<QuotationsWPBean[]>() {
                    @Override
                    public void onSuccess(QuotationsWPBean[] bean) {
                        mSelected.clear();
                        mGoods.clear();
                        Collections.addAll(mGoods, bean);
                        for (int i = 0; i < mGoods.size(); i++) {
                            if (!SPUtils.getInstance(mContext, SPUtils.SP_JL_WP_HOT_SELECTED)
                                    .getString(mGoods.get(i).getProductContract(), "").equals("")) {
                                mSelected.add(mGoods.get(i));
                            }
                        }
                        if (mSelected.size() == 0) {
                            mHandler.removeCallbacks(hotTradeCall);
                            jlhotTradeAdapter.setNewData(mSelected);
                        } else {
                            jlhotTradeAdapter.setNewData(mSelected);
                        }
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(hotTradeCall);
    }

    private class HotTradeCall implements Runnable {
        @Override
        public void run() {
            if (NetWorkUtil.isNetworkConnected(mContext)) {
                fetchHotTradeData();
                mHandler.postDelayed(this, 4000);
            }
        }
    }
}
