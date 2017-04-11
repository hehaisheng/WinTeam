package com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.transfer_tran;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Kevin on 2016/12/7
 * 转账页面
 */

public class TransferTranActivity extends BaseActivity {
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.top_text_right)
    TextView topTextRight;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.transfer_tran_tab_layout)
    TabLayout transferTranTabLayout;
    @BindView(R.id.content_fragment)
    FrameLayout contentFragment;
    private final int TYPE_RECHARGE = 1;
    private final int TYPE_WITHDRAW = 2;
    private RechargeFragment rechargeFragment;
    private boolean isVisiable = false;

    private WithDrawFragment withDrawFragment;


    @Override
    protected int getLayout() {
        return R.layout.activity_transfer_tran;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, TransferTranActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    /**
     * 初始化控件
     *
     * @param savedInstanceState
     */
    private void initView(Bundle savedInstanceState) {
        initToolbar();
        initFragment(savedInstanceState);
        initTabLayout();
    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        transferTranTabLayout.setTabMode(TabLayout.MODE_FIXED);
        transferTranTabLayout.addTab(transferTranTabLayout.newTab()
                .setText("充值")
                .setTag(TYPE_RECHARGE));
        transferTranTabLayout.addTab(transferTranTabLayout.newTab()
                .setText("提现")
                .setTag(TYPE_WITHDRAW));
        transferTranTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if ((int) tab.getTag() == TYPE_RECHARGE) {
                    setTabFragment(TYPE_RECHARGE);
                } else if ((int) tab.getTag() == TYPE_WITHDRAW) {
                    setTabFragment(TYPE_WITHDRAW);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        topTextCenter.setText("转账");
        setVisiable(toolbar, topBack, topTextCenter);
    }

    /**
     * 初始化TabFragment
     */
    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            rechargeFragment = (RechargeFragment) getSupportFragmentManager().findFragmentByTag("JlRechargeFragment");
            withDrawFragment = (WithDrawFragment) getSupportFragmentManager().findFragmentByTag("JlWithDrawFragment");
            setTabFragment(TYPE_RECHARGE);
        } else {
            rechargeFragment = RechargeFragment.newInstance();
            withDrawFragment = WithDrawFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_fragment, rechargeFragment, "JlRechargeFragment")
                    .add(R.id.content_fragment, withDrawFragment, "JlWithDrawFragment")
                    .commit();
            setTabFragment(TYPE_RECHARGE);
        }
    }

    /**
     * 点击Tab的Fragment切换方法
     * 初始化时充值Fragment
     *
     * @param type
     */
    private void setTabFragment(int type) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (type) {
            case TYPE_RECHARGE:
                isVisiable = true;
                fragmentTransaction.hide(withDrawFragment)
                        .show(rechargeFragment);
                break;
            case TYPE_WITHDRAW:
                isVisiable = false;
                fragmentTransaction.hide(rechargeFragment)
                        .show(withDrawFragment);
                break;
        }
        fragmentTransaction.commit();
    }


    @OnClick(R.id.top_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_back:
                finish();
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (rechargeFragment != null && isVisiable) {
            if (rechargeFragment.onKeyDown(keyCode, event)) {
                return true;
            } else finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
