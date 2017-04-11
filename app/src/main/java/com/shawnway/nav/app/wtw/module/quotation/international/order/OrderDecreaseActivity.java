package com.shawnway.nav.app.wtw.module.quotation.international.order;

import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.LoadingView;
import com.shawnway.nav.app.wtw.view.NormalDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/9/27.
 */

public class OrderDecreaseActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.top_text_right)
    TextView topTextRight;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.orderPrice)
    TextView price;
    @BindView(R.id.cb_market)
    CheckBox cb_market;
    @BindView(R.id.cb_limit)
    CheckBox cb_limit;
    @BindView(R.id.et_qty)
    TextView qty;
    @BindView(R.id.limit_price)
    EditText limit_price;
    @BindView(R.id.et_stopLoss)
    TextView et_StopLoss;
    @BindView(R.id.et_stopWin)
    TextView et_StopWin;
    @BindView(R.id.userUsageMoney)
    TextView userUsageMoney;
    @BindView(R.id.chargePrice)
    TextView chargePrice;
    @BindView(R.id.depositPrice)
    TextView depositPrice;
    @BindView(R.id.totalPrice)
    TextView totalPrice;
    @BindView(R.id.productName)
    TextView productName;
    @BindView(R.id.stopWinPrice)
    TextView stopWinPrice;
    @BindView(R.id.stopLossPrice)
    TextView stopLossPrice;
    @BindView(R.id.cb_orderRule)
    CheckBox cb_orderRule;
    @BindView(R.id.tv_rule)
    TextView tvRule;
    private String code;
    private String orderPrice;
    private int accountId;
    private double charge;
    private double deposit;
    private DecimalFormat decimalFormat = new DecimalFormat("0.0");


    @Override
    protected int getLayout() {
        return R.layout.activity_international_order_decrease;
    }

    @Override
    protected void initEventAndData() {
        initTopBar();
        initFirst();
        initAmount();
        initListener();
        String webLinkText = "<a href=''>合约条款</a>";
        tvRule.setText(Html.fromHtml(webLinkText));
        tvRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderRuleActivity.start(mContext);
            }
        });


    }

    private void initListener() {
        cb_market.setOnCheckedChangeListener(this);
        cb_limit.setOnCheckedChangeListener(this);
        limit_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cb_limit.setChecked(true);
                }
            }
        });
    }

    private void initAmount() {

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
                if (amount < 1) {
                    qty.setText(String.format("%s", 1));
                }else {
                    totalPrice.setText(String.format("合计：%s元", charge * Integer.parseInt(s.toString()) + (deposit * Integer.parseInt(s.toString()))));
                    Spannable span = new SpannableString(totalPrice.getText());
                    span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor_yellow)), 3, totalPrice.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    totalPrice.setText(span);
                    stopWinPrice.setText(String.format("%s元", decimalFormat.format(Integer.parseInt(et_StopWin.getText().toString()) / 100.0 * deposit * Integer.parseInt(s.toString()))));
                    stopLossPrice.setText(String.format("-%s元", decimalFormat.format(Integer.parseInt(et_StopLoss.getText().toString()) / 100.0 * deposit * Integer.parseInt(s.toString()))));

                }

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
                if (amount == 0) {
                    et_StopWin.setText(String.format("%s", 10));
                } else if (amount <= 200 && amount > 0) {
                    stopWinPrice.setText(String.format("%s元", decimalFormat.format(amount / 100.0 * deposit * Integer.parseInt(qty.getText().toString()))));
                } else {
                    et_StopWin.setText(String.format("%s", 200));
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
                if (amount == 0) {
                    et_StopLoss.setText(String.format("%s", 10));
                } else if (amount <= 70 && amount > 0) {
                    stopLossPrice.setText(String.format("-%s元", decimalFormat.format(amount / 100.0 * deposit * Integer.parseInt(qty.getText().toString()))));

                } else {
                    et_StopLoss.setText(String.format("%s", 70));
                }
            }
        });
    }

    private void initFirst() {
        charge = getIntent().getDoubleExtra("chargePrice", 0.0);
        deposit = getIntent().getDoubleExtra("depositPrice", 0.0);
        long money = getIntent().getLongExtra("money", 0);
        if (charge != 0.0 && deposit != 0.0) {
            chargePrice.setText(String.format("%s", charge));
            depositPrice.setText(String.format("%s", deposit));
            totalPrice.setText(String.format("合计：%s元", charge + (deposit * (Integer.parseInt(qty.getText().toString())))));
            Spannable span = new SpannableString(totalPrice.getText());
            span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor_yellow)), 3, totalPrice.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            totalPrice.setText(span);
            stopWinPrice.setText(String.format("%s元", decimalFormat.format(2 * deposit)));
            stopLossPrice.setText(String.format("-%s元", decimalFormat.format(0.7 * deposit)));
        }
        if (money != 0) {
            userUsageMoney.setText(String.format("%s", money));
        }
        //产品
        String desc = getIntent().getStringExtra("desc");
        productName.setText(String.format("%s", desc));
        code = getIntent().getStringExtra("code");
        //买入市场价
        orderPrice = getIntent().getStringExtra("orderPrice");
        accountId = getIntent().getIntExtra("accountId", 0);
        price.setText(String.format("（参考价）%s", orderPrice));
    }

    private void initTopBar() {
        toolbar.setVisibility(View.VISIBLE);
        topBack.setVisibility(View.VISIBLE);
        topTextCenter.setText("下单");
        topTextCenter.setVisibility(View.VISIBLE);
        topTextRight.setText("合约条款");
        topTextRight.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.top_back,
            R.id.top_text_right,
            R.id.btn_trade,
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
            case R.id.top_text_right:
                OrderRuleActivity.start(mContext);
                break;
            case R.id.btn_trade:
                if (cb_orderRule.isChecked()) {
                    if(cb_limit.isChecked()){
                        if(limit_price.getText().toString().equals("")){
                            ToastUtil.showShort(mContext,"请输入买入价");
                            return;
                        }
                    }
                    final NormalDialog confirmDialog = NormalDialog.newInstance("确认下单", "下单后无法撤销", "确定");
                    confirmDialog.setOnDialogClickListener(new NormalDialog.OnConfirmClickListener() {
                        @Override
                        public void onClick() {
                            performTrade();
                            confirmDialog.dismiss();
                        }
                    });
                    confirmDialog.showDialog(getSupportFragmentManager());
                } else {
                    ToastUtil.showShort(mContext, "您还未同意交易规则");
                }
                break;
            case R.id.qty_add:
                int addNum = Integer.parseInt(qty.getText().toString());
                addNum += 1;
                qty.setText(String.format("%s", addNum));
                break;
            case R.id.qty_reduce:
                int reduceNum = Integer.parseInt(qty.getText().toString());
                reduceNum -= 1;
                qty.setText(String.format("%s", reduceNum));
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

    private void performTrade() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("securityCode", code);
            if (limit_price.getText().length() > 0) {
                jsonObject.put("tradeType", 2);//自定价
                jsonObject.put("price", limit_price.getText().toString().trim());
            } else {
                jsonObject.put("tradeType", 1);//市场价
                jsonObject.put("price", orderPrice);//根据市场价
            }

            jsonObject.put("lossLimitPoint", Integer.parseInt(et_StopLoss.getText().toString()));
            jsonObject.put("profitLimitPoint", Integer.parseInt(et_StopWin.getText().toString()));

            if (Integer.parseInt(qty.getText().toString()) == 0) {
                ToastUtil.showShort(mContext, "交易手数不能为0");
                return;
            } else {
                jsonObject.put("qty", Integer.parseInt(qty.getText().toString()));
            }
            jsonObject.put("actionType", 1);//
            jsonObject.put("orderType", 1);//普通下单
            jsonObject.put("actionSide", 2);//sell
            jsonObject.put("customerTradingAccount", String.valueOf(accountId));//账户id

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Logger.d(jsonObject.toString());
        final LoadingView loadingGif = new LoadingView(mContext);
        loadingGif.show();
        RetrofitClient
                .getInstance()
                .api()
                .order(new JsonRequestBody().convertJsonContent(jsonObject.toString()))
                .compose(SchedulersCompat.<retrofit2.Response<TradeBean>>applyIoSchedulers())
                .subscribe(new BaseSubscriber<Response<TradeBean>>() {
                    @Override
                    public void onSuccess(Response<TradeBean> response) {
                        if (response.code() == 200) {
                            if (response.body().getOrderId() != null) {
                                ToastUtil.showShort(mContext, "下单成功");
                                loadingGif.dismiss();
                                finish();
                            }
                        } else if (response.code() == 401) {
                            loadingGif.dismiss();
                            LoginActivity.getInstance(mContext);
                        }
                    }

                });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_market:
                limit_price.setText("");
                if (isChecked) {
                    cb_limit.setChecked(false);
                }
                break;
            case R.id.cb_limit:
                if (isChecked) {
                    cb_market.setChecked(false);
                }
                break;
        }
    }
}
