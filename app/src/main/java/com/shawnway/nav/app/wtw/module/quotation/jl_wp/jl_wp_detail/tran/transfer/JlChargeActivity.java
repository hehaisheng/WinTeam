package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.tran.transfer;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.net.JlWPRetrofitClient;
import com.shawnway.nav.app.wtw.tool.GlideManager;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.LoadingView;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;


/**
 * Created by Cicinnus on 2017/1/11.
 */

public class JlChargeActivity extends BaseActivity {


    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.tv_save)
    Button saveButton;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.iv_charge_qrCode)
    ImageView iv_charge_qrCode;



    public static void start(Context context, int money, String bankCard) {
        Intent starter = new Intent(context, JlChargeActivity.class);
        starter.putExtra("money", money);
        starter.putExtra("bankCard", bankCard);
        context.startActivity(starter);
    }

    public static void startWeChat(Context context, int money) {
        Intent starter = new Intent(context, JlChargeActivity.class);
        starter.putExtra("money", money);
        context.startActivity(starter);
    }

    public static void startAliPay(Context context, int money, boolean isAlipay) {
        Intent starter = new Intent(context, JlChargeActivity.class);
        starter.putExtra("money", money);
        starter.putExtra("alipay", isAlipay);
        context.startActivity(starter);

    }

    @Override
    protected int getLayout() {

        return  R.layout.activity_jl_wp_charge;

    }


    @Override
    protected void initEventAndData() {
        topTextCenter.setText("账户充值");
        setVisiable(toolbar, topTextCenter, topBack);
        int money = getIntent().getIntExtra("money", 0);
        boolean isAlipay = getIntent().getBooleanExtra("alipay", false);
        RelativeLayout relativeLayout=(RelativeLayout) findViewById(R.id.jl_wp_layout);
        if(isAlipay)
        {

            relativeLayout.setBackgroundResource(R.drawable.bg_alipay_charge);
        }
        else
        {
            relativeLayout.setBackgroundResource(R.drawable.wechat_pay);
        }
//        String bankCard = getIntent().getStringExtra("bankCard");
//        initWebView();
        String access_token = SPUtils.getInstance(mContext, SPUtils.SP_JL_WP)
                .getString(SPUtils.JL_WP_TOKEN, "");
//        String url = BuildConfig.DEBUG ? "http://uat.thor.opensdk.4001889177.com/api/recharge/Unionpay/create/"+money+"/android/"+bankCard+"?access_token="
//                : JlWpApi.BASE_URL + "/api/recharge/Unionpay/create/" + money + "/android/" + bankCard + "?access_token=";

       final LoadingView loadingView = new LoadingView(mContext);
        loadingView.show();

        if (isAlipay) {

            JlWPRetrofitClient
                    .getInstance()
                    .api()
                    .AlipayCharge(money, "android", access_token)
                    .compose(SchedulersCompat.<QrCodeChargeBean>applyIoSchedulers())
                    .subscribe(new Subscriber<QrCodeChargeBean>() {
                        @Override
                        public void onCompleted() {
                            loadingView.dismiss();


                        }

                        @Override
                        public void onError(Throwable e) {
                            loadingView.dismiss();


                        }

                        @Override
                        public void onNext(QrCodeChargeBean qrCodeChargeBean) {
                            GlideManager.loadImage(mContext, qrCodeChargeBean.getData().getPayUrl(), iv_charge_qrCode);


                        }
                    });
        } else {
           JlWPRetrofitClient
        .getInstance()
        .api()
        .WeChatCharge(money, "android", access_token)
        .compose(SchedulersCompat.<QrCodeChargeBean>applyIoSchedulers())
        .subscribe(new Subscriber<QrCodeChargeBean>() {
@Override
public void onCompleted() {
        loadingView.dismiss();
        }

@Override
public void onError(Throwable e) {
        loadingView.dismiss();
        }

@Override
public void onNext(QrCodeChargeBean qrCodeChargeBean) {
        GlideManager.loadImage(mContext, qrCodeChargeBean.getData().getPayUrl(), iv_charge_qrCode);

        }
        });

        }

//        wb.loadUrl(url + access_token);
        }




@OnClick({R.id.top_back, R.id.tv_save})
public void onClick(View view) {
        switch (view.getId()) {
        case R.id.top_back:
        finish();
        break;
        case R.id.tv_save:
        ToastUtil.showShort(mContext, "保存成功，请前往微信进行扫一扫充值");
        break;
        }
        }

        }
