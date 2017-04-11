package com.shawnway.nav.app.wtw.module.user.ticket;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.module.user.ticket.jl_wp_ticket.JlWpTicketFragment;
import com.shawnway.nav.app.wtw.module.user.ticket.ticket.TicketFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class MyTicketActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MyTicketActivity";
    @BindView(R.id.top_back)
    ImageButton topBack;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.vp_ticket)
    ViewPager vp_ticket;


    public static void getInstance(Context context) {
        Intent intent = new Intent(context, MyTicketActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_myticket;
    }

    @Override
    protected void initEventAndData() {
        setVisiable(toolbar, topBack, topTextCenter);
        topTextCenter.setText("我的赢家券");

        String[] titles = new String[]{"吉交所现金券","期货现金券"};
        List<Fragment> fragmentList = new ArrayList<>();
//        WpTicketFragment wpTicketFragment = WpTicketFragment.newInstance();
        JlWpTicketFragment jlWpTicketFragment = JlWpTicketFragment.newInstance();
        TicketFragment ticketFragment = TicketFragment.newInstance();
        fragmentList.add(jlWpTicketFragment);
//        fragmentList.add(wpTicketFragment);
        fragmentList.add(ticketFragment);
        TicketVpAdapter ticketVpAdapter = new TicketVpAdapter(getSupportFragmentManager());
        ticketVpAdapter.addFragments(fragmentList, Arrays.asList(titles));
        vp_ticket.setAdapter(ticketVpAdapter);
        tabLayout.addTab(tabLayout.newTab().setText("吉交所微盘现金券"));
//        tabLayout.addTab(tabLayout.newTab().setText("粤国际微盘现金券"));
        tabLayout.addTab(tabLayout.newTab().setText("期货现金券"));
        tabLayout.setupWithViewPager(vp_ticket);
        vp_ticket.setOffscreenPageLimit(3);
    }


    @OnClick({R.id.top_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_back:
                finish();
                break;
        }
    }



}
