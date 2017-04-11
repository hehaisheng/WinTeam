package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_list;

import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
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
import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;
import com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.JlWpDetailActivity;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;


public class JlWpListFragment extends BaseFragment<JlWpListPresenter> implements JlWpListContract.IWpListView {


    @BindView(R.id.progress_layout)
    ProgressLayout progressLayout;
    @BindView(R.id.rv_wp)
    RecyclerView rvWp;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private List<QuotationsWPBean> mGoods = new ArrayList<>();
    private QuotationsRunnable run;

    private Handler handler = new Handler();
    private WPListAdapter wpListAdapter;
    private boolean isVisible;
    private boolean isFirst = true;

    public static JlWpListFragment getInstance() {
        return new JlWpListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quotations_list;
    }


    @Override
    protected JlWpListPresenter getPresenter() {
        return new JlWpListPresenter(mContext, this);
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
        run = new QuotationsRunnable();
        handler.post(run);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible) {
            handler.post(run);
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
        ToastUtil.showShort(mContext, errorMsg);
        handler.removeCallbacks(run);
        progressLayout.showError(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getList();
            }
        });
    }

    private class QuotationsRunnable implements Runnable {
        @Override
        public void run() {
            mPresenter.getList();
            handler.postDelayed(this, 4000);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            handler.removeCallbacks(run);
            handler.postDelayed(run, 3000);
        } else {
            isVisible = false;
            handler.removeCallbacks(run);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(run);
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
                    productName = "电解铜";
                    break;
                case "OIL":
                    productName = "燃料油";
                    break;
                case "XAG1":
                    productName = "银制品";
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
//                    final NormalDialog normalDialog = NormalDialog.newInstance("功能正在发开","如需体验其他功能请移步其他页面","确认");
//                    normalDialog.setOnDialogClickListener(new NormalDialog.OnConfirmClickListener() {
//                        @Override
//                        public void onClick() {
//                            normalDialog.dismiss();
//                        }
//                    });
//                    normalDialog.showDialog(getFragmentManager());
                    JlWpDetailActivity.getInstance(mContext, bean);
                }
            });
        }
    }
}
