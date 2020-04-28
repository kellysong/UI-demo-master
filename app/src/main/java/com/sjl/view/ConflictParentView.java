package com.sjl.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * 掌握View的滑动冲突
 *
 * @author Kelly
 * @version 1.0.0
 * @filename ConflictParentView.java
 * @time 2018/9/30 10:45
 * @copyright(C) 2018 xxx有限公司
 */
public class ConflictParentView extends LinearLayout {

    private int mMove;
    private int yDown, yMove;
    private int i = 0;
    private boolean isIntercept = false;

    public ConflictParentView(Context context) {
        super(context);
    }

    public ConflictParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ConflictParentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ScrollView scrollView;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        scrollView = (ScrollView) getChildAt(0);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        onInterceptTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        int mScrollY = scrollView.getScrollY();


        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                yDown = y;
                break;
            case MotionEvent.ACTION_MOVE:
                yMove = y;
                if (yMove - yDown > 0  && mScrollY == 0) {
                    if (!isIntercept) {
                        yDown = (int) ev.getY();
                        isIntercept = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                layout(getLeft(), getTop() - i, getRight(), getBottom() - i);
                i = 0;
                isIntercept = false;
                break;
        }
        if (isIntercept) {
            mMove = yMove - yDown;
            i += mMove;
            layout(getLeft(), getTop() + mMove, getRight(), getBottom() + mMove);
        }
        Log.i("SIMPLE_LOGGER","mScrollY:"+mScrollY+",y:"+y+",isIntercept:"+isIntercept);

        return isIntercept;
    }
}
