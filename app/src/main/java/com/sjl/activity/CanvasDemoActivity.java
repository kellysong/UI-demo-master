package com.sjl.activity;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sjl.uidemo.R;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename CanvasDemoActivity.java
 * @time 2018/7/12 8:38
 * @copyright(C) 2018 深圳市北辰德科技股份有限公司
 */
public class CanvasDemoActivity extends BaseActivity {
    @Override
    protected void initView() {
        setContentView(R.layout.canvas_demo_activity);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    public static class CanvasView extends View {
        private Paint paint;

        public CanvasView(Context context) {
            super(context);
            init();
        }

        /**
         * xml布局要这个构造器
         *
         * @param context
         * @param attrs
         */
        public CanvasView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            init();
        }


        private void init() {
            paint = new Paint(); //设置一个大小是3的黄色的画笔
            paint.setColor(Color.YELLOW);
            System.out.println("init:" + paint.getColor());
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(3);
        }

        @Override
        protected void onDraw(Canvas canvas) {
//            super.onDraw(canvas);
            paint.setColor(Color.YELLOW);
            System.out.println("onDraw:" + paint.getColor());
            canvas.drawColor(Color.WHITE);
            //1.绘制圆形
            canvas.drawCircle(150, 150, 90, paint);

            //2.画一条线
            paint.setColor(Color.BLUE);
            canvas.drawLine(300, 400, 500, 500, paint);

            //3.绘制矩形区域内切椭圆,定义一个矩形区域
            paint.setColor(Color.BLACK);
            RectF oval = new RectF(0, 0, 100, 200);
            canvas.drawOval(oval, paint);


            //4.绘制矩形
            paint.setColor(Color.BLUE);
            RectF rect = new RectF(0, 0, 50, 50);
            canvas.drawRect(rect, paint);


            //5.使用path绘制三角形
            paint.setColor(Color.GRAY);
            Path path = new Path();
            path.moveTo(10, 10);//moveTo是用来移动画笔,把画笔移动(10,10)处开始绘制
            path.lineTo(50, 60);//lineTo 用于进行直线绘制
            path.lineTo(200, 80);
            path.lineTo(10, 10);
            path.close();
            canvas.drawPath(path, paint);


            paint.setColor(Color.argb(50, 255, 100, 100));
            canvas.drawRect(0, 0, 200, 200, paint); // 以原始Canvas画出一个矩形1

            canvas.translate(300, 300); // 将Canvas平移  (100,100)
            paint.setColor(Color.argb(50, 100, 255, 100));
            canvas.drawRect(0, 0, 200, 200, paint); //  矩形2

            canvas.rotate(30); //将Canvas旋转30
            paint.setColor(Color.argb(50, 100, 0, 255));
            canvas.drawRect(0, 0, 200, 200, paint); // 矩形3

            canvas.scale(2, 2); // 将Canvas以原点为中心，放大两倍
            paint.setColor(Color.argb(50, 255, 255, 0));
            canvas.drawRect(0, 0, 200, 200, paint); // 矩形4

            canvas.save();

        }
    }
}
