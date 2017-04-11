package com.shawnway.nav.app.wtw.module.quotation.international.international_detail;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.module.quotation.international.InternationalListBean;
import com.shawnway.nav.app.wtw.module.quotation.international.InternationalPriceBean;
import com.shawnway.nav.app.wtw.module.quotation.international.billingRecord.BillingActivity;
import com.shawnway.nav.app.wtw.module.quotation.international.order.OrderDecreaseActivity;
import com.shawnway.nav.app.wtw.module.quotation.international.order.OrderIncreaseActivity;
import com.shawnway.nav.app.wtw.module.quotation.international.positions.PositionsActivity;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.ThreadManager;
import com.shawnway.nav.app.wtw.tool.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.zeromq.ZMQ;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/26.
 * 国际期货详情页
 */

public class InternationalDetailActivity extends BaseActivity {
    private final String TAG = InternationalDetailActivity.this.getClass().getSimpleName();


    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.fl_international_container)
    FrameLayout flInternationalContainer;
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.lastPrice)
    TextView lastPrice;
    @BindView(R.id.highPrice)
    TextView highPrice;
    @BindView(R.id.lowPrice)
    TextView lowPrice;
    @BindView(R.id.askQty)
    TextView askQty;
    @BindView(R.id.bidQty)
    TextView bidQty;
    @BindView(R.id.tv_positions)
    TextView tvPositions;
    @BindView(R.id.tv_billing)
    TextView tvBilling;
    @BindView(R.id.ll_order_increase)
    TextView llOrderIncrease;
    @BindView(R.id.ll_order_decrease)
    TextView llOrderDecrease;
    @BindView(R.id.userUsageMoney)
    TextView userUsageMoney;
    @BindView(R.id.tv_userMoney)
    TextView tv_userMoney;
    @BindView(R.id.tv_chang_price)
    TextView tvChangPrice;
    @BindView(R.id.change_price)
    TextView changePrice;
    @BindView(R.id.tv_change_rate)
    TextView tvChangeRate;
    @BindView(R.id.change_rate)
    TextView changeRate;
    @BindView(R.id.tv_askQty)
    TextView tvAskQty;
    @BindView(R.id.tv_bidQty)
    TextView tvBidQty;
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
    @BindView(R.id.openPrice)
    TextView openPrice;
    @BindView(R.id.divider3)
    View divider3;
    @BindView(R.id.tv_closePrice)
    TextView tvClosePrice;
    @BindView(R.id.closePrice)
    TextView closePrice;
    @BindView(R.id.tv_tradeQty)
    TextView tvTradeQty;
    @BindView(R.id.tradeQty)
    TextView tradeQty;
    @BindView(R.id.ll_lastPrice)
    LinearLayout llLastPrice;
    @BindView(R.id.ll_currentData)
    LinearLayout llCurrentData;
    @BindView(R.id.ll_tradeQty)
    LinearLayout llTradeQty;
    @BindView(R.id.ll_kline_data)
    LinearLayout ll_kline_data;
    @BindView(R.id.kline_openPrice)
    TextView klineOpenPrice;
    @BindView(R.id.kline_highPrice)
    TextView klineHighPrice;
    @BindView(R.id.kline_lowPrice)
    TextView klineLowPrice;
    @BindView(R.id.kline_closePrice)
    TextView klineClosePrice;
    @BindView(R.id.kline_rate)
    TextView klineRate;


    private String code;//产品代码
    private Gson gson = new Gson();
    private Handler handler = new Handler();
    private boolean connect = true;//MQ是否连接
    private Thread thread;
    private int accountId;//账户id
    private boolean isReal;//判断是否实盘，不是实盘获取
    private InternationalListBean.InstrumentRealmarketBean bean;//初始化进入的bean
    private DecimalFormat decimalFormat;
    private String desc;
    private long money;
    private boolean isLogin;//是否登录
    private ZMQ.Socket subscriber;
    private InternationalPriceBean newBean;//数据刷新的bean
    private String phone;
    private ThreadManager.ThreadPool threadPool;
    private MQRunnable mqRunnable;
    @Override
    protected int getLayout() {
        return R.layout.activity_international_detail;
    }


    @Override
    protected void initEventAndData() {
        code = getIntent().getStringExtra("code");
        isReal = getIntent().getBooleanExtra("isReal", false);
        bean = getIntent().getBundleExtra("bundle").getParcelable("data");
        desc = getIntent().getStringExtra("desc");
        decimalFormat = new DecimalFormat("0.00");
        toolbar.setPadding(0, 0, 0, 0);
        if (bean != null) {
            setFirstData(bean);
        }
        initTab();
        initTopbar();
        phone = SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT)
                .getString(SPUtils.PHONE, "");

        mqRunnable = new MQRunnable();
        FetchDataFromMQServer();
    }

    /**
     * 初次进入的数据填充
     *
     * @param bean
     */
    private void setFirstData(final InternationalListBean.InstrumentRealmarketBean bean) {
        final double chg = bean.getLastprice() - bean.getCloseprice();
        final double rate = chg / bean.getCloseprice();
        handler.post(new Runnable() {
            @Override
            public void run() {
                changePrice.setText(String.format("%s", decimalFormat.format(chg)));
                changeRate.setText(String.format("%s%%", decimalFormat.format(rate * 100)));
                openPrice.setText(String.format("%s", bean.getOpenprice()));
                closePrice.setText(String.format("%s", bean.getCloseprice()));
                lastPrice.setText(String.format("%s", bean.getLastprice()));
                highPrice.setText(String.format("%s", decimalFormat.format(bean.getHighprice())));
                lowPrice.setText(String.format("%s", decimalFormat.format(bean.getLowprice())));
                askQty.setText(String.format("%s", bean.getAskvolume1()));
                bidQty.setText(String.format("%s", bean.getBidvolume1()));

                tradeQty.setText(String.format("%s", bean.getTurnovervolume()));

                int colors = chg > 0 ? getResources().getColor(R.color.text_international_light_red) : getResources().getColor(R.color.text_international_light_green);

                tvChangeRate.setTextColor(colors);
                tvChangPrice.setTextColor(colors);
                tvAskQty.setTextColor(colors);
                tvBidQty.setTextColor(colors);
                tvHighPrice.setTextColor(colors);
                tvLowPrice.setTextColor(colors);
                tvOpenPrice.setTextColor(colors);
                tvClosePrice.setTextColor(colors);
                tvTradeQty.setTextColor(colors);

                divider1.setBackgroundColor(colors);
                divider2.setBackgroundColor(colors);
                divider3.setBackgroundColor(colors);


                int bgColor = chg > 0 ? getResources().getColor(R.color.appcolor) : getResources().getColor(R.color.lightgreen);

                toolbar.setBackgroundColor(bgColor);
                topBack.setBackgroundColor(bgColor);


                llLastPrice.setBackgroundColor(bgColor);
                llCurrentData.setBackgroundColor(bgColor);
                llTradeQty.setBackgroundColor(bgColor);

                setColor(mContext, bgColor);


            }
        });
    }


    /**
     * 订阅MQ数据
     */
    private void FetchDataFromMQServer() {
//        threadPool = ThreadManager.getThreadPool();
//        threadPool.execute(mqRunnable);
         thread =  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ZMQ.Context context = ZMQ.context(1);
                    subscriber = context.socket(ZMQ.SUB);
                    subscriber.connect("tcp://ytx.marketdata.fatwo.cn:5563");
                    subscriber.subscribe(("SP-marketdata:" + code).getBytes());

                    while (connect) {
                        String message = subscriber.recvStr();
                        if (message != null && !message.contains("{")) {
                            //数据
                            final InternationalPriceBean priceBean = gson.fromJson(subscriber.recvStr(), InternationalPriceBean.class);
                            if (priceBean.getSecuritySymbol().equals(code)) {
                                setData(priceBean);
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

    private class MQRunnable implements Runnable{

        @Override
        public void run() {

        }
    }

    /**
     * 从MQ获取数据后设置数据，和根据数据改变顶部颜色
     *
     * @param priceBean
     */
    private void setData(final InternationalPriceBean priceBean) {
        final double chg = priceBean.getLastPrice() - priceBean.getClosePrice();
        newBean = priceBean;
        final double rate = chg / priceBean.getClosePrice();
        handler.post(new Runnable() {
            @Override
            public void run() {
                changePrice.setText(String.format("%s", decimalFormat.format(chg)));
                changeRate.setText(String.format("%s%%", decimalFormat.format(rate * 100)));
                openPrice.setText(String.format("%s", bean.getOpenprice()));
                closePrice.setText(String.format("%s", bean.getCloseprice()));
                lastPrice.setText(String.format("%s", priceBean.getLastPrice()));
                highPrice.setText(String.format("%s", priceBean.getHighPrice()));
                lowPrice.setText(String.format("%s", priceBean.getLowPrice()));
                askQty.setText(String.format("%s 手", priceBean.getAskQty()));
                bidQty.setText(String.format("%s 手", priceBean.getBidQty()));
                tradeQty.setText(String.format("%s", bean.getTurnovervolume()));

                int colors = chg > 0 ? getResources().getColor(R.color.text_international_light_red) : getResources().getColor(R.color.text_international_light_green);

                tvChangeRate.setTextColor(colors);
                tvChangPrice.setTextColor(colors);
                tvAskQty.setTextColor(colors);
                tvBidQty.setTextColor(colors);
                tvHighPrice.setTextColor(colors);
                tvLowPrice.setTextColor(colors);
                tvOpenPrice.setTextColor(colors);
                tvClosePrice.setTextColor(colors);
                tvTradeQty.setTextColor(colors);


                divider1.setBackgroundColor(colors);
                divider2.setBackgroundColor(colors);
                divider3.setBackgroundColor(colors);


                int bgColor = chg > 0 ? getResources().getColor(R.color.appcolor) : getResources().getColor(R.color.lightgreen);

                toolbar.setBackgroundColor(bgColor);
                topBack.setBackgroundColor(bgColor);


                llLastPrice.setBackgroundColor(bgColor);
                llCurrentData.setBackgroundColor(bgColor);
                llTradeQty.setBackgroundColor(bgColor);

                setColor(mContext, bgColor);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!phone.equals("")) {
            FetchUserAccountInfo();
        }
    }

    /**
     * 获取账号信息，accountId为1是模拟盘，0是实盘
     */
    private void FetchUserAccountInfo() {
        if (isReal) {

        } else {
            RetrofitClient
                    .getInstance()
                    .api()
                    .getUserAccountList()
                    .flatMap(new Func1<Response<UserAccountList>, Observable<UserAccountInfo>>() {
                        @Override
                        public Observable<UserAccountInfo> call(Response<UserAccountList> response) {
                            if (response.code() == 200) {
                                for (int i = 0; i < response.body().getList().size(); i++) {
                                    if (response.body().getList().get(i).getIsRealTrading() == 1) {
                                        accountId = response.body().getList().get(i).getTradingAccountId();
                                        JSONObject jsonObject = new JSONObject();
                                        try {
                                            jsonObject.put("tradingAccountId", response.body()
                                                    .getList().get(i).getTradingAccountId());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        return RetrofitClient.getInstance()
                                                .api()
                                                .getUserAccountInfo(new JsonRequestBody().convertJsonContent(jsonObject.toString()));
                                    }
                                }

                            }
                            isLogin = false;

                            return Observable.error(new Exception("获取账号信息失败"));
                        }

                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<UserAccountInfo>() {
                        @Override
                        public void call(final UserAccountInfo userAccountInfo) {
                            isLogin = true;
                            money = userAccountInfo.getCustomerTradingAccount().getTradingAccountUsableAmount().longValue();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    userUsageMoney.setText(String.format("%s",
                                            userAccountInfo.getCustomerTradingAccount()
                                                    .getTradingAccountUsableAmount()));

                                }
                            });
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.e(throwable.getMessage());
                        }
                    });
        }
    }

    /**
     * 关闭线程，断开订阅
     */
    private void stopSubscribe() {
        if (thread != null) {
            connect = false;
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopSubscribe();

    }

    /**
     * 顶部栏
     */
    private void initTopbar() {
        toolbar.setVisibility(View.VISIBLE);
        topBack.setVisibility(View.VISIBLE);
        String title = isReal ? "" : "";
        String userMoney = isReal ? "实盘用户余额：" : "模拟账户余额：";
        tv_userMoney.setText(userMoney);
        topTextCenter.setText(String.format("%s %s", desc, title));
        topTextCenter.setVisibility(View.VISIBLE);
    }

    /**
     * 分时图切换
     */
    private void initTab() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.addTab(tabLayout.newTab().setText("分时"), true);
        tabLayout.addTab(tabLayout.newTab().setText("1分"));
        tabLayout.addTab(tabLayout.newTab().setText("3分"));
        tabLayout.addTab(tabLayout.newTab().setText("5分"));
        tabLayout.addTab(tabLayout.newTab().setText("30分"));

    }

    @OnClick({R.id.top_back,
            R.id.tv_billing,
            R.id.tv_positions,
            R.id.ll_order_decrease,
            R.id.ll_order_increase,
            R.id.refresh})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.top_back:
                onBackPressed();
                break;
            case R.id.tv_positions:
                intent.setClass(mContext, PositionsActivity.class);
                break;
            case R.id.tv_billing:
                intent.setClass(mContext, BillingActivity.class);
                break;
            case R.id.ll_order_increase:
                if (isLogin) {
                    intent.setClass(mContext, OrderIncreaseActivity.class);
                    intent.putExtra("money", money);
                    intent.putExtra("depositPrice", bean.getServiceDeposit());
                    intent.putExtra("chargePrice", bean.getServiceCharge());
                    intent.putExtra("code", code);
                    intent.putExtra("desc", desc);
                    if (newBean == null) {
                        intent.putExtra("orderPrice", String.format("%s", bean.getAskprice1()));
                    } else {
                        intent.putExtra("orderPrice", String.format("%s", newBean.getAskPrice()));
                    }
                    intent.putExtra("accountId", accountId);
                } else {
                    intent.setClass(mContext, LoginActivity.class);
                }

                break;
            case R.id.ll_order_decrease:
                if (isLogin) {
                    intent.setClass(mContext, OrderDecreaseActivity.class);
                    intent.putExtra("money", money);
                    intent.putExtra("depositPrice", bean.getServiceDeposit());
                    intent.putExtra("chargePrice", bean.getServiceCharge());
                    intent.putExtra("code", code);
                    intent.putExtra("desc", desc);
                    if (newBean == null) {
                        intent.putExtra("orderPrice", String.format("%s", bean.getBidprice1()));
                    } else {
                        intent.putExtra("orderPrice", String.format("%s", newBean.getBidPrice()));
                    }
                    intent.putExtra("accountId", accountId);
                } else {
                    intent.setClass(mContext, LoginActivity.class);
                }

                break;
            case R.id.refresh:
                if (!phone.equals("")) {
                    FetchUserAccountInfo();
                }
                break;
        }
        if (view.getId() != R.id.top_back && view.getId() != R.id.refresh) {
            if (isReal) {
                ToastUtil.showShort(mContext, "实盘交易还未开放");
            } else {
                startActivity(intent);
            }
        }
    }

    /**
     * 切换布局
     *
     * @param index
     */
    private void switchFragment(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (index) {
            case 0:
                transaction.replace(R.id.fl_international_container, InternationalTimeLineFragment.newInstance(1, code));
                break;
            case 1:
                transaction.replace(R.id.fl_international_container, InternationalKLineFragment.newInstance(1, code));
                break;
            case 2:
                transaction.replace(R.id.fl_international_container, InternationalKLineFragment.newInstance(3, code));
                break;
            case 3:
                transaction.replace(R.id.fl_international_container, InternationalKLineFragment.newInstance(5, code));
                break;
            case 4:
                transaction.replace(R.id.fl_international_container, InternationalKLineFragment.newInstance(30, code));
                break;
        }
        transaction.commit();
    }

}
