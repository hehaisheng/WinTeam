package com.shawnway.nav.app.wtw.module.mall.user_orders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Kevin on 2016/11/16
 */

public class UserOrdersActivity extends BaseActivity<UserOrdersPresenter> {
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.top_text_right)
    TextView topTextRight;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.userOrders_tab_layout)
    TabLayout userOrdersTabLayout;
    @BindView(R.id.userOrders_viewpager)
    ViewPager userOrdersViewpager;
    private List<Fragment> baseFragments = new ArrayList<>();
    private AllUserOrdersFragment allUserOrdersFragment;
    private ReceivedOrdersFragment receivedOrdersFragment;
    private NotShippedOrdersFragment notShippedOrdersFragment;
    private NotReceivedOrdersFragment notReceivedOrdersFragment;

    @Override
    protected int getLayout() {
        return R.layout.activity_user_orders;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        initToolbar();
        initTabFragment(savedInstanceState);
        initTabLayoutAndViewPager("全部订单", "待发货", "待收货", "已收货");
    }

    /**
     * 初始化TabLayout和ViewPager
     */
    private void initTabLayoutAndViewPager(String... titles) {
        userOrdersViewpager.setOffscreenPageLimit(4);
        userOrdersTabLayout.setTabMode(TabLayout.MODE_FIXED);
        UserOrdersVPAdapter userOrdersVPAdapter = new UserOrdersVPAdapter(getSupportFragmentManager());
        userOrdersVPAdapter.setBaseFragmentList(baseFragments);
        userOrdersVPAdapter.setTitles(titles);
        userOrdersViewpager.setOffscreenPageLimit(3);
        userOrdersViewpager.setAdapter(userOrdersVPAdapter);
        userOrdersTabLayout.setupWithViewPager(userOrdersViewpager);

    }

    private void initTabFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            allUserOrdersFragment = AllUserOrdersFragment.newInstance();
            notShippedOrdersFragment = NotShippedOrdersFragment.newInstance();
            notReceivedOrdersFragment = NotReceivedOrdersFragment.newInstance();
            receivedOrdersFragment = ReceivedOrdersFragment.newInstance();
        }
        baseFragments.add(allUserOrdersFragment);
        baseFragments.add(notShippedOrdersFragment);
        baseFragments.add(notReceivedOrdersFragment);
        baseFragments.add(receivedOrdersFragment);
    }

    public static void start(Context context) {


            Intent starter = new Intent(context, UserOrdersActivity.class);
            context.startActivity(starter);
    }

    private void initToolbar() {
        topTextCenter.setText("我的订单");
        setVisiable(toolbar, topBack, topTextCenter);
    }


    @OnClick(R.id.top_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_back:
                finish();
                break;
        }
    }

    /**
     * 刷新已收货订单状态
     */
    public void refreshReceived() {
        receivedOrdersFragment.refreshAdapter();
    }

    /**
     * 刷新所有订单状态
     */
    public void refreshAllOrder() {
        allUserOrdersFragment.refreshAdapter();
    }

    /**
     * 刷新待收货订单状态
     */
    public void refreshNotReceived() {
        notReceivedOrdersFragment.refreshAdapter();
    }
}
