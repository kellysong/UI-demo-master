package com.sjl.entity;

/**
 * 数据项的模型
 *
 * @author Kelly
 * @version 1.0.0
 * @filename Data.java
 * @time 2018/2/4 14:46
 * @copyright(C) 2018 深圳市北辰德科技股份有限公司
 */
public class Data {
    private String text1, text2, text3; //数据1 2 3

    public Data(String text1, String text2, String text3) {
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
    }
    public String getText1() {
        return text1;
    }
    public String getText2() {
        return text2;
    }
    public String getText3() {
        return text3;
    }
}
