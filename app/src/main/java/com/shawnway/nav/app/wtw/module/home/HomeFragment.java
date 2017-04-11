package com.shawnway.nav.app.wtw.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.bean.BannerPicBean;
import com.shawnway.nav.app.wtw.bean.LuckyDrawGoodsBean;
import com.shawnway.nav.app.wtw.bean.SignInResult;
import com.shawnway.nav.app.wtw.module.WebViewActivity;
import com.shawnway.nav.app.wtw.module.home.hot_trade.international_hot.InternationalHotFragment;
import com.shawnway.nav.app.wtw.module.home.hot_trade.international_hot.InternationalHotTradeActivity;
import com.shawnway.nav.app.wtw.module.home.hot_trade.jl_wp_hot.JlWPHotFragment;
import com.shawnway.nav.app.wtw.module.home.hot_trade.jl_wp_hot.JlWPHotTradeActivity;
import com.shawnway.nav.app.wtw.module.home.hot_trade.wp_hot.WPHotTradeActivity;
import com.shawnway.nav.app.wtw.module.integral_mall.TurnTableActivity;
import com.shawnway.nav.app.wtw.module.quotation.international.international_list.InternationalListUnrealActivity;
import com.shawnway.nav.app.wtw.service.AutoLoginService;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.Banner;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.IHomeView, ImportanceNewsFragment.notifyRefreshFinished {
    private String TAG = "HomeFragment";
    private BannerPicBean bean;
    private LoadingView loadingView;


    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.progress_layout)
    ProgressLayout progressLayout;
    @BindView(R.id.top_text_center)
    TextView center;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.iv_more)
    ImageView more;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.tab_hot_trade)
    TabLayout tabHotTrade;

//    private WPHotFragment wpHotFragment;
    private JlWPHotFragment jlWPHotFragment;
    private InternationalHotFragment internationalHotFragment;
    private int page;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected HomePresenter getPresenter() {
        return new HomePresenter(mContext, this);
    }

    @Override
    protected void initEventAndData(Bundle saveData) {
        loadingView = new LoadingView(mContext);
        importanceNewsFragment1 = new ImportanceNewsFragment();
        economyCalenFragment1 = new EconomyCalenFragment();
//        wpHotFragment = new WPHotFragment();
        jlWPHotFragment = new JlWPHotFragment();
        internationalHotFragment = new InternationalHotFragment();
        initToolBar();

        initView(saveData);
        mPresenter.getBanner();

    }


    private void initView(Bundle saveData) {
        //轮播图
        initBanner();
        //热门交易
        initHotTrade(saveData);
        //资讯
        initInformation(saveData);
        //下拉刷新
        swipeRefreshLayout.setColorSchemeColors(mContext.getResources().getColor(R.color.lightred));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getBanner();
                importanceNewsFragment1.initDatas();
                economyCalenFragment1.initDatas();
            }
        });
        //监听是否加载数据
        importanceNewsFragment1.setNotifyRefreshFinished(HomeFragment.this);

    }

    private void initToolBar() {
        toolbar.setVisibility(View.VISIBLE);
        center.setVisibility(View.VISIBLE);
        center.setText("赢天下");
    }

    /**
     * 轮播图
     */
    private void initBanner() {
        banner.setOnItemClickListener(new Banner.OnItemClickListener() {
            @Override
            public void click(View v, BannerPicBean.PicBean bean) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", bean.contentUrl);
                intent.putExtra("title", bean.getPicDesc());
                intent.putExtra("id", bean.getId());
                intent.putExtra("startTime", bean.getStartTime());
                intent.putExtra("endTime", bean.getEndTime());
                startActivity(intent);
            }
        });
    }


    @OnClick({
            R.id.mall_adr,
            R.id.unreal_trade,
            R.id.earn,
            R.id.signIn,
            R.id.iv_more
    })
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.mall_adr:
                //抽奖
                mPresenter.getPrizesList();
                break;
            case R.id.unreal_trade:
                //模拟交易
                startActivity(new Intent(mContext, InternationalListUnrealActivity.class));

                break;
            case R.id.earn:
                //积分赚取
                startActivity(new Intent(mContext, PointRuleActivity.class));
                break;
            case R.id.signIn:
                //签到
                mPresenter.signIn();
                break;
            case R.id.iv_more:
                //更多
                if (page == 0) {
                    startActivity(new Intent(mContext, JlWPHotTradeActivity.class));
                } else if (page == 1) {
                    startActivity(new Intent(mContext, InternationalHotTradeActivity.class));
                } else if (page == 2) {
                    startActivity(new Intent(mContext, WPHotTradeActivity.class));

                }
                break;
        }
    }


    /**
     * 热门交易
     */
    public void initHotTrade(Bundle saveData) {
//        tabHotTrade.addTab(tabHotTrade.newTab().setText("粤国际微盘"));
        tabHotTrade.addTab(tabHotTrade.newTab().setText("吉交所"));
        tabHotTrade.addTab(tabHotTrade.newTab().setText("国际期货"));
        tabHotTrade.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        switchHotFragment(0);
                        break;
                    case 1:
                        switchHotFragment(1);
                        break;
//                    case 2:
//                        switchHotFragment(2);
//                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (saveData != null) {
//            wpHotFragment = (WPHotFragment) getFragmentManager().findFragmentByTag("wpHotFragment");
            jlWPHotFragment = (JlWPHotFragment) getFragmentManager().findFragmentByTag("jlWPHotFragment");
            internationalHotFragment = (InternationalHotFragment) getFragmentManager().findFragmentByTag("internationalHotFragment");
            switchHotFragment(0);
        } else {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_hot_trade, jlWPHotFragment, "jlWPHotFragment")
