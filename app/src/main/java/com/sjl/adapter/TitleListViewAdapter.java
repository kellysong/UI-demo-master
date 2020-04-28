package com.sjl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sjl.entity.Data;
import com.sjl.entity.Type;
import com.sjl.uidemo.R;

import java.util.List;

/**
 * 多条目listview
 *
 * @author Kelly
 * @version 1.0.0
 * @filename TitleListViewAdapter.java
 * @time 2018/2/4 14:48
 * @copyright(C) 2018 深圳市北辰德科技股份有限公司
 */
public class TitleListViewAdapter extends BaseAdapter {
    private static final int TYPE_HEADER = 0;  //代表标题
    private static final int TYPE_ITEM = 1;    //代表项目item


    private List<Type> mList;
    private LayoutInflater inflater;

    public TitleListViewAdapter(Context context, List<Type> list) {
        mList = list;
        inflater = LayoutInflater.from(context);
    }

    /**
     *
     * @return 所有项的总和
     */
    @Override
    public int getCount() {
        int count = 0;
        if (mList != null) {
            for (Type type : mList) {
                count += type.size();
            }
        }
        return count;
    }

    /**
     * 根据position的不同返回不同的值
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        int head = 0;  //标题位置
        for (Type type : mList) {
            int size = type.size();
            int current = position - head;
            if (current < size) {
                //返回对应位置的值
                return type.getItem(current);
            }
            head += size;
        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        switch (getItemViewType(position)) {
            //分为两种情况加载item
            case TYPE_HEADER: //加载标题布局
                if (convertView == null) {
                    viewHolder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.title_header, parent, false);
                    viewHolder.title = (TextView) convertView.findViewById(R.id.date);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.title.setText((CharSequence) getItem(position));
                break;
            case TYPE_ITEM: //加载数据项目布局
                if (convertView == null) {
                    viewHolder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.title_listview, parent, false);
                    viewHolder.tv1 = (TextView) convertView.findViewById(R.id.text1);
                    viewHolder.tv2 = (TextView) convertView.findViewById(R.id.text2);
                    viewHolder.tv3 = (TextView) convertView.findViewById(R.id.text3);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                Data data = (Data) getItem(position);
                viewHolder.tv1.setText(data.getText1());
                viewHolder.tv2.setText(data.getText2());
                viewHolder.tv3.setText(data.getText3());
                break;
        }

        return convertView;
    }

    /**
     *
     * @return 返回item类型数目
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * 获取当前item的类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int head = 0;
        for (Type type : mList) {
            int size = type.size();
            int current = position - head;
            if (current == 0) {
                return TYPE_HEADER;
            }
            head += size;
        }
        return TYPE_ITEM;
    }

    /**
     * 判断当前的item是否可以点击
     * @param position
     * @return
     */
    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) != TYPE_HEADER;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    private class ViewHolder {
        TextView tv1, tv2, tv3, title;
    }
}
