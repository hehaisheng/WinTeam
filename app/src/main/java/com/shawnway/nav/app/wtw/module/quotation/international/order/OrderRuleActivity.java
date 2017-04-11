package com.shawnway.nav.app.wtw.module.quotation.international.order;

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
 * Created by Cicinnus on 2016/11/30.
 */

public class OrderRuleActivity extends BaseActivity {


    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;


    public static void start(Context context) {
        Intent starter = new Intent(context, OrderRuleActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_order_rule;
    }

    @Override
    protected void initEventAndData() {
        topTextCenter.setText("合约条款");
        setVisiable(topBack, topTextCenter, toolbar);

    }

    @OnClick(R.id.top_back)
    void click(View v) {
        finish();
    }

}
