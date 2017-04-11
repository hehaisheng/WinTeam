package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_register;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.shawnway.nav.app.wtw.BuildConfig;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.net.JlWpApi;
import com.shawnway.nav.app.wtw.tool.PasswordUtil;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.LoadingView;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Cicinnus on 2016/12/8.
 */

public class RegisterJlWPActivity extends BaseActivity<RegisterJlWPPresenter> implements RegisterJlWPContract.IRegisterWpView {


    private String ivCodeUrl;

    public static void start(Context context) {
        Intent starter = new Intent(context, RegisterJlWPActivity.class);
        context.startActivity(starter);
    }

    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.send_sms_code)
    Button sendCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_iv_code)
    EditText etIvCode;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;

    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.top_back)
    ImageButton topBack;


    private int SECONDS = 60 * 5 * 1000;
    private Handler handler = new Handler();
    private LoadingView loadingGif ;
    private String phone;
    private String baseUrl ;
    private int randomToken = new Random().nextInt();

    @Override
    protected int getLayout() {
        return R.layout.activity_register_jl_wp;
    }

    @Override
    protected RegisterJlWPPresenter getPresenter() {
        return new RegisterJlWPPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        loadingGif = new LoadingView(mContext);
        topTextCenter.setText("注册吉交所微盘");
        setVisiable(toolbar, topBack, topTextCenter);
        phone = SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT)
                .getString(SPUtils.PHONE, "");

        tvPhone.setText(String.format("将要注册的手机号码为：%s", phone));


        baseUrl = BuildConfig.DEBUG?"http://uat.thor.opensdk.4001889177.com/":JlWpApi.BASE_URL;

        ivCodeUrl = baseUrl + "/api/getVerificationCodeImg?appId=" + JlWpApi.APPID + "&token=" + randomToken;

        Glide.with(mContext)
                .load(ivCodeUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate()
                .into(ivCode);

        ivCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomToken += 1;

                ivCodeUrl = baseUrl + "/api/getVerificationCodeImg?appId=" + JlWpApi.APPID + "&token=" + randomToken;
                Glide.with(mContext)
                        .load(ivCodeUrl)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .dontAnimate()
                        .into(ivCode);
            }
        });

    }

    @OnClick({R.id.send_sms_code, R.id.top_back, R.id.top_text_right, R.id.btn_register_wp})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.send_sms_code:
                if (etIvCode.getText().length() == 0) {
                    ToastUtil.showShort(mContext, "请输入图形验证码");
                    return;
                }
                mPresenter.sendSmsCode(phone, randomToken, etIvCode.getText().toString());
                break;
            case R.id.btn_register_wp:

                if (etCode.getText().length() == 0) {
                    ToastUtil.showShort(mContext, "请输入短信验证码");
                    return;
                }
                String ase_pwd = SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT)
                        .getString(SPUtils.PWD, "");
                String pwd = "";
                try {
                    pwd = PasswordUtil.aesDecrypt(ase_pwd, PasswordUtil._key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mPresenter.registerWP(phone, pwd, etCode.getText().toString().trim(), phone);
                break;
        }
    }

    @Override
    public void sendSmsCodeSuccess() {
        ToastUtil.showShort(mContext, "验证码已发送");
        loadingGif.dismiss();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (SECONDS > 0) {
                    sendCode.setText(String.format("%s S", SECONDS));
                    sendCode.setTextColor(Color.WHITE);
                    sendCode.postDelayed(this, 1000);
                    SECONDS--;
                } else {
                    sendCode.setTextColor(Color.WHITE);
                    sendCode.setText("获取验证码");
                }
            }
        });
    }

    @Override
    public void registerWPSuccess() {
        ToastUtil.showShort(mContext, "注册并自动登录成功");
        finish();
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
        loadingGif.dismiss();
    }

    @Override
    public void showError(String errorMsg) {
        loadingGif.dismiss();
        ToastUtil.showShort(mContext, errorMsg);
    }

}
