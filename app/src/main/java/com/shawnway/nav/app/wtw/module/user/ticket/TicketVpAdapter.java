package com.shawnway.nav.app.wtw.module.user.ticket;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cicinnus on 2016/12/19.
 */

public class TicketVpAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> mTitles;

    public TicketVpAdapter(FragmentManager fm) {
        super(fm);
        fragmentList = new ArrayList<>();
        mTitles = new ArrayList<>();
    }
    public void addFragments(List<Fragment> fragments,List<String> titles){
        fragmentList.addAll(fragments);
        mTitles.addAll(titles);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