//                    .add(R.id.fl_hot_trade, wpHotFragment, "wpHotFragment")
                    .add(R.id.fl_hot_trade, internationalHotFragment, "internationalHotFragment")
                    .commit();
            switchHotFragment(0);
        }

    }

    private void switchHotFragment(int i) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        switch (i) {
            case 0:
                transaction
                        .show(jlWPHotFragment)
//                        .hide(wpHotFragment);
                        .hide(internationalHotFragment);
                page = 0;
                break;
            case 1:
                transaction
                        .show(internationalHotFragment)
                        .hide(jlWPHotFragment);
//                        .hide(internationalHotFragment);
                page = 1;
                break;
//            case 2:
//
//                transaction
//                        .show(internationalHotFragment)
//                        .hide(wpHotFragment)
//                        .hide(jlWPHotFragment);
//                page = 2;
//                break;
        }
        transaction.commit();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (internationalHotFragment != null) {
                internationalHotFragment.setUserVisibleHint(true);
            }
        } else {
            if (internationalHotFragment != null) {
                internationalHotFragment.setUserVisibleHint(false);
            }
        }
    }

    private ImportanceNewsFragment importanceNewsFragment1;
    private EconomyCalenFragment economyCalenFragment1;

    private void initInformation(Bundle saveData) {
        if (saveData != null) {
            importanceNewsFragment1 = (ImportanceNewsFragment) getFragmentManager().findFragmentByTag("importanceNewsFragment1");
            economyCalenFragment1 = (EconomyCalenFragment) getFragmentManager().findFragmentByTag("economyCalenFragment1");

        } else {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_home_message, economyCalenFragment1, "economyCalenFragment1")
                    .add(R.id.fl_home_message, importanceNewsFragment1, "importanceNewsFragment1")
                    .commit();
        }
        setupBottomInformation();
    }

    /**
     * 最下方的两个列表
     */
    private void setupBottomInformation() {
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                switch (tab.getPosition()) {
                    case 0:
                        transaction
                                .show(importanceNewsFragment1)
                                .hide(economyCalenFragment1);
                        break;
                    case 1:
                        transaction
                                .show(economyCalenFragment1)
                                .hide(importanceNewsFragment1);
                        break;
                }
                transaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
        tabs.addTab(tabs.newTab().setText("重要消息"), true);
        tabs.addTab(tabs.newTab().setText("财经日历"));
    }

    @Override
    public void showUserSignIn(SignInResult signInResult) {
        ToastUtil.showShort(mContext, signInResult.getStatusCode());
    }

    @Override
    public void addBanner(BannerPicBean bannerPicBean) {
        bean = bannerPicBean;
        banner.setPic(bannerPicBean.getBannerPicList());
    }

    @Override
    public void addPrizesList(LuckyDrawGoodsBean bean) {
        ArrayList<LuckyDrawGoodsBean.LotteryRaffleBean> data =
                (ArrayList<LuckyDrawGoodsBean.LotteryRaffleBean>) bean.getLotteryRaffle();
        TurnTableActivity.getInstance(data, getContext());
    }

    @Override
    public void showSignLoading() {
        loadingView = new LoadingView(mContext);
        loadingView.show();
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {
        loadingView.dismiss();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (!progressLayout.isContent()) {
            progressLayout.showContent();
        }
    }

    @Override
    public void showError(String errorMsg) {
        swipeRefreshLayout.setRefreshing(false);
        progressLayout.showError(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startService(new Intent(mContext, AutoLoginService.class));
                mPresenter.getBanner();
                importanceNewsFragment1.initDatas();
                economyCalenFragment1.initDatas();
            }
        });
    }

    @Override
    public void finished() {
        if (banner != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onFailure() {
        swipeRefreshLayout.setRefreshing(false);
        ToastUtil.showShort(mContext, "获取数据失败，请重试");
    }
}
