package com.shawnway.nav.app.wtw.module.mall;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.bean.LuckyDrawGoodsBean;
import com.shawnway.nav.app.wtw.bean.UserPointResult;
import com.shawnway.nav.app.wtw.module.integral_mall.TurnTableActivity;
import com.shawnway.nav.app.wtw.module.mall.adapter.NewestProductAdapter;
import com.shawnway.nav.app.wtw.module.mall.adapter.RecommendProductAdapter;
import com.shawnway.nav.app.wtw.module.mall.address.AddressListActivity;
import com.shawnway.nav.app.wtw.module.mall.bean.NewestProductBean;
import com.shawnway.nav.app.wtw.module.mall.bean.RecommendProductBean;
import com.shawnway.nav.app.wtw.module.mall.exchange_list.ExchangeListActivity;
import com.shawnway.nav.app.wtw.module.mall.filter.MallFilterActivity;
import com.shawnway.nav.app.wtw.module.mall.shopping_car.ShoppingCarActivity;
import com.shawnway.nav.app.wtw.module.mall.user_orders.UserOrdersActivity;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 积分商城
 * Created by Administrator on 2016/7/19 0019.
 */
public class MallFragment extends BaseFragment<MallPresenter> implements MallContract.MallView {

    public static MallFragment newInstance() {

        Bundle args = new Bundle();

        MallFragment fragment = new MallFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.progress_layout)
    ProgressLayout progressLayout;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.top_text_right)
    TextView topTextRight;
    @BindView(R.id.mal_top)
    ImageView malTop;
    @BindView(R.id.rv_newest)
    RecyclerView rv_newest;
    @BindView(R.id.rv_recommend)
    RecyclerView rv_recommend;
    @BindView(R.id.point)
    TextView point;
    @BindView(R.id.signIn)
    TextView signIn;
    @BindView(R.id.mall_order)
    TextView pointRule;
    @BindView(R.id.mall_adr)
    TextView lottery;
    @BindView(R.id.classification)
    TextView classification;
    @BindView(R.id.refreshPoint)
    ImageView refreshPoint;

    private boolean firstEnter = true;
    private LoadingView loadingView;

    private Handler handler = new Handler();
    private NewestProductAdapter newestProductAdapter;
    private RecommendProductAdapter recommendProductAdapter;
    private View.OnClickListener tryClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.getUserPoint();
            mPresenter.getProducts();
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall;
    }

    @Override
    protected MallPresenter getPresenter() {
        return new MallPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        initToolbar();
        loadingView = new LoadingView(mContext);
        initNewestRecyclerView();
        initRecommendRecyclerView();
        swipe.setColorSchemeColors(mContext.getResources().getColor(R.color.lightred));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                mPresenter.getUserPoint();
                mPresenter.getProducts();
            }
        });
    }


    /**
     * 推荐商品
     */
    private void initRecommendRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false);
        rv_recommend.setLayoutManager(gridLayoutManager);
        rv_recommend.setNestedScrollingEnabled(false);
        recommendProductAdapter = new RecommendProductAdapter();
        rv_recommend.setAdapter(recommendProductAdapter);
        View emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_function_no_open, (ViewGroup) rv_recommend.getParent(), false);
        recommendProductAdapter.setEmptyView(emptyView);

    }

    /**
     * 最新上架
     */
    private void initNewestRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false);
        rv_newest.setLayoutManager(gridLayoutManager);
        rv_newest.setNestedScrollingEnabled(false);
        newestProductAdapter = new NewestProductAdapter();
        rv_newest.setAdapter(newestProductAdapter);
        View emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_function_no_open, (ViewGroup) rv_newest.getParent(), false);
        newestProductAdapter.setEmptyView(emptyView);

    }


    private void initToolbar() {
        topTextCenter.setText("积分商城");
        topTextCenter.setVisibility(View.VISIBLE);
        topTextRight.setVisibility(View.VISIBLE);
        topTextRight.setText("");
        Drawable drawable = getResources().getDrawable(R.drawable.tro);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        topTextRight.setCompoundDrawables(drawable, null, null, null);
        toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (firstEnter) {
                firstEnter = false;
                mPresenter.getUserPoint();
                mPresenter.getProducts();
            }
            mPresenter.getUserPoint();
        }
    }

    @OnClick({
            R.id.signIn,
            R.id.mall_adr,
            R.id.mall_order,
            R.id.classification,
            R.id.top_text_right,
            R.id.tv_more_newest,
            R.id.tv_more_recommend,
            R.id.exchange_list,
            R.id.refreshPoint})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signIn:
                mPresenter.signIn();
                break;
            case R.id.mall_adr:
                AddressListActivity.start(mContext);
                break;
            case R.id.mall_order:
                UserOrdersActivity.start(mContext);
                break;
            case R.id.classification:
                startActivity(new Intent(mContext, MallFilterActivity.class));
                break;
            case R.id.top_text_right:
                ShoppingCarActivity.start(mContext);
                break;
            case R.id.tv_more_newest:
                startActivity(new Intent(mContext, MallFilterActivity.class));
                break;
            case R.id.tv_more_recommend:
                startActivity(new Intent(mContext, MallFilterActivity.class));
                break;
            case R.id.exchange_list:
                ExchangeListActivity.start(mContext);
                break;
            case R.id.refreshPoint:
                mPresenter.getUserPoint();
                break;
        }
    }


    @Override
    public void addUserPoint(UserPointResult userPointResult) {
        point.setText(String.format("积分:%s", userPointResult.getPoint()));
        Spannable span = new SpannableString(point.getText());
        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor_yellow)), 3, point.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        point.setText(span);

    }

    @Override
    public void addPrizesList(LuckyDrawGoodsBean bean) {
        ArrayList<LuckyDrawGoodsBean.LotteryRaffleBean> data =
                (ArrayList<LuckyDrawGoodsBean.LotteryRaffleBean>) bean.getLotteryRaffle();
        TurnTableActivity.getInstance(data, getContext());
    }

    @Override
    public void addNewestProduct(final List<NewestProductBean.AllProductEntityBean> bean) {
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
        newestProductAdapter.setNewData(bean);

    }

    @Override
    public void addRecommendProduct(final List<RecommendProductBean.AllProductEntityBean> bean) {
        if (swipe.isRefreshing()) {
            swipe.setRefreshing(false);
        }
        recommendProductAdapter.setNewData(bean);

    }

    @Override
    public void showLoading() {
        if (!progressLayout.isContent()) {
            progressLayout.showLoading();
        }
       loadingView.show();
    }

    @Override
    public void showNoData() {
    }

    @Override
    public void showContent() {
        if (!progressLayout.isContent()) {
            progressLayout.showContent();
        }
        loadingView.dismiss();
    }

    @Override
    public void showError(String errorMsg) {
        progressLayout.showError(tryClick);
        swipe.setRefreshing(false);
        ToastUtil.showShort(mContext, errorMsg);
        loadingView.dismiss();

    }


}
