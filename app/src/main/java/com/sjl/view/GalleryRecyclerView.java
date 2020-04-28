package com.sjl.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.sjl.listener.GalleryScrollListener;

/**
 * 自定义画廊RecyclerView
 * Created by song on 2017/8/31.
 */

public class GalleryRecyclerView extends RecyclerView{
    /**
     * 记录当前第一个View
     */
    private View mCurrentView;

    private GalleryRecyclerView.OnItemScrollChangeListener mItemScrollChangeListener;

    private GalleryScrollListener galleryScrollListener;//滚动监听

    public void setOnItemScrollChangeListener(OnItemScrollChangeListener mItemScrollChangeListener) {
        this.mItemScrollChangeListener = mItemScrollChangeListener;
        galleryScrollListener = new GalleryScrollListener(this.mItemScrollChangeListener);
       // this.setOnScrollListener(galleryScrollListener);//初始化滚动监听
        addOnScrollListener(galleryScrollListener);//初始化滚动监听
    }

    public interface OnItemScrollChangeListener {
        void onChange(View view, int position);
    }

    public GalleryRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        mCurrentView = getChildAt(0);
       if (galleryScrollListener != null){
           galleryScrollListener.setScrollCurrentView(mCurrentView);
       }
        if (mItemScrollChangeListener != null) {
            mItemScrollChangeListener.onChange(mCurrentView, getChildAdapterPosition(mCurrentView));
        }
    }
}
