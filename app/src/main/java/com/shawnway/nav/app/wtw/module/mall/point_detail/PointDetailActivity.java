package com.shawnway.nav.app.wtw.module.mall.point_detail;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.tool.SPUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/28.
 * Administrator github = "https://github.com/Cicinnus0407"
 */

public class PointDetailActivity extends BaseActivity<PointDetailPresenter> implements PointContract.PointDetailView, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.rv_point_detail)
    RecyclerView rvPointDetail;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.currentPoint)
    TextView currentPoint;
    private PointDetailAdapter pointDetailAdapter;

    private int page = 0;


    @Override
    protected int getLayout() {
        return R.layout.activity_point_detail;
    }

    @Override
    protected PointDetailPresenter getPresenter() {
        return new PointDetailPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {

        topBack.setVisibility(View.VISIBLE);
        topTextCenter.setText("积分明细");
        topTextCenter.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);

        pointDetailAdapter = new PointDetailAdapter();
        rvPointDetail.setLayoutManager(new LinearLayoutManager(mContext));
        rvPointDetail.setAdapter(pointDetailAdapter);

        pointDetailAdapter.setOnLoadMoreListener(this);
        swipe.setColorSchemeColors(getResources().getColor(R.color.lightred));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                mPresenter.getPointDetail(page);
            }
        });
    }

    @OnClick(R.id.top_back)
    public void onClick() {
        finish();
    }

    @Override
    public void addPointDetail(PointDetailResult result) {
        if(swipe.isRefreshing()){
            swipe.setRefreshing(false);
        }
        if (page == 0) {
            currentPoint.setText(String.format("剩余积分:%s", result.getList().get(0).getCurrentPointsBalance()));
            Spannable span = new SpannableString(currentPoint.getText());
            span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor_yellow)), 5, currentPoint.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            currentPoint.setText(span);
            SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT)
                    .putInt(SPUtils.USER_POINT, result.getList().get(0).getCurrentPointsBalance())
                    .apply();
        }
        if (result.getList().size() > 0) {
            page += 1;
            pointDetailAdapter.addData(result.getList());
        } else {
            pointDetailAdapter.loadComplete();
            if (pointDetailAdapter.getData().size() >= 15) {
                View footer = View.inflate(mContext, R.layout.layout_footer_no_data, null);
                pointDetailAdapter.removeAllFooterView();
                pointDetailAdapter.addFooterView(footer);
            }

        }

    }

    @Override
    public void showLoading() {
//        swipe.setRefreshing(true);

    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {
//        swipe.setRefreshing(false);
    }

    @Override
    public void showError(String errorMsg) {
        if (errorMsg.equals("Invalid index 0, size is 0")) {
            pointDetailAdapter.loadComplete();
            View footer = View.inflate(mContext, R.layout.layout_footer_no_data, null);
            pointDetailAdapter.removeAllFooterView();
            pointDetailAdapter.addFooterView(footer);
            View emptyView = getLayoutInflater().inflate(R.layout.layout_no_data, (ViewGroup) rvPointDetail.getParent(), false);
            ((TextView) emptyView.findViewById(R.id.no_data_text)).setText("没有积分明细~~");
            pointDetailAdapter.setEmptyView(emptyView);
        }  else {
            View errorView = getLayoutInflater().inflate(R.layout.layout_error, (ViewGroup) rvPointDetail.getParent(), false);
            (errorView.findViewById(R.id.ll_error)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.getPointDetail(page);
                }
            });
            pointDetailAdapter.setEmptyView(errorView);
            pointDetailAdapter.loadComplete();
            swipe.setRefreshing(false);
        }

    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getPointDetail(page);
    }

    private class PointDetailAdapter extends BaseQuickAdapter<PointDetailResult.ListBean> {

        private SimpleDateFormat format;

        public PointDetailAdapter() {
            super(R.layout.item_point_detail, null);
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, PointDetailResult.ListBean bean) {

            baseViewHolder
                    .setText(R.id.point_name, String.format("%s", bean.getTransactionDesc()))
                    .setText(R.id.point_time, String.format("%s", format.format(bean.getTransactionDate())))
                    .setText(R.id.point_amount, String.format("%s%s", bean.getTransactionAmount() > 0 ? "+" : "", bean.getTransactionAmount()))
                    .setTextColor(R.id.point_amount, getResources().getColor(bean.getTransactionAmount() > 0 ?
                            R.color.textColor_yellow : R.color.text_primary));

        }

    }


}
