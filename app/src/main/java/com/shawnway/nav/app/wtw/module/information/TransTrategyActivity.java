package com.shawnway.nav.app.wtw.module.information;

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
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.TimeUtils;
import com.shawnway.nav.app.wtw.view.LoadMoreFailView;
import com.shawnway.nav.app.wtw.view.LoadingView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * 交易攻略
 */
public class TransTrategyActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LatestActActivity";
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.rv_trade)
    RecyclerView rvTrade;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private TradeAdapter tradeAdapter;
    private LoadingView loadingGif;
    private List<NewsBean> newsBeanList = new ArrayList<>();
    private int page;
    private boolean isFirst = true;
    private View loadMoreFailedView;

    public static void getInstance(Context context) {
        Intent intent = new Intent(context, TransTrategyActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_transtrategy;
    }

    @Override
    protected void initEventAndData() {
        initToolBar();
        loadingGif = new LoadingView(mContext);
        rvTrade.setLayoutManager(new LinearLayoutManager(mContext));
        tradeAdapter = new TradeAdapter();
        View emptyView = getLayoutInflater().inflate(R.layout.layout_no_data, (ViewGroup) rvTrade.getParent(), false);
        ((TextView) emptyView.findViewById(R.id.no_data_text)).setText("没有交易攻略~下拉刷新");
        tradeAdapter.setEmptyView(emptyView);
        tradeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                FetchTrade();
            }
        });
        loadMoreFailedView = new LoadMoreFailView().getView(mContext, (ViewGroup) rvTrade.getParent(), new LoadMoreFailView.onLoadMoreFailedClickListener() {
            @Override
            public void onClick() {
                FetchTrade();
            }
        });
        tradeAdapter.setLoadMoreFailedView(loadMoreFailedView);

        rvTrade.setAdapter(tradeAdapter);
        swipe.setColorSchemeColors(getResources().getColor(R.color.lightred));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                page = 0;
                tradeAdapter.setNewData(new ArrayList<NewsBean>());
                FetchTrade();
            }
        });

        FetchTrade();
    }

    private void FetchTrade() {
        if(isFirst) {
            loadingGif.show();
            isFirst = false;
        }
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
                .get30Trade(JsonRequestBody.getInstance().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<NewsBean[]>applyIoSchedulers())
                .subscribe(new Action1<NewsBean[]>() {
                    @Override
                    public void call(NewsBean[] newsBeen) {
                        loadingGif.dismiss();
                        if (swipe.isRefreshing()) {
                            swipe.setRefreshing(false);
                        }
                        if (newsBeen.length > 0) {
                            page += 1;
                            newsBeanList.clear();
                            Collections.addAll(newsBeanList, newsBeen);
                            tradeAdapter.addData(newsBeanList);
                        } else {
                            tradeAdapter.loadComplete();
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (swipe.isRefreshing()) {
                            swipe.setRefreshing(false);
                        }
                        loadingGif.dismiss();
                        tradeAdapter.showLoadMoreFailedView();

                    }
                });

    }

    private void initToolBar() {
        topTextCenter.setText("交易攻略");
        setVisiable(toolbar, topBack, topTextCenter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
        }
    }

    private class TradeAdapter extends BaseQuickAdapter<NewsBean> {

        public TradeAdapter() {
            super(R.layout.item_trans_trategy, null);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, final NewsBean newsBean) {
            baseViewHolder
                    .setText(R.id.item_latest_title, newsBean.newsTitle)
                    .setText(R.id.item_latest_time, String.format("%s", TimeUtils.dateYMDHM(newsBean.updateTime)));

            baseViewHolder.convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsActivity.getInstance(mContext, newsBean);
                }
            });
        }
    }

}
