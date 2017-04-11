package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_chart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;

/**
 * Created by 裘 on 2016/9/3 0003.
 */
public class RegularActivity extends FragmentActivity implements View.OnClickListener {

    public static void getInstance(Context context) {
        Intent i = new Intent(context, RegularActivity.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular);
        initToolBar();
    }

    private void initToolBar() {
        findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        ImageButton iBtn = (ImageButton) findViewById(R.id.top_back);
        iBtn.setVisibility(View.VISIBLE);
        iBtn.setOnClickListener(this);
        TextView center = (TextView) findViewById(R.id.top_text_center);
        center.setVisibility(View.VISIBLE);
        center.setText("产品规则介绍");
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
