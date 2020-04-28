package com.sjl.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Android关键字高亮、关键字背景、是否忽略大小写的相关处理
 *
 * @author Kelly
 * @version 1.0.0
 * @filename HighLightKeyWordUtils.java
 * @time 2019/10/15 14:40
 * @copyright(C) 2019 song
 */
public class HighLightKeyWordUtils {
    /**
     * @param color 关键字颜色
     * @param text 文本
     * @param keyword 关键字
     * @return
     */
    public static SpannableString getHighLightKeyWord(int color, String text, String keyword, boolean ignoreCase) {
        SpannableString s = new SpannableString(text);
        String wordReg = keyword;
        if (ignoreCase){
            wordReg = "(?i)"+ keyword;    ///< 用(?i)来忽略大小写
        }
        Pattern p = Pattern.compile(wordReg);
        Matcher m = p.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(color), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }

    /**
     * @param color 关键字颜色
     * @param text 文本
     * @param keyword 多个关键字数组
     * @return
     */
    public static SpannableString getHighLightKeyWord(int color, String text,String[] keyword, boolean ignoreCase) {
        SpannableString s = new SpannableString(text);
        for (int i = 0; i < keyword.length; i++) {
            String wordReg = keyword[i];
            if (ignoreCase){
                wordReg = "(?i)"+ keyword[i];    ///< 用(?i)来忽略大小写
            }
            Pattern p = Pattern.compile(wordReg);
            Matcher m = p.matcher(s);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new ForegroundColorSpan(color), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return s;
    }

    /**
     * @param color 关键字背景颜色
     * @param text 文本
     * @param keyword 关键字
     * @return
     */
    public static SpannableString getBackgroudKeyWord(int tvcolor, int color, String text, String keyword) {
        SpannableString s = new SpannableString(text);
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(s);
        //while (m.find()) {
        if (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new RoundBackgroundColorSpan(color, tvcolor, 10), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }

    /**
     *
     * @param tvcolor 关键字颜色
     * @param color   关键字背景颜色
     * @param text    文本
     * @param keywords 多个关键字数组
     * @return
     */
    public static SpannableString getBackgroudKeyWord(int tvcolor, int color, String text, String[] keywords) {
        SpannableString s = new SpannableString(text);
        for (int i = 0; i < keywords.length; i++) {
            Pattern p = Pattern.compile(keywords[i]);
            Matcher m = p.matcher(s);
            //while (m.find()) {
            if (m.find()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new RoundBackgroundColorSpan(color, tvcolor, 10), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return s;
    }

    /**
     *
     * @param tvcolor 关键字颜色数组
     * @param color 关键字背景颜色数组
     * @param s SpannableString
     * @param keywords 多个关键字数组
     * @return
     */
    public static SpannableString getBackgroudKeyWord(int[] tvcolor, int[] color, SpannableString s, String[] keywords) {
        for (int i = 0; i < keywords.length; i++) {

            Pattern p = Pattern.compile(keywords[i]);
            Matcher m = p.matcher(s);
            ///< 只找开头的，标题中的不找
            //while (m.find()) {
            //if (m.find()) {
            if (m.find() && m.start() < s.length()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new RoundBackgroundColorSpan(color[i], tvcolor[i], 10), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return s;
    }

    /**
     *
     * @param tvcolor 关键字颜色数组
     * @param color  关键字背景颜色数组
     * @param text   文本
     * @param keywords 多个关键字数组
     * @return
     */
    public static SpannableString getBackgroudKeyWord(int[] tvcolor, int[] color, String text, String[] keywords) {
        SpannableString s = new SpannableString(text);

        for (int i = 0; i < keywords.length; i++) {
            Pattern p = Pattern.compile(keywords[i]);
            Matcher m = p.matcher(s);
            ///< 只找开头的，标题中的不找
            //while (m.find()) {
            //if (m.find()) {
            if (m.find() && m.start() < s.length()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new RoundBackgroundColorSpan(color[i], tvcolor[i], 10), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return s;
    }
}
