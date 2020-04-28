
package com.sjl.adapter.recyclerview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;


public class RecyclerView2ViewPagerAdapter extends FragmentStatePagerAdapter {

  private String[] mTitles;
  private List<Fragment> mFragments;

  public RecyclerView2ViewPagerAdapter(FragmentManager fm, String[] mTitles, List<Fragment> mFragments) {
    super(fm);
    this.mTitles = mTitles;
    this.mFragments = mFragments;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return mTitles[position];
  }

  @Override
  public Fragment getItem(int position) {
    return mFragments.get(position);
  }

  @Override
  public int getCount() {
    return mFragments.size();
  }
}
