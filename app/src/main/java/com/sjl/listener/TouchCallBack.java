package com.sjl.listener;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename TouchCallBack.java
 * @time 2020/5/13 11:34
 * @copyright(C) 2020 song
 */
public interface TouchCallBack {
    //数据交换
    void onItemMove(int fromPosition, int toPosition);

    //数据删除
    void onItemDelete(int position);
}