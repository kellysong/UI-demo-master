package com.sjl.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjl.adapter.MyFragmentPagerAdapter;
import com.sjl.uidemo.R;


/**
 * @author: yaoyongchao
 * @date: 2016/3/28 15:18
 * @description:
 */
public class WeiXinFragment extends Fragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weixin, container, false);
        mViewPager= (ViewPager) view.findViewById(R.id.vp_homepage_show);
        mTabLayout= (TabLayout) view.findViewById(R.id.tl_homepage_navigation);
        initData();
        return view;
    }
    private void initView(View view) {

    }

    private void initData() {
        ////getSupportFragmentManager()是Activity嵌套fragment时使用
        //getChildFragmentManager()是Fragment嵌套Fragment时使用
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager(),
                getContext());
        adapter.notifyDataSetChanged();
        mViewPager.setAdapter(adapter);
        //TabLayout
        mTabLayout.setupWithViewPager(mViewPager);

    }
}
