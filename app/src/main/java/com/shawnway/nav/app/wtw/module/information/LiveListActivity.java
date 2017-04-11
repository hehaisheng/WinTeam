package com.shawnway.nav.app.wtw.module.information;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.bean.LiveListBean;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.GlideManager;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.TimeUtils;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by 裘 on 2016/9/21 0021.
 */

public class LiveListActivity extends BaseActivity {


    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.rv_live_list)
    RecyclerView rvLiveList;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private LiveListAdapter liveListAdapter;

    public static void getInstance(Context context) {
        Intent intent = new Intent(context, LiveListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_livelist;
    }

    @Override
    protected void initEventAndData() {
        initToolBar();
        rvLiveList.setLayoutManager(new LinearLayoutManager(mContext));
        liveListAdapter = new LiveListAdapter();
        View emptyView  = getLayoutInflater().inflate(R.layout.layout_no_data, (ViewGroup) rvLiveList.getParent(),false);
        ((TextView)emptyView.findViewById(R.id.no_data_text)).setText("没有直播间数据~下拉刷新数据~");
        liveListAdapter.setEmptyView(emptyView);
        rvLiveList.setAdapter(liveListAdapter);
        swipe.setColorSchemeColors(getResources().getColor(R.color.lightred));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                FetchLiveList();
            }
        });
        FetchLiveList();
    }

    private void initToolBar() {
        topTextCenter.setText("直播间");
        setVisiable(toolbar,topBack,topTextCenter);
    }

    private void FetchLiveList(){
        RetrofitClient
                .getInstance()
                .api()
                .getLiveList()
                .compose(SchedulersCompat.<LiveListBean>applyIoSchedulers())
                .subscribe(new Action1<LiveListBean>() {
                    @Override
                    public void call(LiveListBean liveListBean) {
                        if (swipe.isRefreshing()) {
                            swipe.setRefreshing(false);
                        }
                        liveListAdapter.setNewData(liveListBean.getAllLiveShows());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                    }
                });
    }

    @OnClick(R.id.top_back)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
        }
    }

    private class LiveListAdapter extends BaseQuickAdapter<LiveListBean.AllLiveShowsBean> {
        public LiveListAdapter() {
            super(R.layout.item_latest, null);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, final LiveListBean.AllLiveShowsBean bean) {
            baseViewHolder.setText(R.id.item_latest_title,bean.getLiveName())
                    .setText(R.id.item_latest_time, TimeUtils.dateYMDHM(bean.getUpdateTime()));

            ImageView iv = baseViewHolder.getView(R.id.item_latest_img);
            GlideManager.loadImage(mContext,bean.getPicUrl(),iv);
            baseViewHolder.convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,LiveActivity.class);
                    intent.putExtra("live",bean.getLiveUrl());
                    mContext.startActivity(intent);
                }
            });
        }
    }

}
