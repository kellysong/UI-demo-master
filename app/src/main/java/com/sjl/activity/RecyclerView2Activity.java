

package com.sjl.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sjl.adapter.recyclerview.RecyclerView2ViewPagerAdapter;
import com.sjl.fragment.MyFragment;
import com.sjl.uidemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView多种方向布局
 */
public class RecyclerView2Activity extends AppCompatActivity{

  //初始化各种控件，照着xml中的顺序写

  private Toolbar mToolbar;
  private ViewPager mViewPager;

  // TabLayout中的tab标题
  private String[] mTitles;
  // 填充到ViewPager中的Fragment
  private List<Fragment> mFragments;
  // ViewPager的数据适配器
  private RecyclerView2ViewPagerAdapter mViewPagerAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recyclerview2);

    // 初始化各种控件
    initViews();

    // 初始化mTitles、mFragments等ViewPager需要的数据
    //这里的数据都是模拟出来了，自己手动生成的，在项目中需要从网络获取数据
    initData();

    // 对各种控件进行设置、适配、填充数据
    configViews();
  }

  private void initData() {

    // Tab的标题采用string-array的方法保存，在res/values/arrays.xml中写
    mTitles = getResources().getStringArray(R.array.tab_titles);

    //初始化填充到ViewPager中的Fragment集合
    mFragments = new ArrayList<>();
    for (int i = 0; i < mTitles.length; i++) {
      Bundle mBundle = new Bundle();
      mBundle.putInt("flag", i);
      MyFragment mFragment = new MyFragment();
      mFragment.setArguments(mBundle);
      mFragments.add(i, mFragment);
    }
  }

  private void configViews() {
    // 设置显示Toolbar
    setSupportActionBar(mToolbar);
    // 初始化ViewPager的适配器，并设置给它
    mViewPagerAdapter = new RecyclerView2ViewPagerAdapter(getSupportFragmentManager(), mTitles, mFragments);
    mViewPager.setAdapter(mViewPagerAdapter);
    // 设置ViewPager最大缓存的页面个数
    mViewPager.setOffscreenPageLimit(5);

  }



  private void initViews() {

    mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
    mViewPager = (ViewPager) findViewById(R.id.viewpager);

  }

  /*@Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }*/

}
