package com.sjl.activity;


import android.widget.ListView;

import com.sjl.adapter.TitleListViewAdapter;
import com.sjl.entity.Data;
import com.sjl.entity.Type;
import com.sjl.uidemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现带标题的ListView
 */

public class TitleListViewActivity extends BaseActivity {
    private ListView listView;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_title_listview);
        listView = (ListView) findViewById(R.id.list);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        List<Type> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Type type = new Type("标题" + i);
            for (int j = 0; j < Math.random() * 8; j++) {
                Data data = new Data("数据1", "数据2", "数据3");
                type.addItem(data);
            }
            list.add(type);
        }
        TitleListViewAdapter adapter = new TitleListViewAdapter(this, list);
        listView.setAdapter(adapter);
    }


}
