package com.sjl.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 存标题和数据Data集合
 *
 * @author Kelly
 * @version 1.0.0
 * @filename Type.java
 * @time 2018/2/4 14:46
 * @copyright(C) 2018 深圳市北辰德科技股份有限公司
 */
public class Type {
    private String title;  //ListView头部显示的标题
    private List<Data> mList; //头部对应的内容集合

    public Type(String title) {
        this.title = title;
        mList = new ArrayList<>();
    }


    /**
     * 添加项目
     * @param data Data对象
     */
    public void addItem(Data data) {
        mList.add(data);
    }

    /**
     * 获取项目
     * @param position 如果position为1就返回标题
     * @return
     */
    public Object getItem(int position) {
        if (position == 0) {
            return title;
        } else {
            return mList.get(position - 1);
        }
    }

    /**
     * @return item数目，为集合大小+1
     */
    public int size() {
        return mList.size() + 1;
    }
}
