package com.shawnway.nav.app.wtw.module.login_register.login;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.module.information.NewsActivity;
import com.shawnway.nav.app.wtw.module.login_register.RegisterActivity;
import com.shawnway.nav.app.wtw.module.login_register.forget_pwd.PwdResetActivity;
import com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.JlWpDetailActivity;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.WpDetailActivity;
import com.shawnway.nav.app.wtw.service.ResetWpPwdService;
import com.shawnway.nav.app.wtw.service.UpdateJlWpInfoService;
import com.shawnway.nav.app.wtw.service.UpdateWpInfoService;
import com.shawnway.nav.app.wtw.tool.PasswordUtil;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.StringUtils;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.LoadingView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.ILoginView,View.OnClickListener {
    private static final String TAG = "LoginActivity";

    @BindView(R.id.activity_login_phonenum)
    EditText mEtPhone;
    @BindView(R.id.activity_login_pwd)
    EditText mEtPwd;
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    private String phone;
    private LoadingView loadingGif;
    private String pwd;

    public static void getInstance(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter(mContext,this);
    }

    @Override
    protected void initEventAndData() {
        topTextCenter.setText("登录");
        setVisiable(toolbar,topBack,topTextCenter);
        loadingGif = new LoadingView(mContext);
    }

    @OnClick({
            R.id.top_back,
            R.id.activity_login_btn,
            R.id.activity_login_tv_register,
            R.id.activity_login_tv_forgetpwd,
            R.id.shawnway})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.activity_login_btn:
                phone = mEtPhone.getText().toString().trim();
                pwd = mEtPwd.getText().toString().trim();
                try {
                    pwd = PasswordUtil.aesEncrypt(pwd, PasswordUtil._key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (StringUtils.isEmpty(phone)) {
                    ToastUtil.showShort(this, getString(R.string.phone_num_cannot_empty));
                    mEtPhone.requestFocus();
                    return;
                }
                if (StringUtils.isEmpty(pwd)) {
                    ToastUtil.showShort(this, getString(R.string.pwd_cannot_empty));
                    mEtPwd.requestFocus();
                    return;
                }

                mPresenter.login(phone, pwd);

                break;
            case R.id.activity_login_tv_register:
                finish();
                RegisterActivity.getInstance(this);
                break;
            case R.id.activity_login_tv_forgetpwd:
                Intent intent = new Intent(this, PwdResetActivity.class);
                startActivity(intent);
                break;
            case R.id.shawnway:
                NewsActivity.getInstance(this, "http://www.shawnway.cn/", "尚为软件");
                break;
            default:
                break;
        }
    }


    @Override
    public void loginSuccess() {
//        mPresenter.loginWp(phone,mEtPwd.getText().toString().trim());
        mPresenter.loginJlWp(phone,mEtPwd.getText().toString().trim());
        //修改密码后更新信息
        if (SPUtils.getInstance(mContext,SPUtils.SP_UPDATE_INFO)
                .getBoolean(SPUtils.UPDATE_PWD,false)) {
            //更新粤国际微盘
            Intent intent = new Intent(mContext, UpdateWpInfoService.class);
            intent.putExtra(UpdateWpInfoService.MOBILE,phone);
            intent.putExtra(UpdateWpInfoService.PWD,mEtPwd.getText().toString().trim());
            startService(intent);
            //更新吉交所微盘
            Intent intent2 = new Intent(mContext, UpdateJlWpInfoService.class);
            intent2.putExtra(UpdateJlWpInfoService.MOBILE,phone);
            intent2.putExtra(UpdateJlWpInfoService.PWD,mEtPwd.getText().toString().trim());
            startService(intent2);
        }
        //忘记密码
        if(SPUtils.getInstance(mContext,SPUtils.SP_UPDATE_INFO)
                .getBoolean(SPUtils.RESET_PWD,false)){
            ResetWpPwdService.start(mContext,phone,mEtPwd.getText().toString().trim());
        }
        EventBus.getDefault().post(new JlWpDetailActivity.LoginWinEvent());
        EventBus.getDefault().post(new WpDetailActivity.LoginEvent());
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

    }

    @Override
    public void showError(String errorMsg) {
        loadingGif.dismiss();
        ToastUtil.showShort(mContext,errorMsg);
    }
}


