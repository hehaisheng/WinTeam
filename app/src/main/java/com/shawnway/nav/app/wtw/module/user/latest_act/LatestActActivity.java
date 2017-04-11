package com.shawnway.nav.app.wtw.module.user.latest_act;

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
import com.shawnway.nav.app.wtw.bean.NewsBean;
import com.shawnway.nav.app.wtw.module.information.NewsActivity;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.GlideManager;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.TimeUtils;
import com.shawnway.nav.app.wtw.view.LoadingView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by 裘 on 2016/9/1 0001.
 */
public class LatestActActivity extends BaseActivity {

    private static final String TAG = "LatestActActivity";
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.rv_latest_act)
    RecyclerView rvLatestAct;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private LatestActivityAdapter latestActivityAdapter;
    private List<NewsBean> newsList = new ArrayList<>();
    private LoadingView loadingView ;
    private int page;

    public static void getInstance(Context context) {
        Intent intent = new Intent(context, LatestActActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_latestact;
    }

    @Override
    protected void initEventAndData() {
        loadingView = new LoadingView(mContext);
        topTextCenter.setText("最新活动");
        setVisiable(topBack, toolbar, topTextCenter);
        rvLatestAct.setLayoutManager(new LinearLayoutManager(mContext));
        latestActivityAdapter = new LatestActivityAdapter();
        View emptyView = getLayoutInflater().inflate(R.layout.layout_no_data, (ViewGroup) rvLatestAct.getParent(), false);
        ((TextView) emptyView.findViewById(R.id.no_data_text)).setText("没有最新活动~下拉刷新数据~");
        latestActivityAdapter.setEmptyView(emptyView);
        latestActivityAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                FetchLatest();
            }
        });
        rvLatestAct.setAdapter(latestActivityAdapter);
        swipe.setColorSchemeColors(getResources().getColor(R.color.lightred));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                page = 0;
                FetchLatest();
            }
        });
        FetchLatest();
    }

    private void FetchLatest() {
        loadingView.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("page", page);
            jsonObject.put("size", 10);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RetrofitClient
                .getInstance()
                .api()
                .get30Activities(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<NewsBean[]>applyIoSchedulers())
                .subscribe(new Action1<NewsBean[]>() {
                    @Override
                    public void call(NewsBean[] newsBeen) {
                        if (swipe.isRefreshing()) {
                            swipe.setRefreshing(false);
                        }
                        loadingView.dismiss();
                        if (newsBeen.length > 0) {
                            page += 1;
                            newsList.clear();
                            Collections.addAll(newsList, newsBeen);
                            latestActivityAdapter.addData(newsList);
                            loadingView.dismiss();
                        } else {
                            latestActivityAdapter.loadComplete();
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        if (swipe.isRefreshing()) {
                            swipe.setRefreshing(false);
                        }
                        loadingView.dismiss();
                    }
                });
    }


    @OnClick({R.id.top_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
        }
    }


    private class LatestActivityAdapter extends BaseQuickAdapter<NewsBean> {

        public LatestActivityAdapter() {
            super(R.layout.item_latest, null);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, final NewsBean newsBean) {
            baseViewHolder.setText(R.id.item_latest_title, newsBean.newsTitle)
                    .setText(R.id.item_latest_time, TimeUtils.dateYMDHM(newsBean.updateTime));

            ImageView iv = baseViewHolder.getView(R.id.item_latest_img);
            GlideManager.loadImage(mContext, newsBean.newsPic, iv);

            baseViewHolder.convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsActivity.getInstance(mContext, newsBean);
                }
            });
        }
    }

}
