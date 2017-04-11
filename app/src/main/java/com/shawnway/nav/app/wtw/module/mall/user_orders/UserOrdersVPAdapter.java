package com.shawnway.nav.app.wtw.module.mall.user_orders;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Kevin on 2016/11/16
 */

public class UserOrdersVPAdapter extends FragmentPagerAdapter {
    private List<Fragment> baseFragmentList;
    private String[] titles;

    public UserOrdersVPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return baseFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return baseFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public void setBaseFragmentList(List<Fragment> baseFragmentList) {
        this.baseFragmentList = baseFragmentList;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }
}
