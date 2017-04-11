package com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_reset_pwd;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.LoadingView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Cicinnus on 2017/1/10.
 */

public class ResetJlWpPwdActivity extends BaseActivity<ResetJlWpPwdPresenter> implements ResetJlWpPwdContract.IResetJlWpPwdView {


    public static void start(Context context) {
        Intent starter = new Intent(context, ResetJlWpPwdActivity.class);
        context.startActivity(starter);
    }


    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.et_iv_code)
    EditText etIvCode;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.ll_iv_code)
    LinearLayout llIvCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_send_sms_code)
    TextView tvSendSmsCode;
    @BindView(R.id.ll_code)
    LinearLayout llCode;
    @BindView(R.id.btn_reset_pwd_wp)
    Button btnResetPwdWp;

    private String ivCodeUrl;
    private String baseUrl = BuildConfig.DEBUG ? "http://uat.thor.opensdk.4001889177.com/" : JlWpApi.BASE_URL;
    private int randomToken = new Random().nextInt();
    private String phone;
    private LoadingView loadingView;

    @Override
    protected int getLayout() {
        return R.layout.activity_reset_jl_wp;
    }

    @Override
    protected ResetJlWpPwdPresenter getPresenter() {
        return new ResetJlWpPwdPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        loadingView = new LoadingView(mContext);
        topTextCenter.setText("重置吉交所微盘密码");
        setVisiable(toolbar, topTextCenter, topBack);


        phone = SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT)
                .getString(SPUtils.PHONE, "");


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

    @OnClick({R.id.top_back, R.id.tv_send_sms_code, R.id.btn_reset_pwd_wp})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.tv_send_sms_code:
                //短信验证码
                if (etIvCode.getText().length() == 0) {
                    ToastUtil.showShort(mContext, "请输入图形验证码");
                    return;
                }
                mPresenter.sendSmsCode(randomToken, phone, etIvCode.getText().toString());
                break;
            case R.id.btn_reset_pwd_wp:
                String dec_pwd = "";
                try {
                    dec_pwd = PasswordUtil.aesDecrypt(SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT).getString(SPUtils.PWD, ""), PasswordUtil._key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mPresenter.resetPwd(phone, etCode.getText().toString(), dec_pwd);
                break;
        }
    }

    @Override
    public void resetSuccess() {
        loadingView.dismiss();
        SPUtils.getInstance(mContext,SPUtils.SP_UPDATE_INFO)
                .putBoolean(SPUtils.RESET_JL_WP_PWD,false)
                .apply();
        ToastUtil.showShort(mContext, "重置成功");
        finish();
    }

    @Override
    public void sendSmsCodeSuccess() {
        Observable
                .interval(1, TimeUnit.SECONDS)
                .take(300)
                .compose(SchedulersCompat.<Long>applyIoSchedulers())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        this.unsubscribe();
                        tvSendSmsCode.setText("发送验证码");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        tvSendSmsCode.setText(String.format("%s", 300 - aLong));
                    }
                });
    }

    @Override
    public void loginSuccess() {
    }

    @Override
    public void showLoading() {
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
    }
}
