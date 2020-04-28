package com.sjl.listener;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;

import com.sjl.view.GalleryRecyclerView;

/**
 * 画廊滚动监听
 * Created by song on 2017/9/1.
 */
public class GalleryScrollListener extends OnScrollListener{

    private View mCurrentView;

    private GalleryRecyclerView.OnItemScrollChangeListener mItemScrollChangeListener;

    public GalleryScrollListener(GalleryRecyclerView.OnItemScrollChangeListener mItemScrollChangeListener) {
        this.mItemScrollChangeListener = mItemScrollChangeListener;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

    }

    /**
     *
     * 滚动时，判断当前第一个View是否发生变化，发生才回调
     */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        View newView = recyclerView.getChildAt(0);
        if (mItemScrollChangeListener != null) {
            if (newView != null && newView != mCurrentView) {
                mCurrentView = newView;
                mItemScrollChangeListener.onChange(mCurrentView,recyclerView.getChildAdapterPosition(mCurrentView));
            }
        }
    }

    /**
     * 设置当前滚动的view
     * @param scrollCurrentView
     */
    public void setScrollCurrentView(View scrollCurrentView) {
        this.mCurrentView = scrollCurrentView;
    }
}
