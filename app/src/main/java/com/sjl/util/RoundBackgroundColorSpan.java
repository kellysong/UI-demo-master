package com.sjl.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;


/*
 *@Description: 字体添加圆角背景
 *@Author: hl
 *@Time: 2018/7/10 11:34
 */
public class RoundBackgroundColorSpan extends ReplacementSpan {
    private int bgColor;
    private int textColor;
    private int mSize;
    private int radius;

    public RoundBackgroundColorSpan(int bgColor, int textColor, int radius) {
        super();
        this.bgColor = bgColor;
        this.textColor = textColor;
        this.radius = radius;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        //设置宽度为文字宽度加16dp
        return (mSize = (int) (paint.measureText(text, start, end) + 2 * radius));
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        int originalColor = paint.getColor();
        paint.setColor(this.bgColor);
        paint.setAntiAlias(true);// 设置画笔的锯齿效果
        //画圆角矩形背景
        RectF oval = new RectF(
                x > 5 ? (x - 5) : x,
                (y + paint.ascent()) > 5 ? ((y + paint.ascent()) - 5) : (y + paint.ascent()),
                x + mSize + 5,
                y + paint.descent() + 5);
        //设置文字背景矩形，x为span其实左上角相对整个TextView的x值，y为span左上角相对整个View的y值。paint.ascent()获得文字上边缘，paint.descent()获得文字下边缘
        canvas.drawRoundRect(oval, radius, radius, paint);//绘制圆角矩形，第二个参数是x半径，第三个参数是y半径
        paint.setColor(this.textColor);
        //画文字
        canvas.drawText(text, start, end, x + radius, y, paint);
        //将paint复原
        paint.setColor(originalColor);
    }
}
