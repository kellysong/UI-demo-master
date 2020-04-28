package com.sjl.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * onTouchEvent 中阻止 MotionEvent.ACTION_MOVE 事件

 onDrawListner

 计算竖直滚动范围

 public class MyWeb
 *
 * @author Kelly
 * @version 1.0.0
 * @filename MyWebView.java
 * @time 2018/7/9 16:00
 * @copyright(C) 2018 深圳市北辰德科技股份有限公司
 */
public class MyWebView extends WebView {
    public interface MyWebViewListener {
        void afterDraw(WebView webView);
    }

    private MyWebViewListener mListener;
    private int mMoveCheckedCnt;

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    public void setListener(MyWebViewListener listener) {
        mListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mMoveCheckedCnt = 0;
                flingScroll(0, 0);
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveCheckedCnt++;
                return false;
            case MotionEvent.ACTION_UP:
                if (mMoveCheckedCnt >= 2) {
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    mMoveCheckedCnt = 0;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        MyWebViewListener listener = mListener;
        if (listener != null) {
            listener.afterDraw(this);
        }
    }

    public int getVScrollRange() {
        int v = computeVerticalScrollRange() - computeVerticalScrollExtent();
        if (v < 0) {
            v = 0;
        }
        return v;
    }
}