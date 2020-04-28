package com.sjl.activity;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ScrollView;

import com.sjl.util.BrowerMapper;
import com.sjl.view.MyScrollView;
import com.sjl.view.MyWebView;
import com.sjl.uidemo.R;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename ScrollWebViewActivity.java
 * @time 2018/7/9 15:55
 * @copyright(C) 2018 深圳市北辰德科技股份有限公司
 */
public class ScrollWebViewActivity extends BaseActivity implements MyWebView.MyWebViewListener {

    MyWebView mWebView;
    GestureDetector mGesture = null;

    View mToolBar;
    int mToolBarHeight;
    MyScrollView mScrollView;
    int mScrollViewHeight;
    int mScrollOffset;
    EditText mUrlEdit;
    String mUrl;

    @Override
    protected void initView() {
        setContentView(R.layout.scroll_webview_activity);
        mWebView = (MyWebView) findViewById(R.id.webView);
        mScrollView = (MyScrollView) findViewById(R.id.scrollView);
        mUrlEdit = (EditText) findViewById(R.id.urlEdit);
        mToolBar = findViewById(R.id.toolBar);

    }

    @Override
    protected void initListener() {
        mWebView.setListener(this);
        findViewById(R.id.goButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mUrlEdit.getText().toString();
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
                mWebView.loadUrl(url);
            }
        });
        findViewById(R.id.root).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mToolBarHeight = mToolBar.getHeight();
                mScrollViewHeight = mScrollView.getHeight();
                adjustScrollView();
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                int position = BrowerMapper.get(mUrl);
                if(position > 0){
                    mWebView.scrollTo(0, position);//webview加载完成后直接定位到上次访问的位置
                }
            }
        });



    }

    @Override
    protected void initData() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        mGesture = new GestureDetector(this, new GestureListener());
        mUrl = "http://www.sohu.com";
        mWebView.loadUrl(mUrl);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mGesture.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void afterDraw(WebView webView) {
        if (mWebView.getVScrollRange() < mScrollOffset) {
            mScrollOffset = mWebView.getVScrollRange();
            adjustScrollView();
        }
    }


    class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.e("Temp", "onDoubleTap");
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.e("Temp", "onDown");
            return super.onDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            Log.e("Temp", "onFling:velocityX = " + velocityX + " velocityY" + velocityY);

            int effectX = (int) velocityX;
            int effectY = (int) velocityY;

            if (effectOnScrollViewByScroll((velocityY < 0 ? 1 : -1) * 8000)) {
                effectY = 0;
            }
            mWebView.flingScroll(-effectX, -effectY);

            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.e("Temp", "onLongPress");
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            Log.e("Temp", "onScroll:distanceX = " + distanceX + " distanceY = " + distanceY);

            int effectX = (int) distanceX;
            int effectY = (int) distanceY;
            if (effectOnScrollViewByScroll((int) distanceY)) {
                effectY = 0;
            }
            mWebView.scrollBy(effectX, effectY);

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e("Temp", "onSingleTapUp");
            return super.onSingleTapUp(e);
        }
    }

    private boolean effectOnScrollViewByScroll(int distanceY) {
        if (distanceY > 0) {
            // scroll up, the web will scroll down
            if (mScrollOffset >= mToolBarHeight || mScrollOffset >= mWebView.getVScrollRange()) {
                return false;
            }

            mScrollOffset += distanceY;
            if (mScrollOffset > mToolBarHeight) {
                mScrollOffset = mToolBarHeight;
            }
            if (mScrollOffset > mWebView.getVScrollRange()) {
                mScrollOffset = mWebView.getVScrollRange();
            }

        } else {
            if (mScrollOffset <= 0) {
                return false;
            }

            mScrollOffset += distanceY;
            if (mScrollOffset <= 0) {
                mScrollOffset = 0;
            }
        }

        adjustScrollView();
        return true;
    }

    private void adjustScrollView() {
        Log.e("Temp", "offset is " + mScrollOffset);
        ViewGroup.LayoutParams layoutParams = mWebView.getLayoutParams();
        int newHeight = (mScrollViewHeight - mToolBarHeight) + mScrollOffset;
        Log.e("Temp", "newHeight is " + newHeight + ", layoutParams.height" + layoutParams.height);
        if (newHeight != layoutParams.height) {
            layoutParams.height = newHeight;
            mWebView.setLayoutParams(layoutParams);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) {
            int scrollY = mWebView.getScrollY();
            BrowerMapper.put(mUrl, scrollY);//保存访问的位置
        }
    }
}
