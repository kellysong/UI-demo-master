
package com.sjl.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sjl.uidemo.R;


public class RecyclerView2Holder extends RecyclerView.ViewHolder {

  public TextView mTextView;

  public RecyclerView2Holder(View itemView) {
    super(itemView);
    mTextView = (TextView) itemView.findViewById(R.id.id_textview);
  }
}
