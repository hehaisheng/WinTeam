package com.shawnway.nav.app.wtw.module.user.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.tool.StringUtils;

/**
 * Created by Administrator on 2016/9/13.
 */
public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initToolbar();
        initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.activity_about_version)).setText("版本：" + StringUtils.getVersionName(this));
        ((TextView) findViewById(R.id.activity_about_wxnum)).setText(String.format("微信号：%s", "jiatouapp"));
    }

    private void initToolbar() {
        findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        TextView centerText = (TextView) findViewById(R.id.top_text_center);
        centerText.setVisibility(View.VISIBLE);
        centerText.setText("关于赢天下");
        ImageButton backButton = (ImageButton) findViewById(R.id.top_back);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
        }
    }
}
