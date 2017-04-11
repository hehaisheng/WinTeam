package com.shawnway.nav.app.wtw.module.quotation.wp.wp_list;

import android.graphics.drawable.GradientDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.WpDetailActivity;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscription;


public class WpListFragment extends BaseFragment<WpListPresenter> implements WpListContract.IWpListView {

    private static final String TAG = "JlWpListFragment";

    @BindView(R.id.progress_layout)
    ProgressLayout progressLayout;
    @BindView(R.id.rv_wp)
    RecyclerView rvWp;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private List<QuotationsWPBean> mGoods = new ArrayList<>();
    private WPListAdapter wpListAdapter;
    private boolean isVisible;
    private boolean isFirst = true;
    private Subscription subscription;

    public static WpListFragment getInstance() {
        return new WpListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quotations_list;
    }


    @Override
    protected WpListPresenter getPresenter() {
        return new WpListPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        rvWp.setLayoutManager(new LinearLayoutManager(mContext));
        wpListAdapter = new WPListAdapter();
        View emptyView = mContext.getLayoutInflater().inflate(R.layout.layout_no_data, (ViewGroup) rvWp.getParent(), false);
        ((TextView) emptyView.findViewById(R.id.no_data_text)).setText("暂时没有数据");
        wpListAdapter.setEmptyView(emptyView);
        rvWp.setAdapter(wpListAdapter);
        swipe.setColorSchemeColors(getResources().getColor(R.color.lightred));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getList();
            }
        });
    }

    private void startSubscription() {
        subscription = Observable.interval(0,4, TimeUnit.SECONDS)
                .compose(SchedulersCompat.<Long>applyIoSchedulers())
                .subscribe(new BaseSubscriber<Long>() {
                    @Override
                    public void onSuccess(Long aLong) {
                        mPresenter.getList();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible) {
            startSubscription();
        }
    }

    @Override
    public void addList(QuotationsWPBean[] quotationsWPBeen) {
        mGoods.clear();
        Collections.addAll(mGoods, quotationsWPBeen);
        wpListAdapter.setNewData(mGoods);
    }

    @Override
    public void showLoading() {
        if (isFirst) {
            isFirst = false;
            swipe.setRefreshing(true);
        }
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
        if (!progressLayout.isContent()) {
            progressLayout.showContent();
        }
    }

    @Override
    public void showError(String errorMsg) {
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
        stopSubscription();
        ToastUtil.showShort(mContext, errorMsg);
        stopSubscription();
        progressLayout.showError(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSubscription();
            }
        });
    }

    private void stopSubscription() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            stopSubscription();
            startSubscription();
        } else {
            isVisible = false;
            stopSubscription();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopSubscription();
    }

    private class WPListAdapter extends BaseQuickAdapter<QuotationsWPBean> {
        private DecimalFormat dlf;
        private String productName;

        public WPListAdapter() {
            super(R.layout.item_quopage, null);
            dlf = new DecimalFormat("0.00");
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, final QuotationsWPBean bean) {

            double chgNum = bean.getLatestPrice() - bean.getPriceAtEndLastday();//差值
            double chg = chgNum / bean.getPriceAtEndLastday();//涨跌幅
            productName = "";
            switch (bean.getProductContract()) {
                case "CU":
                    productName = "粤铜";
                    break;
                case "OIL":
                    productName = "粤油";
                    break;
                case "XAG1":
                    productName = "粤银";
                    break;
            }
            baseViewHolder
                    .setBackgroundColor(R.id.view_indicator, chgNum >= 0 ?
                            mContext.getResources().getColor(R.color.appcolor) :
                            mContext.getResources().getColor(R.color.lightgreen))
                    .setText(R.id.item_goodsname, productName)
                    .setText(R.id.goods_id, bean.getProductContract())
                    .setText(R.id.item_quopage_lasttrade, String.format("%s", bean.getLatestPrice()))
                    .setTextColor(R.id.item_quopage_lasttrade, chgNum >= 0 ? mContext.getResources().getColor(R.color.lightred) :
                            mContext.getResources().getColor(R.color.lightgreen))
                    .setText(R.id.item_quopage_chg, String.format("%s%%", dlf.format(chg * 100)));

            GradientDrawable background = (GradientDrawable) baseViewHolder.getView(R.id.item_quopage_chg).getBackground();
            background.setColor(chgNum >= 0 ?
                    mContext.getResources().getColor(R.color.appcolor) :
                    mContext.getResources().getColor(R.color.lightgreen));

            baseViewHolder.convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WpDetailActivity.getInstance(mContext, bean);
                }
            });
        }
    }
}
