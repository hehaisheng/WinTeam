package com.shawnway.nav.app.wtw.module;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.module.home.HomeFragment;
import com.shawnway.nav.app.wtw.module.information.InformationFragment;
import com.shawnway.nav.app.wtw.module.mall.MallFragment;
import com.shawnway.nav.app.wtw.module.quotation.QuotationsFragment;
import com.shawnway.nav.app.wtw.module.user.UserFragment;
import com.shawnway.nav.app.wtw.service.HeartBeatService;
import com.shawnway.nav.app.wtw.service.MyReceiver;
import com.shawnway.nav.app.wtw.tool.PollingUtils;
import com.shawnway.nav.app.wtw.tool.ToastUtil;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    private HomeFragment homeFragment;
    private QuotationsFragment quotationsFragment;
    private MallFragment mallFragment;
    private InformationFragment informationFragment;
    private UserFragment userFragment;


    @BindView(R.id.rg_bottom)
    RadioGroup rg_bottom;
    private long firstTime;



    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData(Bundle savedState) {


        homeFragment = HomeFragment.newInstance();
        quotationsFragment = QuotationsFragment.newInstance();
        mallFragment = MallFragment.newInstance();
        informationFragment = InformationFragment.newInstance();
        userFragment = UserFragment.newInstance();
        //注册极光推送广播接收器
        registerMessageReceiver();
        //心跳线程，开始轮询的服务
        PollingUtils.startPollingService(mContext, HeartBeatService.class, HeartBeatService.ACTION);


        rg_bottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        switchFragment(0);
                        break;
                    case R.id.rb_quotations:
                        switchFragment(1);
                        break;
                    case R.id.rb_mall:
                        switchFragment(2);
                        break;
                    case R.id.rb_information:
                        switchFragment(3);
                        break;
                    case R.id.rb_user:
                        switchFragment(4);
                        break;
                }
            }
        });
        //设置保存的状态
        setupFrameLayout(savedState);

    }


    private void setupFrameLayout(Bundle savedState) {

        if (savedState != null) {


            homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
            quotationsFragment = (QuotationsFragment) getSupportFragmentManager().findFragmentByTag("quotationsFragment");
            mallFragment = (MallFragment) getSupportFragmentManager().findFragmentByTag("mallFragment");
            informationFragment = (InformationFragment) getSupportFragmentManager().findFragmentByTag("informationFragment");
            userFragment = (UserFragment) getSupportFragmentManager().findFragmentByTag("userFragment");
            switchFragment(0);
        } else {


            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_main_container, homeFragment, "homeFragment")
                    .add(R.id.fl_main_container, quotationsFragment, "quotationsFragment")
                    .add(R.id.fl_main_container, mallFragment, "mallFragment")
                    .add(R.id.fl_main_container, informationFragment, "informationFragment")
                    .add(R.id.fl_main_container, userFragment, "userFragment")
                    .commit();
            switchFragment(0);
        }
    }

    private void switchFragment(int i) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (i) {
            case 0:

                transaction.show(homeFragment)
                        .hide(quotationsFragment)
                        .hide(mallFragment)
                        .hide(informationFragment)
                        .hide(userFragment);

                homeFragment.setUserVisibleHint(true);

                quotationsFragment.setUserVisibleHint(false);
                break;
            case 1:

                transaction.show(quotationsFragment)
                        .hide(homeFragment)
                        .hide(mallFragment)
                        .hide(informationFragment)
                        .hide(userFragment);
                quotationsFragment.setUserVisibleHint(true);
                homeFragment.setUserVisibleHint(false);
                break;
            case 2:
                transaction.show(mallFragment)
                        .hide(quotationsFragment)
                        .hide(homeFragment)
                        .hide(informationFragment)
                        .hide(userFragment);

                homeFragment.setUserVisibleHint(false);
                mallFragment.setUserVisibleHint(true);
                quotationsFragment.setUserVisibleHint(false);
                break;
            case 3:
                transaction.show(informationFragment)
                        .hide(quotationsFragment)
                        .hide(mallFragment)
                        .hide(homeFragment)
                        .hide(userFragment);
                homeFragment.setUserVisibleHint(false);
                informationFragment.setUserVisibleHint(true);
                quotationsFragment.setUserVisibleHint(false);

                break;
            case 4:
                transaction.show(userFragment)
                        .hide(quotationsFragment)
                        .hide(mallFragment)
                        .hide(informationFragment)
                        .hide(homeFragment);
                homeFragment.setUserVisibleHint(false);
                userFragment.setUserVisibleHint(true);
                quotationsFragment.setUserVisibleHint(false);
                break;
        }
        transaction.commit();
    }



    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(MainActivity.this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(MainActivity.this);
    }

    //取消广播注册，停止心跳轮询
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
        PollingUtils.stopPollingService(mContext, HeartBeatService.class, HeartBeatService.ACTION);
    }


    private MyReceiver myReceiver;

    /**
     * 极光推送，只做动态注册
     */
    public void registerMessageReceiver() {
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction("cn.jpush.android.intent.REGISTRATION");
        filter.addAction("cn.jpush.android.intent.MESSAGE_RECEIVED");
        filter.addAction("cn.jpush.android.intent.NOTIFICATION_RECEIVED");
        filter.addAction("cn.jpush.android.intent.ACTION_RICHPUSH_CALLBACK");
        filter.addAction("cn.jpush.android.intent.CONNECTION");
        registerReceiver(myReceiver, filter);
    }



    @Override
    public void onBackPressed() {

        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            firstTime = secondTime;
            ToastUtil.showShort(mContext, "再次点击返回退出应用");
        } else {
            finish();
        }

    }

}
