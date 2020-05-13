package com.sjl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjl.listener.TouchCallBack;
import com.sjl.uidemo.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename MyAdapter.java
 * @time 2020/5/13 11:34
 * @copyright(C) 2020 song
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements TouchCallBack {
    private Context context;
    private ArrayList<String> list;

    public MyAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String str = list.get(i);
        viewHolder.tv.setText(str);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //交换位置
        Collections.swap(list, fromPosition, toPosition);
        //局部刷新(移动)
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDelete(int position) {
        //删除数据
        list.remove(position);
        //局部刷新(移除)
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_msg);
        }
    }
}
