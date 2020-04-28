package com.sjl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjl.entity.TimeLine;
import com.sjl.uidemo.R;

import java.util.List;

/**
 * Created by song on 2017/8/25.
 */

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.HorizontalVh> {
    private Context context;
    private List<TimeLine> mTimeLines;
    //当前到达节点
    private int currentNode = 1;

    public TimeLineAdapter(Context context, List<TimeLine> mTimeLines) {
        this.context = context;
        this.mTimeLines = mTimeLines;
    }

    @Override
    public HorizontalVh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_horizontal_timeline, null, false);
        HorizontalVh vh = new HorizontalVh(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(HorizontalVh holder, int position) {
        TimeLine timeLine= mTimeLines.get(position);
        holder.desc.setText(timeLine.getDesc());
        holder.time.setText(timeLine.getTime());
        if (position < currentNode) {
            //当前节点之前的全部设为已经经过
            holder.point.setImageResource(R.drawable.point_shape_blue);
            holder.lineLeft.setBackgroundResource(R.color.primary);
            holder.lineRight.setBackgroundResource(R.color.primary);
        }

        // 去掉左右两头的分支
        if (position == 0) {
            holder.lineLeft.setVisibility(View.INVISIBLE);
        }
        if (position == mTimeLines.size() - 1) {
            holder.lineRight.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mTimeLines.size();
    }

    /**
     * 设置当前节点
     * @param currentNode
     */
    public void setCurrentNode(int currentNode) {
        this.currentNode = currentNode;
        this.notifyDataSetChanged();
    }

    class HorizontalVh extends RecyclerView.ViewHolder {
        private ImageView point;

        private View lineLeft, lineRight;
        private TextView desc, time;

        public HorizontalVh(View itemView) {
            super(itemView);
            this.point = (ImageView) itemView.findViewById(R.id.point);
            this.lineLeft = itemView.findViewById(R.id.line_left);
            this.lineRight = itemView.findViewById(R.id.line_right);
            this.desc = (TextView) itemView.findViewById(R.id.tv_desc);
            this.time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
