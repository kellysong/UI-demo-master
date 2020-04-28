package com.sjl.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.sjl.adapter.MyFragmentPagerAdapter;
import com.sjl.uidemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * TabLayout+ViewPager+Fragment实现顶部tab导航
 * Created by song on 2017/8/20.
 */

public class TabLayoutActivity extends AppCompatActivity {
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }
    /**
     * 初始化控件
     */
    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tablayout);
        ButterKnife.bind(this);

    }
    private void initListener() {

    }

    /**
     * 初始化数据
     */
    private void initData() {

//        viewPager.setOffscreenPageLimit(1);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),
                this);
        viewPager.setAdapter(adapter);
        //TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
