package com.shawnway.nav.app.wtw.module.quotation.international.international_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.module.quotation.international.InternationalListBean;
import com.shawnway.nav.app.wtw.module.quotation.international.InternationalPriceBean;
import com.shawnway.nav.app.wtw.module.quotation.international.international_detail.InternationalDetailActivity;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Cicinnus on 2016/11/4.
 * 模拟交易列表
 */

public class InternationalListUnrealActivity extends BaseActivity<InternationalPresenter> implements InternationListContract.IInternationalListView {

    public static void start(Context context) {
        Intent starter = new Intent(context, InternationalListUnrealActivity.class);
        context.startActivity(starter);
    }

    @BindView(R.id.progress_layout)
    ProgressLayout progressLayout;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.rv_international_unreal)
    RecyclerView rv_unreal;
    @BindView(R.id.top_back)
    ImageButton topback;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;

    private boolean connect = true;
    private ZMQ.Context context;
    private ZMQ.Socket subscriber;
    private Thread thread;
    private List<InternationalListBean.InstrumentRealmarketBean> realmarketBeanList = new ArrayList<>();

    private InternationalListAdapter adapter;
    private Gson gson;
    private Handler handler;
    private LoadingView loadingView ;

    @Override
    protected int getLayout() {
        return R.layout.activity_international_unreal;
    }


    @Override
    protected InternationalPresenter getPresenter() {
        return new InternationalPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        loadingView = new LoadingView(mContext,1000);
        initTopBar();
        gson = new Gson();
        handler = new Handler();
        adapter = new InternationalListAdapter(false);
        rv_unreal.setLayoutManager(new LinearLayoutManager(mContext));
        rv_unreal.setAdapter(adapter);

        adapter.setOnItemClickListener(new InternationalListAdapter.OnItemClickListener() {
            @Override
            public void onClick(InternationalListBean.InstrumentRealmarketBean bean) {
                Intent intent = new Intent(mContext, InternationalDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", bean);
                intent.putExtra("bundle", bundle);
                intent.putExtra("isReal", false);
                intent.putExtra("code", bean.getInstrumentid());
                intent.putExtra("desc", bean.getSecurityDesc());
                startActivity(intent);
            }
        });

        swipe.setColorSchemeColors(mContext.getResources().getColor(R.color.lightred));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                mPresenter.getInternationalList();
            }
        });


        mPresenter.getInternationalList();
    }

    private void initTopBar() {
        topback.setVisibility(View.VISIBLE);
        topTextCenter.setText("模拟操盘");
        topTextCenter.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.top_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;

        }
    }


    private void FetchDataFromMQServer() {

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    context = ZMQ.context(1);
                    subscriber = context.socket(ZMQ.SUB);
                    subscriber.connect("tcp://ytx.marketdata.fatwo.cn:5563");
                    subscriber.subscribe("SP-marketdata".getBytes());
                    while (connect) {
                        String message = subscriber.recvStr();
                        if (message != null && !message.contains("{") && gson != null
                                && realmarketBeanList != null
                                && realmarketBeanList.size() > 0) {
                            InternationalPriceBean priceBean = gson.fromJson(subscriber.recvStr(), InternationalPriceBean.class);
                            for (int i = 0; i < realmarketBeanList.size(); i++) {
                                InternationalListBean.InstrumentRealmarketBean bean = realmarketBeanList.get(i);
                                if (priceBean.getSecuritySymbol().equals(bean.getInstrumentid())) {
                                    bean.setLastprice(priceBean.getLastPrice());
                                    bean.setCloseprice(priceBean.getClosePrice());
                                    realmarketBeanList.set(i, bean);
                                    final int finalI = i;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyItemChanged(finalI);
                                        }
                                    });
                                }
                            }
                        }
                    }
                    subscriber.disconnect("tcp://ytx.marketdata.fatwo.cn:5563");
                    subscriber.close();
                    context.term();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        thread.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (thread != null) {
            connect = false;
        }
    }

    @Override
    public void addInternationalList(List<InternationalListBean.InstrumentRealmarketBean> instrumentRealmarket) {
        swipe.setRefreshing(false);
        realmarketBeanList.clear();
        realmarketBeanList.addAll(instrumentRealmarket);
        if (realmarketBeanList.size() == 0) {
            View view = getLayoutInflater().inflate(R.layout.layout_no_data, (ViewGroup) rv_unreal.getParent(), false);
            ((TextView) view.findViewById(R.id.no_data_text)).setText("暂时没有数据");
            adapter.setEmptyView(view);
        }
        adapter.setNewData(realmarketBeanList);
        if (thread != null) {
            thread.interrupt();
            FetchDataFromMQServer();
        }


        FetchDataFromMQServer();

    }

    @Override
    public void showLoading() {
        loadingView.show();
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {
        loadingView.dismiss();
        if (!progressLayout.isContent()) {
            progressLayout.showContent();
        }
    }

    @Override
    public void showError(String errorMsg) {
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
        loadingView.dismiss();
        progressLayout.showError(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getInternationalList();
            }
        });
    }
}
