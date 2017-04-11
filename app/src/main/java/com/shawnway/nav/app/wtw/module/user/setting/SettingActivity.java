package com.shawnway.nav.app.wtw.module.user.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.DataCleanManager;
import com.shawnway.nav.app.wtw.tool.GlideManager;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.StringUtils;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.tencent.bugly.beta.Beta;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private Button btnLogOut;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pwd = SPUtils.getInstance(mContext, SPUtils.SP_ACCOUNT)
                .getString(SPUtils.PWD, "");
        initToolbar();
        initView();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_my_setting;
    }

    @Override
    protected void initEventAndData() {

    }

    public void initToolbar() {
        findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        TextView centerText = (TextView) findViewById(R.id.top_text_center);
        centerText.setVisibility(View.VISIBLE);
        centerText.setText("我的设置");
        ImageButton backButton = (ImageButton) findViewById(R.id.top_back);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(this);

    }

    public void initView() {
        findViewById(R.id.activity_setting_suggestions).setOnClickListener(this);
        findViewById(R.id.activity_setting_wins).setOnClickListener(this);
        findViewById(R.id.activity_setting_versions).setOnClickListener(this);
        findViewById(R.id.clear_cache).setOnClickListener(this);
        btnLogOut = (Button) findViewById(R.id.activity_setting_return);
        if (StringUtils.isNotEmpty(pwd)) {
            btnLogOut.setVisibility(View.VISIBLE);
            btnLogOut.setOnClickListener(this);
        } else {
            btnLogOut.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.activity_setting_messages://消息中心
                ToastUtil.showShort(this, getString(R.string.function_not_open));
                break;
            case R.id.activity_setting_suggestions://意见反馈
                startActivity(new Intent(this, FeedBackActivity.class));
                break;
            case R.id.activity_setting_wins://关于赢天下
                startActivity(new Intent(SettingActivity.this, AboutActivity.class));
                break;
            case R.id.activity_setting_versions://版本检查
                Beta.checkUpgrade();
                break;
            case R.id.activity_setting_return:
                RetrofitClient
                        .getInstance()
                        .api()
                        .logOut()
                        .compose(SchedulersCompat.<LogoutResponse>applyIoSchedulers())
                        .subscribe(new BaseSubscriber<LogoutResponse>() {
                            @Override
                            public void onSuccess(LogoutResponse response) {

                            }
                        });
                ToastUtil.showShort(SettingActivity.this, "退出成功");
                SPUtils.getInstance(SettingActivity.this, SPUtils.SP_ACCOUNT).deleteAllSPData();
                SPUtils.getInstance(SettingActivity.this, SPUtils.SP_WP).deleteAllSPData();
                SPUtils.getInstance(SettingActivity.this, SPUtils.SP_JL_WP).deleteAllSPData();
                finish();
                break;
            case R.id.clear_cache:
                GlideManager.clearPicCache(mContext);
                DataCleanManager.cleanInternalCache(mContext);
                ToastUtil.showShort(mContext,"缓存清理成功");
                break;

        }
    }

    public class VersionResponse {
        public int version;
    }

    public class LogoutResponse {
        public String statusCode;
    }
}
