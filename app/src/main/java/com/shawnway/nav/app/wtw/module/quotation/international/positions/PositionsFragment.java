package com.shawnway.nav.app.wtw.module.quotation.international.positions;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.module.quotation.international.InternationalListBean;
import com.shawnway.nav.app.wtw.module.quotation.international.order.TradeBean;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.NormalDialog;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/13.
 * Administrator github = "https://github.com/Cicinnus0407"
 */

public class PositionsFragment extends BaseFragment<PositionsPresenter> implements PositionsContract.IPositionsView {


    @BindView(R.id.progress_layout)
    ProgressLayout progressLayout;
    @BindView(R.id.rv_positions)
    RecyclerView rvPositions;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;


    public static PositionsFragment newInstance() {

        Bundle args = new Bundle();

        PositionsFragment fragment = new PositionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private PositionsAdapter positionsAdapter;
    private List<PositionsBean.WareHouseInfosBean> beanList = new ArrayList<>();
    private NormalDialog dialog;
    private Handler handler = new Handler();
    private GetLastPriceRunnable getLastPriceRunnable = new GetLastPriceRunnable();
    private int removePosition;
    private LoadingView loadingGif;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_positions_history;
    }

    @Override
    protected PositionsPresenter getPresenter() {
        return new PositionsPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData(final Bundle savedBundles) {
        loadingGif = new LoadingView(mContext);
        positionsAdapter = new PositionsAdapter();
        rvPositions.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvPositions.setAdapter(positionsAdapter);
        View emptyView = getLayoutInflater(savedBundles).inflate(R.layout.layout_no_data, (ViewGroup) rvPositions.getParent(), false);
        ((TextView) emptyView.findViewById(R.id.no_data_text)).setText("没有持仓记录~~");
        positionsAdapter.setEmptyView(emptyView);
        swipe.setColorSchemeColors(getResources().getColor(R.color.lightred));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                beanList.clear();
                mPresenter.getMarketPrice();
                mPresenter.getPositions();
            }
        });
        positionsAdapter.setOnSellClickListener(new PositionsAdapter.OnSellClickListener() {
            @Override
            public void onClick(final int position, final PositionsBean.WareHouseInfosBean bean) {
                dialog = NormalDialog.newInstance("平仓操作", "确定后无法撤销，请确认", "确定");
                dialog.setOnDialogClickListener(new NormalDialog.OnConfirmClickListener() {
                    @Override
                    public void onClick() {
                        removePosition = position;
                        mPresenter.order(bean);
                        dialog.dismiss();
                    }
                });
                dialog.showDialog(getFragmentManager());
            }
        });
        mPresenter.getPointValue();
        mPresenter.getPositions();
    }

    @Override
    public void onResume() {
        super.onResume();

        handler.post(getLastPriceRunnable);
    }


    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(getLastPriceRunnable);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(getLastPriceRunnable);
    }

    @Override
    public void addPositions(PositionsBean positionsBean) {
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
        loadingGif.dismiss();
        for (int i = 0; i < positionsBean.getWareHouseInfos().size(); i++) {
            if (positionsBean.getWareHouseInfos().get(i).getOrderType() == 1) {
                beanList.add(positionsBean.getWareHouseInfos().get(i));
            }
        }
        positionsAdapter.setNewData(beanList);
    }

    @Override
    public void addMarketPrice(InternationalListBean bean) {

        for (int i = 0; i < bean.getInstrumentRealmarket().size(); i++) {
            if (beanList.size() > 0) {
                for (int j = 0; j < beanList.size(); j++) {
                    if (bean.getInstrumentRealmarket().get(i).getInstrumentid().equals(beanList.get(j).getProdCode())) {
                        PositionsBean.WareHouseInfosBean infosBean = beanList.get(j);
                        infosBean.setMarketPrice(bean.getInstrumentRealmarket().get(i).getLastprice());
                        beanList.set(j, infosBean);
                        positionsAdapter.notifyItemChanged(j);
                    }
                }
            } else {
                handler.removeCallbacks(getLastPriceRunnable);
            }
        }
    }

    @Override
    public void orderSuccess(TradeBean tradeBean) {
        loadingGif.dismiss();
        ToastUtil.showShort(mContext, "交易成功");
        positionsAdapter.remove(removePosition);
    }

    @Override
    public void addPointValue(List<PointValueResultBean.ListBean> list) {
        positionsAdapter.setPointValueList(list);
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
        if (!progressLayout.isContent()) {
            progressLayout.showContent();
        }
    }

    @Override
    public void showError(String errorMsg) {
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
        progressLayout.setContentVisibility(false);
        loadingGif.dismiss();
        progressLayout.showError(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getPositions();
            }
        });
    }

    private class GetLastPriceRunnable implements Runnable {

        @Override
        public void run() {
            mPresenter.getMarketPrice();
            handler.postDelayed(this, 5000);

        }
    }


}
