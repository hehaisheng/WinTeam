package com.shawnway.nav.app.wtw.module.user.message_center;

import android.content.Context;
import android.content.Intent;
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
import com.shawnway.nav.app.wtw.bean.NewsBean;
import com.shawnway.nav.app.wtw.module.information.NewsActivity;
import com.shawnway.nav.app.wtw.tool.TimeUtils;
import com.shawnway.nav.app.wtw.view.LoadingView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Cicinnus on 2016/11/22.
 */

public class SystemMessageActivity extends BaseActivity<SystemMessagePresenter> implements SystemMessageContract.ISystemMessageView, BaseQuickAdapter.RequestLoadMoreListener {


    private String title;
    private int page = 0;

    public static void start(Context context, String title) {
        Intent starter = new Intent(context, SystemMessageActivity.class);
        starter.putExtra("title", title);
        context.startActivity(starter);
    }

    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.rv_sysytem_message)
    RecyclerView rvSysytemMessage;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private SystemMessageAdapter systemMessageAdapter;
    private List<NewsBean> mNewsDatas = new ArrayList<>();
    private LoadingView loadingGif;

    @Override
    protected int getLayout() {
        return R.layout.activity_system_message;
    }


    @Override
    protected SystemMessagePresenter getPresenter() {
        return new SystemMessagePresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        loadingGif = new LoadingView(mContext);
        title = getIntent().getStringExtra("title");
        topTextCenter.setText(title);
        setVisiable(topBack, toolbar, topTextCenter);


        rvSysytemMessage.setLayoutManager(new LinearLayoutManager(mContext));
        systemMessageAdapter = new SystemMessageAdapter();
        rvSysytemMessage.setAdapter(systemMessageAdapter);
        View emptyView = getLayoutInflater().inflate(R.layout.layout_no_data, (ViewGroup) rvSysytemMessage.getParent(), false);
        ((TextView) emptyView.findViewById(R.id.no_data_text)).setText("没有系统消息~下拉刷新数据~");
        systemMessageAdapter.setEmptyView(emptyView);
        systemMessageAdapter.setOnLoadMoreListener(this);
        swipe.setColorSchemeColors(getResources().getColor(R.color.lightred));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                page = 0;
                systemMessageAdapter.setNewData(new ArrayList<NewsBean>());
                mPresenter.getSystemMessage(page);
            }
        });
        mPresenter.getSystemMessage(page);
    }


    @OnClick(R.id.top_back)
    void onClick(View v) {
        finish();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getSystemMessage(page);
    }

    @Override
    public void addSystemMessage(NewsBean[] newsBeen) {
        loadingGif.dismiss();
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
        if (newsBeen.length > 0) {
            page += 1;
            mNewsDatas.clear();
            Collections.addAll(mNewsDatas, newsBeen);
            systemMessageAdapter.addData(mNewsDatas);
        } else {
            systemMessageAdapter.loadComplete();
        }
    }

    @Override
    public void showLoading() {
        loadingGif.show();
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(String errorMsg) {
        loadingGif.dismiss();
    }

    private class SystemMessageAdapter extends BaseQuickAdapter<NewsBean> {

        public SystemMessageAdapter() {
            super(R.layout.item_systeminfo, null);
        }

        @Override
        protected void convert(final BaseViewHolder baseViewHolder, final NewsBean newsBeen) {
            baseViewHolder
                    .setText(R.id.item_systeminfo_title, newsBeen.newsTitle)
                    .setText(R.id.item_systeminfo_content, newsBeen.shortDesc)
                    .setText(R.id.item_systeminfo_time, TimeUtils.dateYMDHM(newsBeen.updateTime));

            baseViewHolder.convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsActivity.getInstance(mContext, newsBeen);
                }
            });
        }
    }
}
