package com.sjl.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 网页浏览记录映射
 *
 * @author Kelly
 * @version 1.0.0
 * @filename BrowerMapper.java
 * @time 2018/4/1 9:49
 * @copyright(C) 2018 深圳市北辰德科技股份有限公司
 */
public class BrowerMapper {
    private static Map<String, Integer> urls = new LinkedHashMap<String, Integer>();


    /**
     * 缓存当前网页记录点
     * @param url
     * @param progress
     */
    public static void put(String url, Integer progress) {
        urls.put(url, progress);
    }

    /**
     * 取出网页记录点
     * @param url
     * @return
     */
    public static int get(String url) {
        Integer integer = urls.get(url);
        if (integer == null) {
            return 0;
        } else {
            return integer;
        }
    }

    /**
     * 清空
     */
    public static void clearAll() {
        urls.clear();
    }
}
