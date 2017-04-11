package com.shawnway.nav.app.wtw.module.login_register.update_pwd;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.StringUtils;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.LoadingView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UpdatePwdActivity extends BaseActivity<UpdatePwdPresenter> implements UpdatePwdContract.IUpdatePasswordView, View.OnClickListener {
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.update_login_pwd)
    EditText updateLoginPwd;
    @BindView(R.id.update_login_new_pwd)
    EditText updateLoginNewPwd;
    @BindView(R.id.update_login_confirm_pwd)
    EditText updateLoginConfirmPwd;
    @BindView(R.id.ll_jl_wp_update)
    LinearLayout ll_jl_wp_update;
    @BindView(R.id.sendJlWpCode)
    TextView sendJlWpCode;
    @BindView(R.id.et_jl_wp_code)
    EditText et_jl_wp_code;

    private LoadingView loadingView;
    private String access_token;


    @Override
    protected int getLayout() {
        return R.layout.activity_passwordupdate;
    }

    @Override
    protected UpdatePwdPresenter getPresenter() {
        return new UpdatePwdPresenter(mContext, this);
    }

    @Override
    protected void initEventAndData() {
        initTitleBar();
        access_token = SPUtils.getInstance(mContext, SPUtils.SP_JL_WP).getString(SPUtils.JL_WP_TOKEN, "");
        if (TextUtils.isEmpty(access_token)) {
            ll_jl_wp_update.setVisibility(View.INVISIBLE);
        } else {
            ll_jl_wp_update.setVisibility(View.VISIBLE);
        }

    }

    private void initTitleBar() {
        setVisiable(toolbar, topBack, topTextCenter);
        topTextCenter.setText("交易密码修改");
    }


    @OnClick({R.id.top_back, R.id.activity_password_btn, R.id.sendJlWpCode})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.top_back:
                finish();
                break;
            //确认
            case R.id.activity_password_btn:
                String oldPwd = updateLoginPwd.getText().toString().trim();
                String newPwd = updateLoginNewPwd.getText().toString().trim();
                String confirmPwd = updateLoginConfirmPwd.getText().toString().trim();
                String phone = SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT).getString(SPUtils.PHONE, "");
                String wp_token = SPUtils.getInstance(mContext, SPUtils.SP_WP).getString(SPUtils.WP_TOKEN, "");
                if (StringUtils.isEmpty(oldPwd)) {
                    ToastUtil.showShort(this, "当前密码不能为空");
                    updateLoginPwd.requestFocus();
                    return;
                }
                if (StringUtils.isEmpty(newPwd)) {
                    ToastUtil.showShort(this, "新置密码不能为空");
                    updateLoginNewPwd.requestFocus();
                    return;
                }
                if (StringUtils.isEmpty(confirmPwd)) {
                    ToastUtil.showShort(this, "确认密码不能为空");
                    updateLoginConfirmPwd.requestFocus();
                    return;
                }
                if (!newPwd.equals(confirmPwd)) {
                    ToastUtil.showShort(this, "新置密码与确认密码不一致，请重新输入");
                    updateLoginNewPwd.setText("");
                    updateLoginConfirmPwd.setText("");
                    updateLoginNewPwd.requestFocus();
                    return;
                }
                if(ll_jl_wp_update.getVisibility()==View.VISIBLE&&et_jl_wp_code.getText().length()==0){
                    ToastUtil.showShort(mContext,"请输入验证码");
                    return;
                }
                mPresenter.updatePwd(oldPwd, newPwd, phone, wp_token,et_jl_wp_code.getText().toString());
                break;
            case R.id.sendJlWpCode:
                mPresenter.sendJlWpCode(access_token);
                break;
        }
    }

    @Override
    public void updateSuccess() {
        ToastUtil.showShort(mContext, "密码修改成功");
        LoginActivity.getInstance(mContext);
        SPUtils.getInstance(mContext, SPUtils.SP_COOKIES).deleteAllSPData();
        SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT).deleteAllSPData();
        SPUtils.getInstance(mContext, SPUtils.SP_WP).deleteAllSPData();
        SPUtils.getInstance(mContext, SPUtils.SP_JL_WP).deleteAllSPData();
        finish();
    }

    @Override
    public void sendSuccess() {
        Observable
                .interval(1, TimeUnit.SECONDS)
                .take(300)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        this.unsubscribe();
                        sendJlWpCode.setClickable(true);
                        sendJlWpCode.setText("获取验证码");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        sendJlWpCode.setClickable(false);
                        sendJlWpCode.setText(String.format("%s", 300 - aLong));
                    }
                });
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

    }

    @Override
    public void showError(String errorMsg) {
        ToastUtil.showShort(mContext, errorMsg);
        loadingView.dismiss();
    }
}
