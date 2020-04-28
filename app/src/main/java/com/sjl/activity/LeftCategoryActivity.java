package com.sjl.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sjl.uidemo.R;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename LeftCategoryActivity.java
 * @time 2018/3/23 9:02
 * @copyright(C) 2018 深圳市北辰德科技股份有限公司
 */
public class LeftCategoryActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private String[] strs = {"常用分类", "服饰内衣", "鞋靴", "手机", "家用电器", "数码", "电脑办公",
            "个护化妆", "图书"};
    private ListView listView;
    private LeftCategoryAdapter adapter;
    private LeftCategoryFragment myFragment;
    public static int mPosition;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_left_category);
        listView = (ListView) findViewById(R.id.listview);
    }

    @Override
    protected void initListener() {
        listView = (ListView) findViewById(R.id.listview);
    }

    @Override
    protected void initData() {
        adapter = new LeftCategoryAdapter(this, strs);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        //创建MyFragment对象
        myFragment = new LeftCategoryFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, myFragment);
        //通过bundle传值给MyFragment
        Bundle bundle = new Bundle();
        bundle.putString(LeftCategoryFragment.TAG, strs[mPosition]);
        myFragment.setArguments(bundle);
        fragmentTransaction.commit();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //拿到当前位置
        mPosition = position;
        //即使刷新adapter
        adapter.notifyDataSetChanged();

        myFragment = new LeftCategoryFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, myFragment);
        Bundle bundle = new Bundle();
        bundle.putString(LeftCategoryFragment.TAG, strs[position]);
        myFragment.setArguments(bundle);
        fragmentTransaction.commit();
    }

    public class LeftCategoryAdapter extends BaseAdapter {

        private Context context;
        private String[] strings;

        public LeftCategoryAdapter(Context context, String[] strings) {
            this.context = context;
            this.strings = strings;
        }

        @Override
        public int getCount() {
            return strings.length;
        }

        @Override
        public Object getItem(int position) {
            return strings[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_leftcategory, parent,false);
            TextView tv = (TextView) convertView.findViewById(R.id.tv_category);
            tv.setText(strings[position]);
            if (position == mPosition) {
                convertView.setBackgroundResource(R.mipmap.red_bg);
            } else {
                convertView.setBackgroundColor(Color.parseColor("#f4f4f4"));
            }
            return convertView;
        }
    }

    public class LeftCategoryFragment extends Fragment {

        public static final String TAG = "MyFragment";
        private String str;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            TextView tv_title = new TextView(getContext());
            //得到数据
            str = getArguments().getString(TAG);
            tv_title.setText(str);
            return tv_title;
        }
    }
}
