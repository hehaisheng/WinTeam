package com.shawnway.nav.app.wtw.module.user.user_center;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.YTXUserWrapper;
import com.shawnway.nav.app.wtw.module.login_register.update_pwd.UpdatePwdActivity;
import com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_reset_pwd.ResetJlWpPwdActivity;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_reset_pwd.ResetWpPwdActivity;
import com.shawnway.nav.app.wtw.module.user.setting.SettingActivity;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.Utils;

public class UserCenterActivity extends BaseActivity implements View.OnClickListener {
    private TextView activity_user_username;
    private TextView activity_user_telphone;

    @Override
    protected int getLayout() {
        return R.layout.activity_user_center;
    }

    @Override
    protected void initEventAndData() {
        initTitleBar();
        initView();
        initYTXData();
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.getInstance(UserCenterActivity.this, SPUtils.SP_ACCOUNT).deleteAllSPData();
                SPUtils.getInstance(UserCenterActivity.this, SPUtils.SP_WP).deleteAllSPData();
                SPUtils.getInstance(UserCenterActivity.this, SPUtils.SP_JL_WP).deleteAllSPData();
                RetrofitClient
                        .getInstance()
                        .api()
                        .logOut()
                        .compose(SchedulersCompat.<SettingActivity.LogoutResponse>applyIoSchedulers())
                        .subscribe(new BaseSubscriber<SettingActivity.LogoutResponse>() {
                            @Override
                            public void onSuccess(SettingActivity.LogoutResponse response) {

                            }
                        });
                finish();
            }
        });
    }

    private void initTitleBar() {
        findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        TextView centerText = (TextView) findViewById(R.id.top_text_center);
        centerText.setVisibility(View.VISIBLE);
        centerText.setText("个人信息");
        ImageButton backButton = (ImageButton) findViewById(R.id.top_back);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_back:
                finish();
                break;
            case R.id.updatePwd:
                Intent intent=new Intent(mContext,UpdatePwdActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.activity_card_update:
                UnbindBankCardActivity.getInstance(mContext);
                break;
            case R.id.unbind_jl_wp_bankcard:
                UnbindJlWpBankCardActivity.getInstance(mContext);
                break;
            case R.id.rl_reset_jl_wp_pwd:
                ResetJlWpPwdActivity.start(mContext);
                break;
            case R.id.rl_reset_wp_pwd:
                ResetWpPwdActivity.start(mContext);
                break;
        }
    }
    private void initView(){
        activity_user_username=(TextView)  findViewById(R.id.activity_user_username);
        activity_user_telphone=(TextView)  findViewById(R.id.activity_user_telphone);
        TextView activity_user_password = (TextView) findViewById(R.id.updatePwd);
        RelativeLayout activity_card_update = (RelativeLayout) findViewById(R.id.activity_card_update);
        RelativeLayout unbind_jl_wp_bankcard = (RelativeLayout) findViewById(R.id.unbind_jl_wp_bankcard);
        activity_user_password.setOnClickListener(this);
        activity_card_update.setOnClickListener(this);
        unbind_jl_wp_bankcard.setOnClickListener(this);
        findViewById(R.id.rl_reset_wp_pwd).setOnClickListener(this);
        findViewById(R.id.rl_reset_jl_wp_pwd).setOnClickListener(this);
    }
    /**
     * 获取赢天下注册信息
     */

    private void initYTXData(){
        RetrofitClient
                .getInstance()
                .api()
                .getPersonInfo()
                .compose(SchedulersCompat.<YTXUserWrapper>applyIoSchedulers())
                .subscribe(new BaseSubscriber<YTXUserWrapper>() {
                    @Override
                    public void onSuccess(final YTXUserWrapper ytxUserWrapper) {
                        if(ytxUserWrapper.registNickname!=null)
                            activity_user_username.setText(ytxUserWrapper.registNickname);
                        if(ytxUserWrapper.registMobile!=null)
                            activity_user_telphone.setText(Utils.setPhoneNum(ytxUserWrapper.registMobile));
                    }
                });
    }
}
