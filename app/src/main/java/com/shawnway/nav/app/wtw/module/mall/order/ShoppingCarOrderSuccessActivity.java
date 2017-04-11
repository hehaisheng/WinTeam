package com.shawnway.nav.app.wtw.module.mall.order;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.module.mall.user_orders.UserOrdersActivity;
import com.shawnway.nav.app.wtw.tool.TimeUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Cicinnus on 2016/11/18.
 */

public class ShoppingCarOrderSuccessActivity extends BaseActivity {


    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.order_time)
    TextView orderTime;
    @BindView(R.id.totalSpend)
    TextView totalSpend;

    public static void start(Context context, int point) {
        Intent starter = new Intent(context, ShoppingCarOrderSuccessActivity.class);
        starter.putExtra("point", point);
        context.startActivity(starter);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_shopping_car_order_success;
    }

    @Override
    protected void initEventAndData() {
        topTextCenter.setText("兑换结果");
        setVisiable(topBack, topTextCenter, toolbar);
        int point = getIntent().getIntExtra("point", 0);
        totalSpend.setText(String.format("消费积分：%s", point));
        orderTime.setText(String.format("%s", TimeUtils.dateYMDHM(System.currentTimeMillis())));
    }

    @OnClick({R.id.top_back,R.id.checkOrder})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.checkOrder:
                UserOrdersActivity.start(mContext);
                finish();
                break;
        }
    }
}
