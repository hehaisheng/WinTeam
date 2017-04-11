package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_chart.JlWpChartContract;
import com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_chart.JlWpChartPresenter;
import com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_positions.JlWpPositionActivity;
import com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_register.RegisterJlWPActivity;
import com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_reset_pwd.ResetJlWpPwdActivity;
import com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.tran.build_tran.JlBuildTranActivity;
import com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.tran.transfer.JlTransferActivity;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.KLine.WpKLineFragment;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.KLine.WpTimeLineFragment;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_chart.RegularActivity;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.NormalDialog;
import com.shawnway.nav.app.wtw.view.ProgressLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;


public class JlWpDetailActivity extends BaseActivity<JlWpChartPresenter> implements JlWpChartContract.IWpChartView, View.OnClickListener {


    @BindView(R.id.top_back)
    public ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;

    @BindView(R.id.lastPrice)
    TextView lastPrice;
    @BindView(R.id.wp_chgpoint)
    TextView tvChgPoint;
    @BindView(R.id.wp_chg_rate)
    TextView tvChg;
    @BindView(R.id.activity_chart_tv_highestprice)
    TextView highPrice;
    @BindView(R.id.activity_chart_tv_lowwestprice)
    TextView lowPrice;
    @BindView(R.id.activity_chart_tv_pricebeginning)
    TextView tvPriceAtBeginning;
    @BindView(R.id.activity_chart_tv_pricelastday)
    TextView tvPriceAtEndLastday;

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.tv_chang_price)
    TextView tvChangPrice;
    @BindView(R.id.tv_change_rate)
    TextView tvChangeRate;
    @BindView(R.id.ll_lastPrice)
    LinearLayout llLastPrice;
    @BindView(R.id.tv_highPrice)
    TextView tvHighPrice;
    @BindView(R.id.divider1)
    View divider1;
    @BindView(R.id.tv_lowPrice)
    TextView tvLowPrice;
    @BindView(R.id.divider2)
    View divider2;
    @BindView(R.id.tv_openPrice)
    TextView tvOpenPrice;
    @BindView(R.id.divider3)
    View divider3;
    @BindView(R.id.tv_closePrice)
    TextView tvClosePrice;
    @BindView(R.id.ll_currentData)
    LinearLayout llCurrentData;
    @BindView(R.id.ll_tradeQty)
    LinearLayout llTradeQty;
    @BindView(R.id.progress_layout)
    ProgressLayout progressLayout;
    @BindView(R.id.userUsageMoney)
    TextView userUsageMoney;

    private boolean isFirst = true;//第一次进入
    private boolean isLogin = false;//是否登录
    private boolean isRegister = false;//是否注册
    private LoadingView loadingView;


    private QuotationsWPBean firstBean;
    private DecimalFormat dlf = new DecimalFormat("0.00");
    private String token;
    private Subscription subscription;

    public static void getInstance(Context context, QuotationsWPBean bean) {
        Intent intent = new Intent(context, JlWpDetailActivity.class);
        intent.putExtra("firstBean", bean);
        context.startActivity(intent);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_jl_wp_detail;
    }

    @Override
    protected JlWpChartPresenter getPresenter() {
        return new JlWpChartPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        loadingView = new LoadingView(mContext);
        firstBean = (QuotationsWPBean) getIntent().getSerializableExtra("firstBean");
        initToolBar();
        setFirstData();
        initFragment();

        token = SPUtils.getInstance(mContext, SPUtils.SP_JL_WP)
                .getString(SPUtils.JL_WP_TOKEN, "");


        //判断是否重置过密码
        boolean resetPwd = SPUtils.getInstance(mContext, SPUtils.SP_UPDATE_INFO).getBoolean(SPUtils.RESET_JL_WP_PWD, false);


        //第一次进入判断是否登录
        if (!SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT)
                .getString(SPUtils.PHONE, "").equals("")) {
            isLogin = true;
            if (resetPwd) {
                showResetPwdDialog();
            }
        }
        //判断是否已登录微盘
        if (!token.equals("")) {
            isRegister = true;
        }
        mPresenter.getUseageMoney(token);
        EventBus.getDefault().register(mContext);
    }

    /**
     * 显示重置密码的Dialog
     */
    private void showResetPwdDialog() {
        final NormalDialog resetPwdDialog = NormalDialog.newInstance("重置密码", "需要重置吉交所密码", "确定");
        resetPwdDialog.setOnDialogClickListener(new NormalDialog.OnConfirmClickListener() {
            @Override
            public void onClick() {
                resetPwdDialog.dismiss();
                ResetJlWpPwdActivity.start(mContext);
                finish();
            }
        });
        resetPwdDialog.showDialog(getSupportFragmentManager());
    }

    @Override
    protected void onResume() {
        super.onResume();
        startSubscription();
        if (!token.equals("")) {
            mPresenter.getUseageMoney(token);
        }
    }

    private void startSubscription() {
        subscription = Observable
                .interval(0, 3, TimeUnit.SECONDS)
                .compose(SchedulersCompat.<Long>applyIoSchedulers())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mPresenter.getData();
                    }
                });
    }

    private void stopScription() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

    //登录后事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void login(LoginWinEvent loginWinEvent) {
        isLogin = true;
    }

    //登录微盘事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginJlWp(LoginJlWpEvent loginJlWpEvent) {
        isRegister = true;
        token = SPUtils.getInstance(mContext, SPUtils.SP_JL_WP)
                .getString(SPUtils.JL_WP_TOKEN, "");
        mPresenter.getUseageMoney(token);
    }

    /**
     * 切换tab
     */
    private void initFragment() {
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (tab.getPosition()) {
                    case 0:
                        transaction.replace(R.id.fl_wp_kline_container, WpTimeLineFragment.getInstance(firstBean));
                        break;
                    case 1:
                        transaction.replace(R.id.fl_wp_kline_container, WpKLineFragment.getInstance(firstBean, 2));
                        break;
                    case 2:
                        transaction.replace(R.id.fl_wp_kline_container, WpKLineFragment.getInstance(firstBean, 3));
                        break;
                    case 3:
                        transaction.replace(R.id.fl_wp_kline_container, WpKLineFragment.getInstance(firstBean, 4));
                        break;
                    case 4:
                        transaction.replace(R.id.fl_wp_kline_container, WpKLineFragment.getInstance(firstBean, 5));
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
        tabs.addTab(tabs.newTab().setText("分时"), true);
        tabs.addTab(tabs.newTab().setText("5分"));
        tabs.addTab(tabs.newTab().setText("15分"));
        tabs.addTab(tabs.newTab().setText("30分"));
        tabs.addTab(tabs.newTab().setText("1小时"));

    }


    /**
     * 设置第一次进入的数据
     */
    private void setFirstData() {
        lastPrice.setText(dlf.format(firstBean.getLatestPrice()));
        double chgPoint = firstBean.getLatestPrice() - firstBean.getPriceAtEndLastday();
        tvChgPoint.setText(dlf.format(chgPoint));
        double chg = chgPoint / firstBean.getPriceAtBeginning();
        tvChg.setText(String.format("%s%%", dlf.format(chg * 100)));


        highPrice.setText(dlf.format(firstBean.getHighestPrice()));
        lowPrice.setText(dlf.format(firstBean.getLowwestPrice()));
        tvPriceAtBeginning.setText(dlf.format(firstBean.getPriceAtBeginning()));
        tvPriceAtEndLastday.setText(dlf.format(firstBean.getPriceAtEndLastday()));

        int textColor = chg >= 0 ? getResources().getColor(R.color.text_international_light_red) : getResources().getColor(R.color.text_international_light_green);

        tvChangeRate.setTextColor(textColor);
        tvChangPrice.setTextColor(textColor);
        tvHighPrice.setTextColor(textColor);
        tvLowPrice.setTextColor(textColor);
        tvOpenPrice.setTextColor(textColor);
        tvClosePrice.setTextColor(textColor);


        divider1.setBackgroundColor(textColor);
        divider2.setBackgroundColor(textColor);
        divider3.setBackgroundColor(textColor);


        int bgColor = chg >= 0 ? getResources().getColor(R.color.appcolor) : getResources().getColor(R.color.lightgreen);

        toolbar.setBackgroundColor(bgColor);
        topBack.setBackgroundColor(bgColor);


        llLastPrice.setBackgroundColor(bgColor);
        llCurrentData.setBackgroundColor(bgColor);
        llTradeQty.setBackgroundColor(bgColor);

        setColor(mContext, bgColor);

    }


    @Override
    public void addData(QuotationsWPBean[] beans) {
        for (QuotationsWPBean bean : beans) {
            if (firstBean.productContract.equals(bean.productContract)) {
                refreshData(bean);
            }
        }
    }

    @Override
    public void addMoney(UserAccountBean.AccBanBean data) {
        userUsageMoney.setText(String.format("%s", data.getUseableBalance()));
    }

    @Override
    public void showLoading() {
        if (isFirst) {
            loadingView.show();
            isFirst = false;
        }
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showContent() {
        loadingView.dismiss();
    }

    @Override
    public void showError(String errorMsg) {
        loadingView.dismiss();
    }


    @Override
    protected void onPause() {
        super.onPause();
        stopScription();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(mContext);
    }


    /**
     * 刷新内容
     *
     * @param bean
     */
    private void refreshData(QuotationsWPBean bean) {
        lastPrice.setText(dlf.format(bean.getLatestPrice()));
        double chgPoint = bean.getLatestPrice() - bean.getPriceAtEndLastday();
        tvChgPoint.setText(dlf.format(chgPoint));
        double chg = chgPoint / bean.getPriceAtBeginning();
        tvChg.setText(String.format("%s%%", dlf.format(chg * 100)));


        highPrice.setText(dlf.format(bean.getHighestPrice()));

        lowPrice.setText(dlf.format(bean.getLowwestPrice()));

        tvPriceAtBeginning.setText(dlf.format(bean.getPriceAtBeginning()));

        tvPriceAtEndLastday.setText(dlf.format(bean.getPriceAtEndLastday()));


        int textColor = chg >= 0 ?
                getResources().getColor(R.color.text_international_light_red) :
                getResources().getColor(R.color.text_international_light_green);

        tvChangeRate.setTextColor(textColor);
        tvChangPrice.setTextColor(textColor);
        tvHighPrice.setTextColor(textColor);
        tvLowPrice.setTextColor(textColor);
        tvOpenPrice.setTextColor(textColor);
        tvClosePrice.setTextColor(textColor);


        divider1.setBackgroundColor(textColor);
        divider2.setBackgroundColor(textColor);
        divider3.setBackgroundColor(textColor);

        int bgColor = chg >= 0 ? getResources().getColor(R.color.appcolor) : getResources().getColor(R.color.lightgreen);

        toolbar.setBackgroundColor(bgColor);
        topBack.setBackgroundColor(bgColor);

        llLastPrice.setBackgroundColor(bgColor);
        llCurrentData.setBackgroundColor(bgColor);
        llTradeQty.setBackgroundColor(bgColor);

        setColor(mContext, bgColor);


    }


    private void initToolBar() {
        String productName = "";
        switch (firstBean.getProductContract()) {
            case "CU":
                productName = "电解铜";
                break;
            case "OIL":
                productName = "燃料油";
                break;
            case "XAG1":
                productName = "银制品";
                break;
        }
        topTextCenter.setText(productName);
        setVisiable(topBack, toolbar, topTextCenter);
    }


    @OnClick({
            R.id.top_back,
            R.id.top_text_right,
            R.id.activity_chart_rb_buyup,
            R.id.activity_chart_rb_buydown,
            R.id.wp_positions,
            R.id.wp_transfer,
            R.id.refresh})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.top_text_right://规则
                RegularActivity.getInstance(this);
                break;
            case R.id.activity_chart_rb_buyup://选择买涨之后的购买页面和操作
                if (isLogin) {
                    if (isRegister) {
                        JlBuildTranActivity.start(mContext, true, firstBean);
                    } else {
                        showRegisterDialog();
                    }
                } else {
                    LoginActivity.getInstance(mContext);
                }
                break;
            case R.id.activity_chart_rb_buydown://选择买跌之后的购买页面和操作
                if (isLogin) {
                    if (isRegister) {
                        JlBuildTranActivity.start(mContext, false, firstBean);
                    } else {
                        showRegisterDialog();
                    }
                } else {
                    LoginActivity.getInstance(mContext);

                }
                break;
            case R.id.wp_positions:
                //持仓
                if (isLogin) {
                    if (isRegister) {
                        JlWpPositionActivity.start(mContext, token);
                    } else {
                        showRegisterDialog();
                    }
                } else {
                    LoginActivity.getInstance(mContext);

                }
                break;
            case R.id.wp_transfer:
                //转账
                if (isLogin) {
                    if (isRegister) {
                        JlTransferActivity.start(mContext);
                    } else {
                        showRegisterDialog();
                    }
                } else {
                    LoginActivity.getInstance(mContext);

                }
                break;
            case R.id.refresh:
                //刷新余额
                String token = SPUtils.getInstance(mContext, SPUtils.SP_JL_WP)
                        .getString(SPUtils.JL_WP_TOKEN, "");
                mPresenter.getUseageMoney(token);
                break;
        }
    }

    /**
     * 提示注册微盘
     */
    private void showRegisterDialog() {
        final NormalDialog registerDialog = NormalDialog.newInstance("您还没有注册吉交所微盘", "点击确定进行注册", "确定");
        registerDialog.setOnDialogClickListener(new NormalDialog.OnConfirmClickListener() {
            @Override
            public void onClick() {
                registerDialog.dismiss();
                RegisterJlWPActivity.start(mContext);
                finish();
            }
        });
        registerDialog.showDialog(getSupportFragmentManager());
    }

    //登录事件类
    public static class LoginWinEvent {

    }

    //登录微盘事件类
    public static class LoginJlWpEvent {

    }

}
