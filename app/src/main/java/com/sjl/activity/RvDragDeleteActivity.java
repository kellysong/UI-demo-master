package com.sjl.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.sjl.adapter.MyAdapter;
import com.sjl.listener.SimpleItemTouchCallBack;
import com.sjl.uidemo.R;

import java.util.ArrayList;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename RvDragDeleteActivity.java
 * @time 2020/5/13 11:33
 * @copyright(C) 2020 song
 */
public class RvDragDeleteActivity extends BaseActivity {
    @Override
    protected void initView() {
        setContentView(R.layout.rv_drag_delete_activity);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        RecyclerView  lv = (RecyclerView) findViewById(R.id.rv);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list.add("Hello "+i);
        }
        MyAdapter adapter = new MyAdapter(this, list);
        lv.setAdapter(adapter);
        lv.setLayoutManager(new LinearLayoutManager(this));

        // 拖拽移动和左滑删除
        SimpleItemTouchCallBack simpleItemTouchCallBack = new SimpleItemTouchCallBack(adapter);
        // 要实现侧滑删除条目，把 false 改成 true 就可以了
        simpleItemTouchCallBack.setmSwipeEnable(false);
        ItemTouchHelper helper = new ItemTouchHelper(simpleItemTouchCallBack);
        helper.attachToRecyclerView(lv);
    }
}
