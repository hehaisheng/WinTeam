package com.shawnway.nav.app.wtw.module.user;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Kevin on 2016/12/23.
 */
public class NewGuidelinesActivity extends BaseActivity {
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.top_text_right)
    TextView topTextRight;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;

    public static void getInstance(Context context) {
        Intent intent = new Intent(context, NewGuidelinesActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.top_back)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_newguidelines;
    }

    @Override
    protected void initEventAndData() {
        initToolbar();

    }


    private void initToolbar() {
        topTextCenter.setText("新手指引");
        setVisiable(toolbar, topBack, topTextCenter);
    }

}
