package com.sjl.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.Toast;

import com.sjl.adapter.RecyclerViewAdapter;
import com.sjl.view.RefreshItemDecoration;
import com.sjl.uidemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * RecyclerView
 * Created by song on 2017/8/10.
 */

public class RecyclerViewActivity extends AppCompatActivity{
    @Bind(R.id.recyclerView)
    RecyclerView  mRecyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    List<String> mDatas = new ArrayList<>();
    private RecyclerViewAdapter mRecyclerViewAdapter; //适配器
    private LinearLayoutManager mLinearLayoutManager;//线性布局管理者
    private  boolean isHasMore = true; //true有更多，false没有更多
    private  int currentPage=1;
    private  int maxPage=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        setContentView(R.layout.activity_recyclerview);
        ButterKnife.bind(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLUE,Color.GREEN);

    }
    private void initListener() {
        initPullDownRefresh();
        initLoadMoreListener();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        initRecylerView();
    }

    /**
     * 初始化RecylerView
     */
    private void initRecylerView() {
        for (int i = 0; i < 10; i++) {

            mDatas.add(" Item "+i);
        }
        mRecyclerViewAdapter = new RecyclerViewAdapter(this,mDatas);
        mLinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        //添加动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        mRecyclerView.addItemDecoration(new RefreshItemDecoration(this, RefreshItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        if (!isSlideToBottom(mRecyclerView)){//到达底部，显示没有更多数据
            isHasMore = false;
            mRecyclerViewAdapter.changeMoreStatus(mRecyclerViewAdapter.NO_LOAD_MORE);
            return;
        }
    }

    /**
     * 判断RecyclerView是否滚动到底部
     * @param recyclerView
     * @return
     */
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        //computeVerticalScrollExtent()是当前屏幕显示的区域高度，
        // computeVerticalScrollOffset()，
        // 而computeVerticalScrollRange()是整个View控件的高度。
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    /**
     * 下拉刷新监听
     */
    private void initPullDownRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage=1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.clear();

                        List<String> headDatas = new ArrayList<String>();
                        for (int i = 0; i <10 ; i++) {

                            headDatas.add(" Item "+i);
                        }
                       mRecyclerViewAdapter.AddHeaderItem(headDatas);

                        //刷新完成
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (!isHasMore){
                            isHasMore=true;//恢复上拉加载
                        }
                        Toast.makeText(RecyclerViewActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });
    }


    /**
     * 上拉加载更多
     */
    private void initLoadMoreListener() {

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem ;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!isHasMore){//没有更多数据了
                    return;
                }

                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if(newState==RecyclerView.SCROLL_STATE_IDLE){
                    if (lastVisibleItem+1== mRecyclerViewAdapter.getItemCount()){
                        //设置正在加载更多
                        mRecyclerViewAdapter.changeMoreStatus(mRecyclerViewAdapter.LOADING_MORE);

                        //改为网络请求
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                List<String> footerDatas= requestServerData();
                                mRecyclerViewAdapter.AddFooterItem(footerDatas);
                                if (currentPage > maxPage){
                                    isHasMore = false;
                                    mRecyclerViewAdapter.changeMoreStatus(mRecyclerViewAdapter.NO_LOAD_MORE);
                                    return;
                                }
                                //设置回到上拉加载更多
                                mRecyclerViewAdapter.changeMoreStatus(mRecyclerViewAdapter.PULLUP_LOAD_MORE);
                                //没有加载更多了
                                //mRecyclerViewAdapter.changeMoreStatus(mRecyclerViewAdapter.NO_LOAD_MORE);
                                Toast.makeText(RecyclerViewActivity.this, "更新了 "+footerDatas.size()+" 条目数据", Toast.LENGTH_SHORT).show();
                            }
                        }, 1000);

                    }


                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //最后一个可见的ITEM
                lastVisibleItem=layoutManager.findLastVisibleItemPosition();
            }
        });

    }

    private  List<String> requestServerData(){
        List<String> footerDatas = new ArrayList<String>();
        for (int i = 0; i< 5; i++) {
            footerDatas.add("上拉加载第一" + currentPage+"页");
        }
        currentPage++;
        return  footerDatas;
    }
}
