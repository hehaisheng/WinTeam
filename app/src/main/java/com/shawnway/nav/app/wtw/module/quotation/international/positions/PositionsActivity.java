package com.shawnway.nav.app.wtw.module.quotation.international.positions;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.module.quotation.international.billingRecord.BillingActivity;
import com.shawnway.nav.app.wtw.module.quotation.international.positions.today.TodayTradingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/27.
 */

public class PositionsActivity extends BaseActivity {


    @BindView(R.id.tab_positions)
    TabLayout tab_positions;
    @BindView(R.id.vp_positions)
    ViewPager vp_positions;
    @BindView(R.id.top_text_center)
    TextView topTextCenter;
    @BindView(R.id.top_text_right)
    TextView topTextRight;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.top_back)
    ImageButton topBack;
    public PositionsFragment positionsFragment;
    public TodayTradingFragment todayTradingFragment;

    @Override
    protected int getLayout() {
        return R.layout.activity_positions;
    }

    @Override
    protected void initEventAndData() {
        initTopBar();
        setupTab();
    }

    private void setupTab() {
        tab_positions.addTab(tab_positions.newTab().setText("持仓情况"));
        tab_positions.addTab(tab_positions.newTab().setText("今天下单"));
        PositionsPagerAdapter adapter = new PositionsPagerAdapter(getSupportFragmentManager());
        List<String> titles = new ArrayList<>();
        titles.add("持仓情况");
        titles.add("今天下单");
        List<Fragment> fragments = new ArrayList<>();
        positionsFragment = PositionsFragment.newInstance();
        todayTradingFragment = TodayTradingFragment.newInstance();
        fragments.add(positionsFragment);
        fragments.add(todayTradingFragment);
        adapter.addData(titles,fragments);
        vp_positions.setAdapter(adapter);
        tab_positions.setupWithViewPager(vp_positions);

    }


    private void initTopBar() {
        toolbar.setVisibility(View.VISIBLE);
        topTextCenter.setText("持仓");
        topTextCenter.setVisibility(View.VISIBLE);
        topBack.setVisibility(View.VISIBLE);
        topTextRight.setText("结算");
        topTextRight.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.top_back, R.id.top_text_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_back:
                finish();
                break;
            case R.id.top_text_right:
                startActivity(new Intent(mContext, BillingActivity.class));
                break;
        }
    }

    class PositionsPagerAdapter extends FragmentPagerAdapter {

        private List<String> mTitles;
        private List<Fragment> fragmentList;

        public PositionsPagerAdapter(FragmentManager fm) {
            super(fm);
            mTitles = new ArrayList<>();
            fragmentList = new ArrayList<>();
        }

        public void addData(List<String> titles, List<Fragment> fragments) {
            mTitles.addAll(titles);
            fragmentList.addAll(fragments);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
}
