package com.sjl.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sjl.fragment.LazyPageFragment;

/**
 * Created by song on 2017/8/20.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    public final int COUNT = 3;
    private String[] titles = new String[]{"Tab1", "Tab2", "Tab3"};
    private Context context;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return LazyPageFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
