package com.sjl.activity;


import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sjl.uidemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * android：listview显示table效果，并有固定不动的表头
 */

public class TableListViewActivity extends BaseActivity {
    /**
     * 当前分辨率
     */
    private DisplayMetrics dm;
    private int w;
    private TextView tv1, tv2, tv3, tv4, tv5;

    private ListView listview;
    private mBaseAdapter mAdapter;
    private LayoutInflater mInflater1;
    private ListViewHander hander;

    private List<Map<String, String>> listDatas;
    private Map<String, String> map;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_table_listview);
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        w = dm.widthPixels / 15; // 当前分辨率 宽度 分为15等份

        tv1 = (TextView) findViewById(R.id.textView1);
        tv1.setText("一");

        tv2 = (TextView) findViewById(R.id.textView2);
        tv2.setText("二");

        tv3 = (TextView) findViewById(R.id.textView3);
        tv3.setText("三");

        tv4 = (TextView) findViewById(R.id.textView4);
        tv4.setText("四");


        tv5 = (TextView) findViewById(R.id.textView5);
        tv5.setText("五");


        tv1.setWidth(w * 3);        //为每个表头文本框设置宽度，可根据实际情况 进行自我调整
        tv2.setWidth(w * 3);
        tv3.setWidth(w * 3);
        tv4.setWidth(w * 3);
        tv5.setWidth(w * 3);

        initDatas();

        listview = (ListView) findViewById(R.id.listView1);  //
        mAdapter = new mBaseAdapter();
        listview.setAdapter(mAdapter);  //为listView添加适配器

        mInflater1 = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    //初始化listview要用到的数据
    public void initDatas() {
        listDatas = new ArrayList<Map<String, String>>();
        for (int j = 0; j < 50; j++) {
            map = new HashMap<String, String>();
            map.put("test1", "" + j);
            map.put("test2", "" + j);
            map.put("test3", "" + j);
            map.put("test4", "" + j);
            map.put("test5", "" + j);
            listDatas.add(map);
        }
    }

    //为适配器要用到的控件对象创建类
    private class ListViewHander {
        TextView textview1;
        TextView textview2;
        TextView textview3;
        TextView textview4;
        TextView textview5;
    }

    private class mBaseAdapter extends BaseAdapter {         //继承BaseAdapter类，并重写方法
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return listDatas.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View contentView, ViewGroup arg2) {
            // TODO Auto-generated method stub
            if (contentView == null) {
                contentView = mInflater1.inflate(R.layout.table_listview_title, null);  //使用表头布局test1.xml
                hander = new ListViewHander();   //与表头的文本控件一一对应
                hander.textview1 = (TextView) contentView.findViewById(R.id.textView1);
                hander.textview2 = (TextView) contentView.findViewById(R.id.textView2);
                hander.textview3 = (TextView) contentView.findViewById(R.id.textView3);
                hander.textview4 = (TextView) contentView.findViewById(R.id.textView4);
                hander.textview5 = (TextView) contentView.findViewById(R.id.textView5);

                contentView.setTag(hander);

                hander.textview1.setWidth(w * 3); //记住，这里的宽度设置必须和表头文本宽度一致
                hander.textview2.setWidth(w * 3);
                hander.textview3.setWidth(w * 3);
                hander.textview4.setWidth(w * 3);
                hander.textview5.setWidth(w * 3);

                hander.textview1.setText("");
                hander.textview2.setText("");
                hander.textview3.setText("");
                hander.textview4.setText("");
                hander.textview5.setText("");

            } else {
                hander = (ListViewHander) contentView.getTag();
            }

            //为listview中的TextView布局控件添加内容
            for (int j = 0; j < listDatas.size(); j++) {
                if (position == j) {

                    map = listDatas.get(position);
                    hander.textview1.setText("" + map.get("test1"));
                    hander.textview2.setText("" + map.get("test2"));
                    hander.textview3.setText("" + map.get("test3"));
                    hander.textview4.setText("" + map.get("test4"));
                    hander.textview5.setText("" + map.get("test5"));
                }
                // 设置隔行颜色
                if (position % 2 != 0) {
                    contentView.setBackgroundColor(Color.GRAY);
                } else {
                    contentView.setBackgroundColor(Color.LTGRAY);
                }
            }

            return contentView;
        }

    }

}
