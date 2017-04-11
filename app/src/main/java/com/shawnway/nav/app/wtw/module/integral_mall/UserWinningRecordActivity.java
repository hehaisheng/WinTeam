package com.shawnway.nav.app.wtw.module.integral_mall;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.bean.UserWinningResult;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/28.
 * Administrator github = "https://github.com/Cicinnus0407"
 * 用户积分详情页
 */

public class UserWinningRecordActivity extends BaseActivity<UserWinningPresenter> implements UserWinningContract.UserWinningView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.top_back)
    ImageButton top_back;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.rv_userWinningRecord)
    RecyclerView rvRecord;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.progress_layout)
    ProgressLayout progressLayout;


    private WinningAdapter adapter;

    private int page = 0;

    @Override
    protected int getLayout() {
        return R.layout.activity_user_winning_record;
    }

    @Override
    protected UserWinningPresenter getPresenter() {
        return new UserWinningPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        top_back.setVisibility(View.VISIBLE);
        topTextCenter.setVisibility(View.VISIBLE);
        topTextCenter.setText("我的中奖");
        toolbar.setVisibility(View.VISIBLE);
        rvRecord.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        adapter = new WinningAdapter();
        rvRecord.setAdapter(adapter);
        View emptyView = getLayoutInflater().inflate(R.layout.layout_no_data, (ViewGroup) rvRecord.getParent(), false);
        ((TextView) emptyView.findViewById(R.id.no_data_text)).setText("很可惜没有中奖记录~~");
        adapter.setEmptyView(emptyView);

        swipe.setColorSchemeColors(getResources().getColor(R.color.lightred));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getUserWinningResult(page);
            }
        });

        adapter.setOnLoadMoreListener(this);

        mPresenter.getUserWinningResult(page);
    }

    @OnClick(R.id.top_back)
    public void onClick() {
        finish();
    }

    @Override
    public void addUserWinningData(UserWinningResult result) {
        if (result.getList().size() > 0) {
            page += 1;
            adapter.addData(result.getList());
        } else {
            adapter.loadComplete();
        }
    }

    @Override
    public void showLoading() {
        swipe.setRefreshing(true);
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {
        swipe.setRefreshing(false);
    }

    @Override
    public void showError(String errorMsg) {
        if (errorMsg.equals("Invalid index 0, size is 0")) {
            adapter.loadComplete();
            View footer = View.inflate(mContext, R.layout.layout_footer_no_data, null);
            adapter.removeAllFooterView();
            adapter.addFooterView(footer);
        } else {
            View.OnClickListener click = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    page = 0;
                    mPresenter.getUserWinningResult(page);
                }
            };
            progressLayout.showError(click);
            adapter.loadComplete();
            swipe.setRefreshing(false);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getUserWinningResult(page);
    }


    private class WinningAdapter extends BaseQuickAdapter<UserWinningResult.ListBean> {

        private SimpleDateFormat format;

        public WinningAdapter() {
            super(R.layout.item_user_winning_record, null);
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, UserWinningResult.ListBean listBean) {
            baseViewHolder.setText(R.id.tv_winning_time, String.format("%s", format.format(listBean.getCreateTime())))
                    .setText(R.id.tv_prize_name, listBean.getPrizeName());
        }
    }
}
