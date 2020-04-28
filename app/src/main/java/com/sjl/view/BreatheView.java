package com.sjl.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.sjl.uidemo.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename BreatheView.java
 * @time 2019/10/12 11:40
 * @copyright(C) 2019 song
 */
public class BreatheView extends View {
    /* 官方文档：
          https://developer.android.google.cn/reference/android/graphics/Canvas
          https://developer.android.google.cn/reference/android/graphics/Paint
      */
    private Context context;///< 上下文
    private Canvas canvas;  ///< 画布
    private Paint paint;    ///< 画笔

    ///< 做红色点击区域限制
    private boolean bIsDownInRedRegion = false;
    ///< 定时刷新
    private Timer timer = null;

    ///< 圆圈半径
    private int radius;
    ///< 圆圈颜色
    private String color;
    ///< 控件宽度和高度
    private int width;
    private int height;


    /**
     * 刷新绘制+增量变化
     */
    private static final int STEP_RADIUS = 10;  ///< 每次半径增加10
    private int changeRadius = 0;               ///< 变化量记录，达到50时则开始减；达到0就开始增加
    private boolean addFlag = true;             ///< 标记是否增加增量

    public BreatheView(Context context) {
        this(context, null);
    }

    public BreatheView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);

        ///< TypedArray的方式
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BreathView);
        ///< getDimension() getDimensionPixelOffset() getDimensionPixelSize()
        ///  --这三个方法都是根据DisplayMetrics获取相应的值，不同在于方法1直接保存float型数据，方法2直接对float取整，方法3对float小数先四舍五入后取整。
        radius = ta.getDimensionPixelOffset(R.styleable.BreathView_bv_radius, 50);
        color = ta.getString(R.styleable.BreathView_bv_color);
        width = ta.getDimensionPixelOffset(R.styleable.BreathView_android_layout_width, 200);
        height = ta.getDimensionPixelOffset(R.styleable.BreathView_android_layout_height, 200);

        ///< 做一个兼容，如果半径超过了控件宽或者高
        int minWH = width;
        if (width > height){
            minWH = height;
        }
        if ((radius * 2) > minWH){
            radius = minWH / 2;
        }

        Log.e("attrs", "像素值 radius= " + radius);
        Log.e("attrs", "color= " + color);
        Log.e("attrs", "width= " + width);
        Log.e("attrs", "height= " + height);
        ta.recycle();
        
        this.context = context;
        ///< 1. 做一些绘制初始化
        canvas = new Canvas();  ///< 也可以指定绘制到Bitmap上面 -> Canvas(Bitmap bitmap)
        paint = new Paint();
        paint.setColor(Color.parseColor(color));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        ///< 2.进行绘制
        ///< 绘制一个圆圈吧-> drawCircle(float cx, float cy, float radius, Paint paint)
        canvas.drawCircle(width/2, height/2,
                radius + changeRadius, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("test", "getX=" + event.getX());
        Log.e("test", "getY=" + event.getY());
        //Log.e("test", "getRawX=" + event.getRawX());
        //Log.e("test", "getRawY=" + event.getRawY());

        int x = (int) event.getX();
        int y = (int) event.getY();

        ///< 控件大小是： dp2px(context, 200) * dp2px(context, 200)
        ///< 圆心坐标是:  dp2px(context, 100) * dp2px(context, 100)
        ///< 圆半径是:    dp2px(context, 50)
        ///< 所以点击区域就是左上角范围(dp2px(context, 50), dp2px(context, 50))
        ///<                右下角范围:(dp2px(context, 150), dp2px(context, 150))
      /*  int min = dp2px(context, 50);
        int max = dp2px(context, 150);


        Log.e("test", "x=" + x);
        Log.e("test", "y="+ y);
        Log.e("test", "min=" + min);
        Log.e("test", "max="+ max);
        Log.e("test", "event.getAction()=" + event.getAction());*/

        int minX = (width - radius * 2)/2;
        int maxX = width/2 + radius;
        int minY = (height - radius * 2)/2;
        int maxY = height/2 + radius;
        Log.e("test", "x=" + x);
        Log.e("test", "y="+ y);
        Log.e("test", "minX=" + minX);
        Log.e("test", "maxX="+ maxX);
        Log.e("test", "minY=" + minY);
        Log.e("test", "maxY="+ maxY);
        Log.e("test", "event.getAction()=" + event.getAction());

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (x >= minX && x <= maxX &&
                        y >= minY && y <= maxY){
                    bIsDownInRedRegion = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (bIsDownInRedRegion){
                    bIsDownInRedRegion = false;

                    if (x >= minX && x <= maxX &&
                            y >= minY && y <= maxY){
                        ///< 抬手时我们就可以启动定时器进行绘制刷新了
                        Log.e("test", "红色区域点击了呀，sb");
                        if (null == timer){
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    ///< Handler也行
                                    ((Activity)context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            updateDraw();
                                        }
                                    });
                                }
                            }, 0, 80);
                        }else{
                            timer.cancel();
                            timer = null;
                        }
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 刷新绘制+增量变化
     */
    private void updateDraw(){
        changeRadius = addFlag ? (changeRadius += STEP_RADIUS) : (changeRadius -= STEP_RADIUS);
        if (changeRadius > 50){
            addFlag = false;
        }else if (changeRadius < 0){
            addFlag = true;
        }
        invalidate();
    }

    /**
     * dp转px
     * @param dp
     * @return
     */
    public static int dp2px(Context context, int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
