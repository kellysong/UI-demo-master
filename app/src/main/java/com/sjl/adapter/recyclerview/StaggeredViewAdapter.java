

package com.sjl.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjl.uidemo.R;

import java.util.ArrayList;
import java.util.List;


public class StaggeredViewAdapter extends RecyclerView.Adapter<RecyclerView2Holder> {

  public interface OnItemClickListener {
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);
  }

  public OnItemClickListener mOnItemClickListener;

  public void setOnItemClickListener(OnItemClickListener listener) {
    this.mOnItemClickListener = listener;
  }

  public Context mContext;
  public List<String> mDatas;
  public List<Integer> mHeights;
  public LayoutInflater mLayoutInflater;

  public StaggeredViewAdapter(Context mContext) {
    this.mContext = mContext;
    mLayoutInflater = LayoutInflater.from(mContext);
    mDatas = new ArrayList<>();
    mHeights = new ArrayList<>();
    for (int i = 'A'; i <= 'Z'; i++) {
      mDatas.add((char) i + "");
    }
    for (int i = 0; i < mDatas.size(); i++) {
      mHeights.add((int) (Math.random() * 300) + 200);
    }
  }

  /**
   * 创建ViewHolder
   */
  @Override
  public RecyclerView2Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    View mView = mLayoutInflater.inflate(R.layout.recyclerview2_recycler_item, parent, false);
    RecyclerView2Holder mViewHolder = new RecyclerView2Holder(mView);
    return mViewHolder;
  }

  /**
   * 绑定ViewHoler，给item中的控件设置数据
   */
  @Override
  public void onBindViewHolder(final RecyclerView2Holder holder, final int position) {
    if (mOnItemClickListener != null) {
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mOnItemClickListener.onItemClick(holder.itemView, position);
        }
      });

      holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
          mOnItemClickListener.onItemLongClick(holder.itemView, position);
          return true;
        }
      });
    }

    ViewGroup.LayoutParams mLayoutParams = holder.mTextView.getLayoutParams();
    mLayoutParams.height = mHeights.get(position);
    holder.mTextView.setLayoutParams(mLayoutParams);
    holder.mTextView.setText(mDatas.get(position));
  }

  @Override
  public int getItemCount() {
    return mDatas.size();
  }
}
