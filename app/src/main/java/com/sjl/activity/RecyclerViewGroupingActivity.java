package com.sjl.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.sjl.adapter.RecyclerViewGroupingAdapter;
import com.sjl.entity.MenuInfo;
import com.sjl.uidemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * RecyclerView 分组显示条目
 */

public class RecyclerViewGroupingActivity extends BaseActivity {
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private RecyclerViewGroupingAdapter mRecyclerViewAdapter; //适配器
    private GridLayoutManager manage;
    private List<MenuInfo.ChildListBean> childListBeans;
    private List<MenuInfo> list;

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_grouping_recyclerview);
        ButterKnife.bind(this);

    }

    @Override
    protected void initListener() {

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        list = new ArrayList<>();
        //假设后台返回数组格式如下
        List<String> mDatas = null;
        MenuInfo info = null;
        for (int i = 0; i < 3; i++) {
            info = new MenuInfo();
            if (i == 0) {
                info.setHeader("便民服务");
                mDatas = new ArrayList<>();
                mDatas.add("便民服务簿");
                mDatas.add("生活小常识");
                mDatas.add("活动会");
                info.setDataList(mDatas);
            } else if (i == 1) {
                info.setHeader("商品服务");
                mDatas = new ArrayList<>();
                mDatas.add("物业超市");
                mDatas.add("好货");
                info.setDataList(mDatas);
            } else {
                info.setHeader("智能硬件");
                mDatas = new ArrayList<>();
                mDatas.add("门禁");
                mDatas.add("智能家居");
                mDatas.add("车位锁");
                mDatas.add("智能安防");
                info.setDataList(mDatas);
            }
            list.add(info);
        }
        //上面数据如果显示列表比较复杂，故重新组装数据，分组标题和内容改成同级，提高页面加载性能
        childListBeans = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            MenuInfo menuInfo = list.get(i);
            childListBeans.add(new MenuInfo.ChildListBean(menuInfo.getHeader(), true));//true表示分组标题
            List<String> dataList = menuInfo.getDataList();
            for (int j = 0; j < dataList.size(); j++) {
                childListBeans.add(new MenuInfo.ChildListBean(dataList.get(j), false));//false表示分组内容
            }
        }
        initRecylerView();
    }

    /**
     * 初始化RecylerView
     */
    private void initRecylerView() {
        boolean showDataWay = true;//false原始数据，true显示加工数据
        mRecyclerViewAdapter = new RecyclerViewGroupingAdapter(this,showDataWay, list,childListBeans);
        //需求必须使用GridLayoutManager布局管理器
        manage = new GridLayoutManager(getApplicationContext(), 3, OrientationHelper.VERTICAL, false);
        // 默认所有的数据都在同一行，没有将头部和内容分开。
        // 所以要重写GridLayoutManager的setSpanSizeLookup方法，
        // 如果条目类型为标题，是则该数据占三列，否则占据一列。
        //GridLayoutManager为我们提供了动态改变每个item所占列数的方法
        manage.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //共3列，当显示标题时就让他显示一列（3/3），如果是child就让他显示1/3列
                int spanCount = manage.getSpanCount();
                return mRecyclerViewAdapter.getItemViewType(position) == RecyclerViewGroupingAdapter.TYPE_TITLE ? spanCount : 1;
            }
        });
        mRecyclerView.setLayoutManager(manage);
        //添加动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
//        mRecyclerView.addItemDecoration(new RefreshItemDecoration(this, RefreshItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(manage);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);


    }

}
