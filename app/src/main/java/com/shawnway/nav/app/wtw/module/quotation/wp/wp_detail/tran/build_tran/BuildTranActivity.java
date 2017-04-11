package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.build_tran;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.BuildTranBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.NotLiquidateOrder;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.TranGoodsWrapper;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.module.user.ticket.wp_ticket.WpTicketBean;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Kevin on 2016/12/7
 */

public class BuildTranActivity extends BaseActivity<BuildTranPresenter> implements BuildTranContract.IBuildTranView {
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.top_text_right)
    TextView topTextRight;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.btn_build_tran_buyup)
    Button btnBuildTranBuyup;
    @BindView(R.id.btn_build_tran_buydown)
    Button btnBuildTranBuydown;
    @BindView(R.id.tv_build_tran_goodsname)
    TextView tvBuildTranGoodsname;
    @BindView(R.id.tv_build_tran_goodsprice)
    TextView tvBuildTranGoodsprice;
    @BindView(R.id.tv_build_tran_chgpoint)
    TextView tvBuildTranChgpoint;
    @BindView(R.id.tv_build_tran_chg)
    TextView tvBuildTranChg;
    @BindView(R.id.build_tran_goodswrapper)
    RelativeLayout buildTranGoodswrapper;
    @BindView(R.id.et_qty)
    TextView qty;
    @BindView(R.id.tv_build_tran_tradefee)
    TextView tvBuildTranTradefee;
    @BindView(R.id.cb_build_tran_useticket)
    CheckBox cbBuildTranUseticket;
    @BindView(R.id.tv_build_tran_account)
    TextView tvBuildTranAccount;
    @BindView(R.id.et_stopLoss)
    TextView et_StopLoss;
    @BindView(R.id.et_stopWin)
    TextView et_StopWin;
    @BindView(R.id.tv_build_tran_paymoney)
    TextView tvBuildTranPaymoney;
    @BindView(R.id.tv_build_tran_useable_balance)
    TextView tvBuildTranUseableBalance;
    @BindView(R.id.tv_build_tran_maxbuy)
    TextView tvBuildTranMaxBuy;
    @BindView(R.id.btn_build_tran_buy)
    Button btnBuildTranBuy;

    private static String IS_BUY_UP = "isBuyUp";
    private static String Quotations_WP_Bean = "QuotationsWPBean";
    private boolean isBuyUp;
    //产品bean
    private QuotationsWPBean quotationsWPBean;
    //格式化Decimal数据
    private DecimalFormat amDlf = new DecimalFormat("0");
    private DecimalFormat dlf = new DecimalFormat("0.00");
    //产品列表
    private ArrayList<TranGoodsWrapper.TranGoodsBean> tranGoodsBeanArrayList;
    private String mvSelectItemName;
    private ArrayList<String> tranNameList;
    //用户实时盈亏和当前仓位
    private ArrayList<NotLiquidateOrder.NotLiquidateBean.BuyamountBean> buyAmountBeanArrayList;

    private final int TYPE_UP = 1;
    private final int TYPE_DOWN = 2;

    private final String RESULT_OK = "200";
    private final String RESULT_ERROR = "500";
    //选择中的产品
    private TranGoodsWrapper.TranGoodsBean selectTranGoodBean;
    //用户赢家券
    private WpTicketBean userWpTicketBean;
    //用户资金信息
    private UserAccountBean userAccount;
    private int maxValueIncrease = 1;
    private LoadingView loadingView;
    private String access_token;


    @Override
    protected int getLayout() {
        return R.layout.activity_build_tran;
    }

    public static void start(Context context, boolean isBuyUp, QuotationsWPBean quotationsWPBean) {
        Intent starter = new Intent(context, BuildTranActivity.class);
        starter.putExtra(IS_BUY_UP, isBuyUp);
        starter.putExtra(Quotations_WP_Bean, quotationsWPBean);
        context.startActivity(starter);
    }

    @Override
    protected BuildTranPresenter getPresenter() {
        return new BuildTranPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        getIntentData();
        initToolBar();
        initData();
        initListener();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        access_token = SPUtils.getInstance(mContext, SPUtils.SP_WP)
                .getString(SPUtils.WP_TOKEN, "");

        //获取产品列表
        mPresenter.getTranGoods(quotationsWPBean);

        //获取赢加券
        mPresenter.getUserTicket(access_token);

        //获取用户资金信息
        mPresenter.getUserAccountBalance(access_token);

    }


    /**
     * 设置监听
     */
    private void initListener() {
        qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int amount = Integer.parseInt(s.toString());
                if (amount == 0 && maxValueIncrease != 0) {
                    qty.setText(String.format("%s", 1));
                } else if (amount > maxValueIncrease && maxValueIncrease != 0) {
                    qty.setText(String.format("%s", maxValueIncrease));
                }
                tvBuildTranMaxBuy.setText(String.format("(当前剩余数量为:%s)", maxValueIncrease - Integer.parseInt(qty.getText().toString())));
                calculationPrice(selectTranGoodBean);
            }
        });
        et_StopWin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int amount = Integer.parseInt(s.toString());
                if (amount < 0) {
                    et_StopWin.setText(String.format("%s", 0));
                } else if (amount > 50) {
                    et_StopWin.setText(String.format("%s", 50));
                }
            }
        });
        et_StopLoss.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int amount = Integer.parseInt(s.toString());
                if (amount < 0) {
                    et_StopLoss.setText(String.format("%s", 0));
                } else if (amount > 50) {
                    et_StopLoss.setText(String.format("%s", 50));
                }
            }
        });
        cbBuildTranUseticket.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                calculationPrice(selectTranGoodBean);
            }
        });
    }

    /**
     * 获取Intent的数据
     */
    private void getIntentData() {
        isBuyUp = getIntent().getBooleanExtra(IS_BUY_UP, false);
        if (isBuyUp) {
            btnBuildTranBuydown.setVisibility(View.GONE);
            btnBuildTranBuy.setBackgroundResource(R.color.appcolor);
            btnBuildTranBuy.setText(getString(R.string.buy_up_now));
        } else {
            btnBuildTranBuyup.setVisibility(View.GONE);
            btnBuildTranBuy.setBackgroundResource(R.color.lightgreen);
            btnBuildTranBuy.setText(getString(R.string.buy_down_now));
        }
        quotationsWPBean = (QuotationsWPBean) getIntent().getSerializableExtra(Quotations_WP_Bean);
    }


    private void initToolBar() {
        topTextCenter.setText("建仓");
        setVisiable(toolbar, topBack, topTextCenter);
    }

    @OnClick({
            R.id.top_back,
            R.id.btn_build_tran_buyup,
            R.id.btn_build_tran_buydown,
            R.id.btn_build_tran_buy,
            R.id.build_tran_goodswrapper,
            R.id.qty_add,
            R.id.qty_reduce,
            R.id.stopLoss_add,
            R.id.stopLoss_reduce,
            R.id.stopWin_add,
            R.id.stopWin_reduce})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.build_tran_goodswrapper:
                //点击弹出窗口选择产品
                chooseTranGoods();
                break;
            case R.id.btn_build_tran_buy:
                //建仓
                if (maxValueIncrease != 0) {
                    showCreateDialog();
                } else
                    Toast.makeText(mContext, String.format("您持有的%s产品的最大仓位已满,请先平仓或选择其他产品", selectTranGoodBean.productName), Toast.LENGTH_SHORT).show();
                break;
            case R.id.qty_add:
                if (maxValueIncrease != 0) {
                    int addNum = Integer.parseInt(qty.getText().toString());
                    addNum += 1;
                    qty.setText(String.format("%s", addNum));
                } else
                    Toast.makeText(mContext, String.format("您持有的%s产品的最大仓位已满,请先平仓或选择其他产品", selectTranGoodBean.productName), Toast.LENGTH_SHORT).show();
                break;
            case R.id.qty_reduce:
                if (maxValueIncrease != 0) {
                    int reduceNum = Integer.parseInt(qty.getText().toString());
                    reduceNum -= 1;
                    qty.setText(String.format("%s", reduceNum));
                }
                break;
            case R.id.stopLoss_add:
                int stopLossAddNum = Integer.parseInt(et_StopLoss.getText().toString());
                stopLossAddNum += 10;
                et_StopLoss.setText(String.format("%s", stopLossAddNum));
                break;
            case R.id.stopLoss_reduce:
                int stopLossReduceNum = Integer.parseInt(et_StopLoss.getText().toString());
                stopLossReduceNum -= 10;
                et_StopLoss.setText(String.format("%s", stopLossReduceNum));
                break;
            case R.id.stopWin_add:
                int stopWinAddNum = Integer.parseInt(et_StopWin.getText().toString());
                stopWinAddNum += 10;
                et_StopWin.setText(String.format("%s", stopWinAddNum));
                break;
            case R.id.stopWin_reduce:
                int stopWinReduceNum = Integer.parseInt(et_StopWin.getText().toString());
                stopWinReduceNum -= 10;
                et_StopWin.setText(String.format("%s", stopWinReduceNum));
                break;
        }
    }

    /**
     * 显示建仓提示框
     */
    private void showCreateDialog() {
        AlertDialog.Builder build = new AlertDialog.Builder(mContext);
        View buildview = View.inflate(mContext, R.layout.dialog_buildposition, null);
        TextView goodsName = (TextView) buildview.findViewById(R.id.dialog_buildposition_goodsname);
        TextView buildAmount = (TextView) buildview.findViewById(R.id.dialog_buildposition_amount);
        TextView payMoney = (TextView) buildview.findViewById(R.id.dialog_buildposition_paymoney);
        LinearLayout userTicketTip = (LinearLayout) buildview.findViewById(R.id.dialog_buildposition_redwrapper);

        Button buildCancel = (Button) buildview.findViewById(R.id.dialog_buildposition_btn_cancel);
        Button buildOk = (Button) buildview.findViewById(R.id.dialog_buildposition_btn_ok);
        build.setView(buildview);

        final AlertDialog dialog_build = build.show();

        userTicketTip.setVisibility(cbBuildTranUseticket.isChecked() ? View.VISIBLE : View.GONE);
        goodsName.setText(String.format("%s%s", selectTranGoodBean.getProductName(), selectTranGoodBean.getSpecifications()));
        buildAmount.setText(String.format("%s", Integer.parseInt(qty.getText().toString())));
        payMoney.setText(tvBuildTranPaymoney.getText());

        buildCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_build.dismiss();
            }
        });

        buildOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectTranGoodBean.isDuringTradingTime()) {
                    if (userAccount.data.getUseableBalance() < Double.parseDouble(tvBuildTranPaymoney.getText().toString())) {
                        ToastUtil.showShort(mContext, "账户可用余额不足");
                    } else {
                        buildTran();
                    }
                } else {
                    ToastUtil.showShort(mContext, "该商品处于非交易时间，请查明再试");
                }
                dialog_build.dismiss();
            }
        });

    }

    /**
     * 建仓
     */
    private void buildTran() {
        BuildTranBean.BuildBean buildTranBean = new BuildTranBean.BuildBean();
        buildTranBean.setProdectId(selectTranGoodBean.getId());
        buildTranBean.setTradeType(isBuyUp ? 1 : 2);
        buildTranBean.setAmount(Integer.parseInt(qty.getText().toString()));
        Integer targetProfit = Integer.parseInt(et_StopWin.getText().toString());
        Integer stopLoss = Integer.parseInt(et_StopLoss.getText().toString());
        buildTranBean.setTargetProfit(targetProfit.doubleValue());
        buildTranBean.setStopLoss(stopLoss.doubleValue());
        buildTranBean.setUseTicket(cbBuildTranUseticket.isChecked() ? 1 : 0);
        String access_token = SPUtils.getInstance(mContext, SPUtils.SP_WP)
                .getString(SPUtils.WP_TOKEN, "");

        mPresenter.createOrder(access_token, buildTranBean);
    }

    /**
     * 选择交易商品
     */
    private void chooseTranGoods() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.dialog_selectgoods, null);
        final WheelView wv = (WheelView) view.findViewById(R.id.dialog_selectgoods_wv);
        Button btnCancel = (Button) view.findViewById(R.id.dialog_selectgoods_btn_cancel);
        Button btnOk = (Button) view.findViewById(R.id.dialog_selectgoods_btn_ok);
        builder.setView(view);
        final AlertDialog dialog = builder.show();
        if (tranGoodsBeanArrayList == null || tranGoodsBeanArrayList.size() == 0) {
            ToastUtil.showShort(mContext, "没有可交易的商品");
        } else {
            setWheelView(wv);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mvSelectItemName == null) {
                    ToastUtil.showShort(mContext, "选择失败  服务器异常");
                    dialog.dismiss();
                }
                for (TranGoodsWrapper.TranGoodsBean tranGoodsBean : tranGoodsBeanArrayList) {
                    if (TextUtils.equals(mvSelectItemName, tranGoodsBean.getProductName() + tranGoodsBean.getSpecifications())) {//选中的商品
                        setTranGoodData(tranGoodsBean);
                        setNumberHandData(buyAmountBeanArrayList);
                        int currentMaxValue = Integer.valueOf(qty.getText().toString());
                        if (currentMaxValue >= maxValueIncrease) {
                            qty.setText(String.format("%s", maxValueIncrease));
                        }
                        dialog.dismiss();
                    }
                }
            }
        });
    }

    /**
     * 设置弹窗的滚动组件
     *
     * @param wv
     */
    private void setWheelView(final WheelView wv) {
        tranNameList = new ArrayList<>();
        for (TranGoodsWrapper.TranGoodsBean tranGoodsBean : tranGoodsBeanArrayList) {
            tranNameList.add(tranGoodsBean.getProductName() + tranGoodsBean.getSpecifications());
        }
        wv.setWheelData(tranNameList);
        wv.setWheelAdapter(new ArrayWheelAdapter(mContext)); // 文本数据源
        wv.setSkin(WheelView.Skin.Holo); // common皮肤-->灰色系列  None皮肤-->全白系列  Holo-->全白但是显示选中的分割线
        wv.setLoop(false);
        wv.setWheelSize(5);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.backgroundColor = getResources().getColor(R.color.white);
        style.holoBorderColor = getResources().getColor(R.color.line_divider);
        style.textSize = 15;
        style.textColor = getResources().getColor(R.color.user_textColor_gray);
        style.selectedTextSize = 18;
        style.selectedTextColor = getResources().getColor(R.color.user_textColor);
        style.textAlpha = 0.8f;
        wv.setStyle(style);
        wv.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                mvSelectItemName = (String) wv.getSelectionItem();

            }
        });
    }

    /**
     * 获取对应产品列表返回结果
     *
     * @param tranGoodsWrapper
     */
    @Override
    public void addTranGoods(TranGoodsWrapper tranGoodsWrapper) {
        setTranGoodsBeanArrayList(tranGoodsWrapper.getData());
        setTranGoodData(tranGoodsWrapper.getData().get(0));
        //获取用户的实时盈亏和当前仓位
        mPresenter.getNotLiquidateOrder(access_token);
    }

    /**
     * 设置选中的产品
     * 设置总价
     *
     * @param tranGoodData
     */
    private void setTranGoodData(TranGoodsWrapper.TranGoodsBean tranGoodData) {
        //设置选择中的产品
        setSelectTranGoodBean(tranGoodData);

        tvBuildTranGoodsname.setText(tranGoodData.getProductName() + tranGoodData.getSpecifications());
        //产品规格价格
        tvBuildTranGoodsprice.setText(amDlf.format(tranGoodData.getUnitPrice()) + "元/手");
        //产品盈亏
        float pl = tranGoodData.getFloatProfit() * 100;
        String header = pl > 0 ? "+" : "-";
        tvBuildTranChgpoint.setText(header + dlf.format(pl * tranGoodData.getUnitPrice() / 100));
        tvBuildTranChg.setText(header + dlf.format(pl) + "%");

        calculationPrice(tranGoodData);
    }

    /**
     * 计算总价格
     */
    private void calculationPrice(TranGoodsWrapper.TranGoodsBean tranGoodsBean) {
        if (cbBuildTranUseticket.isChecked() && userWpTicketBean.getData().size() > 0) {
            //使用了赢家券
            if (tranGoodsBean.getUnitPrice() == 10 && Integer.parseInt(qty.getText().toString()) == 1) {
                //10元一手的免手续费
                double tranFree = 0;
                if ((tranGoodsBean.getUnitPrice() - userWpTicketBean.getData().get(0).getSum()) <= 0) {
                    tranFree = 0;
                }
                tvBuildTranPaymoney.setText(String.format("%s", dlf.format(tranFree)));
            } else {
                //其他情况
                double totalPrice = ((tranGoodsBean.getUnitPrice() + tranGoodsBean.getTradeFee()) * Integer.parseInt(qty.getText().toString())) - userWpTicketBean.getData().get(0).getSum();
                if (totalPrice <= 0) {
                    totalPrice = 0;
                }
                tvBuildTranPaymoney.setText(String.format("%s", dlf.format(totalPrice)));

            }
        } else {
            //没使用赢家券
            tvBuildTranPaymoney.setText(dlf.format((tranGoodsBean.getUnitPrice() + tranGoodsBean.getTradeFee()) * Integer.parseInt(qty.getText().toString())));
        }

        //手续费
        tvBuildTranTradefee.setText("手续费：" + dlf.format(tranGoodsBean.getTradeFee() * Integer.parseInt(qty.getText().toString())) + "元");
    }

    /**
     * 获取用户实时盈亏和当前仓位结果
     *
     * @param notLiquidateBean
     */
    @Override
    public void addNotLiquidateOrder(NotLiquidateOrder.NotLiquidateBean notLiquidateBean) {
        setBuyAmountBeanArrayList(notLiquidateBean.getBuyamount());
        setNumberHandData(notLiquidateBean.getBuyamount());
    }

    /**
     * 根据仓位设置手数
     */
    private void setNumberHandData(ArrayList<NotLiquidateOrder.NotLiquidateBean.BuyamountBean> buyAmountBeanArrayList) {
        //当前Id为选择的ID时才设置
        for (NotLiquidateOrder.NotLiquidateBean.BuyamountBean buyamountBean : buyAmountBeanArrayList) {
            if (isBuyUp) {
                if (selectTranGoodBean.getId() == buyamountBean.getId() && buyamountBean.getType() == TYPE_UP) {
                    //买涨的手数
                    selectTranGoodBean.setMaxBuyAmout(buyamountBean.getMaxBuyAmout() - buyamountBean.getBuyAmount());
                    maxValueIncrease = selectTranGoodBean.getMaxBuyAmout();
                }
            } else {
                if (selectTranGoodBean.getId() == buyamountBean.getId() && buyamountBean.getType() == TYPE_DOWN) {
                    //买跌的手数
                    selectTranGoodBean.setMaxBuyAmout(buyamountBean.getMaxBuyAmout() - buyamountBean.getBuyAmount());
                    maxValueIncrease = selectTranGoodBean.getMaxBuyAmout();
                }
            }
        }
        tvBuildTranMaxBuy.setText(String.format("(当前剩余数量为:%s)", maxValueIncrease - Integer.parseInt(qty.getText().toString())));
        if (maxValueIncrease == 0) {
            qty.setText(String.format("%s", maxValueIncrease));
        }
    }

    /**
     * 获取用户赢家券
     *
     * @param wpTicketBean
     */
    @Override
    public void addUserTicket(WpTicketBean wpTicketBean) {
        setUserWpTicketBean(wpTicketBean);
        tvBuildTranAccount.setText(wpTicketBean.getData().size() + "");
        if (wpTicketBean.getData().size() == 0) {
            cbBuildTranUseticket.setEnabled(false);
        }
    }


    /**
     * 获取用户资金信息
     *
     * @param userAccountBean
     */
    @Override
    public void addUserAccountBalance(UserAccountBean userAccountBean) {
        setUserAccount(userAccountBean);
    }


    /**
     * 获取建仓结果
     *
     * @param buildTranBean
     */
    @Override
    public void onCreateResult(BuildTranBean buildTranBean) {
        if (TextUtils.equals(buildTranBean.state, RESULT_OK)) {
            Toast.makeText(mContext, "建仓成功", Toast.LENGTH_SHORT).show();
            finish();
        } else if (TextUtils.equals(buildTranBean.state, RESULT_ERROR)) {
            Toast.makeText(mContext, "服务器异常，建仓失败，请稍后再试", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showLoading() {
        loadingView = new LoadingView(mContext);
        loadingView.show();
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
        Toast.makeText(mContext, "服务器异常,请稍后再试", Toast.LENGTH_SHORT).show();
    }


    public void setTranGoodsBeanArrayList(ArrayList<TranGoodsWrapper.TranGoodsBean> tranGoodsBeanArrayList) {
        this.tranGoodsBeanArrayList = tranGoodsBeanArrayList;
    }

    public void setBuyAmountBeanArrayList(ArrayList<NotLiquidateOrder.NotLiquidateBean.BuyamountBean> buyAmountBeanArrayList) {
        this.buyAmountBeanArrayList = buyAmountBeanArrayList;
    }

    public void setSelectTranGoodBean(TranGoodsWrapper.TranGoodsBean selectTranGoodBean) {
        this.selectTranGoodBean = selectTranGoodBean;
    }

    public void setUserWpTicketBean(WpTicketBean userWpTicketBean) {
        this.userWpTicketBean = userWpTicketBean;
    }

    public void setUserAccount(UserAccountBean userAccount) {
        if (userAccount.data != null) {
            tvBuildTranUseableBalance.setText(String.format("(您的可用余额为:%s元)", userAccount.data.getUseableBalance()));
            setVisiable(tvBuildTranUseableBalance);
        } else tvBuildTranUseableBalance.setVisibility(View.GONE);
        this.userAccount = userAccount;
    }
}
