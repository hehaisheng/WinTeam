package com.shawnway.nav.app.wtw.module.quotation.international.international_real_list;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.module.quotation.international.InternationalListBean;
import com.shawnway.nav.app.wtw.module.quotation.international.InternationalPriceBean;
import com.shawnway.nav.app.wtw.module.quotation.international.international_list.InternationalListAdapter;
import com.shawnway.nav.app.wtw.module.quotation.international.international_list.InternationalListUnrealActivity;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.NormalDialog;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/9/26.
 */

public class InternationalFragment extends BaseFragment<InternationalRealListPresenter> implements InternationalRealListContract.IInternationRealListView {


    private static final String TAG = "InternationalFragment";
    @BindView(R.id.progress_layout)
    ProgressLayout progressLayout;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.rv_international)
    RecyclerView rvInternational;
    private InternationalListAdapter adapter;

    private Gson gson;

    private List<InternationalListBean.InstrumentRealmarketBean> foreignSecurityBeanList = new ArrayList<>();
    private Handler handler;
    private View header;
    private TextView head;
    private boolean connect = true;
    private ZMQ.Context context;
    private ZMQ.Socket subscriber;
    private Thread thread;
    private NormalDialog normalDialog;

    public static InternationalFragment newInstance() {

        Bundle args = new Bundle();

        InternationalFragment fragment = new InternationalFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_international;
    }


    @Override
    protected InternationalRealListPresenter getPresenter() {
        return new InternationalRealListPresenter(mContext,this);
    }

    @Override
    protected void initEventAndData() {
        adapter = new InternationalListAdapter(true);
        header = View.inflate(mContext, R.layout.layout_international_header, null);
        head = (TextView) header.findViewById(R.id.header);
        adapter.addFooterView(header);
        normalDialog = NormalDialog.newInstance("实盘交易功能尚未开放","是否进入模拟操盘","确定");
        normalDialog.setOnDialogClickListener(new NormalDialog.OnConfirmClickListener() {
            @Override
            public void onClick() {
                InternationalListUnrealActivity.start(mContext);
                normalDialog.dismiss();
            }
        });
        rvInternational.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvInternational.setAdapter(adapter);
        adapter.setOnItemClickListener(new InternationalListAdapter.OnItemClickListener() {
            @Override
            public void onClick(InternationalListBean.InstrumentRealmarketBean bean) {
                normalDialog.showDialog(getFragmentManager());
            }
        });
        gson = new Gson();
        handler = new Handler();

        swipe.setColorSchemeColors(getResources().getColor(R.color.lightred));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getList();
            }
        });
        mPresenter.getList();
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
                        if (message != null && message.contains("{") && gson != null
                                && foreignSecurityBeanList != null
                                && foreignSecurityBeanList.size() > 0) {
                            InternationalPriceBean priceBean = gson.fromJson(message, InternationalPriceBean.class);
                            for (int i = 0; i < foreignSecurityBeanList.size(); i++) {
                                InternationalListBean.InstrumentRealmarketBean bean = foreignSecurityBeanList.get(i);
                                if (priceBean.getSecuritySymbol().equals(bean.getInstrumentid())) {
                                    bean.setLastprice(priceBean.getLastPrice());
                                    bean.setCloseprice(priceBean.getClosePrice());
                                    foreignSecurityBeanList.set(i, bean);
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

    private void stopSubscribe() {
        if (thread != null) {
            connect = false;
        }
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (thread == null) {
                FetchDataFromMQServer();
            } else {
                connect = true;
                thread.interrupt();
                FetchDataFromMQServer();
            }
        } else {
            stopSubscribe();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (thread != null&&subscriber!=null) {
           stopSubscribe();
        }
    }

    @Override
    public void addList(List<InternationalListBean.InstrumentRealmarketBean> instrumentRealmarket) {
        foreignSecurityBeanList.clear();
        foreignSecurityBeanList.addAll(instrumentRealmarket);
        adapter.setNewData(foreignSecurityBeanList);
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
        ToastUtil.showShort(mContext,errorMsg);
        progressLayout.showError(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getList();
            }
        });
    }
}
