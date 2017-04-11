package com.shawnway.nav.app.wtw.module.quotation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseFragment;
import com.shawnway.nav.app.wtw.module.quotation.international.international_real_list.InternationalFragment;
import com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_list.JlWpListFragment;
import com.shawnway.nav.app.wtw.view.MyViewPager;
import com.shawnway.nav.app.wtw.view.PagerSlidingTabStrip;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 行情页面
 * Created by Administrator on 2016/7/19 0019.
 */
public class QuotationsFragment extends BaseFragment {


    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.top_text_center)
    TextView center;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    MyViewPager pager;

    private ArrayList<SubFragEntity> mFragmentList;
    private InternationalFragment internationalFragment = InternationalFragment.newInstance();
//    private WpListFragment wpListFragment = WpListFragment.getInstance();
    private JlWpListFragment jlWpListFragment = JlWpListFragment.getInstance();


    public static QuotationsFragment newInstance() {

        Bundle args = new Bundle();

        QuotationsFragment fragment = new QuotationsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quotations;
    }

    @Override
    protected void initEventAndData() {
        initToolBar();
        initContent();
    }

    private void initToolBar() {
        toolbar.setVisibility(View.VISIBLE);
        center.setVisibility(View.VISIBLE);
        center.setText("行情");
    }

    private void addTab() {
        if (mFragmentList == null) {
            mFragmentList = new ArrayList<>();
        } else
            mFragmentList.clear();

        mFragmentList.add(new SubFragEntity(getString(R.string.title_fragment_quotations_jl_wp), jlWpListFragment));
//        mFragmentList.add(new SubFragEntity(getString(R.string.title_fragment_quotations_microdisk), wpListFragment));
        mFragmentList.add(new SubFragEntity(getString(R.string.title_fragment_quotations_interfuture), internationalFragment));
    }

    private void initContent() {
        addTab();
        tabs.setTextSize(14);
        pager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager()));
        tabs.setViewPager(pager);
        pager.setOffscreenPageLimit(3);
        setTabsValue();
    }


    private void setTabsValue() {
        tabs.setIndicatorStyle(PagerSlidingTabStrip.STYLE_CIRCLE);
        tabs.setFillTabBar(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (pager.getCurrentItem() == 0) {
                jlWpListFragment.setUserVisibleHint(true);
            } else if (pager.getCurrentItem() == 1) {
                internationalFragment.setUserVisibleHint(true);
//                wpListFragment.setUserVisibleHint(true);
            } else if (pager.getCurrentItem() == 2) {
            }
        } else {
//            wpListFragment.setUserVisibleHint(false);
            internationalFragment.setUserVisibleHint(false);
            jlWpListFragment.setUserVisibleHint(false);
        }
    }


    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentList.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position).getFragment();
        }

    }

    class SubFragEntity {
        String mTitle;
        Fragment mFragment;

        public SubFragEntity(String title, Fragment fragment) {
            mTitle = title;
            mFragment = fragment;
        }

        public String getTitle() {
            return mTitle;
        }

        public Fragment getFragment() {
            return mFragment;
        }


    }

}
