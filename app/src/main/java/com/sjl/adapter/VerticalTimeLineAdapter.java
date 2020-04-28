package com.sjl.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjl.entity.TimeLine;
import com.sjl.uidemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 2017/8/25.
 */

public class VerticalTimeLineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private List<TimeLine> traceList = new ArrayList<>(1);
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL = 0x0001;
    private Context context;
    public VerticalTimeLineAdapter(Context context, List<TimeLine> traceList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.traceList = traceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_vertical_timeline, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemHolder = (ViewHolder) holder;
        if (getItemViewType(position) == TYPE_TOP) {
            // 第一行头的竖线不显示
            itemHolder.tvTopLine.setVisibility(View.INVISIBLE);
            // 字体颜色加深
            itemHolder.tvAcceptTime.setTextColor(context.getResources().getColor(R.color.gray_555));//getColor(int id)在API版本23时(Android 6.0)已然过时,
            itemHolder.tvAcceptStation.setTextColor(ContextCompat.getColor(context,R.color.gray_555));
            itemHolder.tvDot.setBackgroundResource(R.drawable.timelline_dot_first);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            itemHolder.tvTopLine.setVisibility(View.VISIBLE);
            itemHolder.tvAcceptTime.setTextColor(ContextCompat.getColor(context,R.color.gray_999));
            itemHolder.tvAcceptStation.setTextColor(ContextCompat.getColor(context,R.color.gray_999));
            itemHolder.tvDot.setBackgroundResource(R.drawable.timelline_dot_normal);
        }

        itemHolder.bindHolder(traceList.get(position));
    }

    @Override
    public int getItemCount() {
        return traceList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAcceptTime, tvAcceptStation;
        private TextView tvTopLine, tvDot;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAcceptTime = (TextView) itemView.findViewById(R.id.tvAcceptTime);
            tvAcceptStation = (TextView) itemView.findViewById(R.id.tvAcceptStation);
            tvTopLine = (TextView) itemView.findViewById(R.id.tvTopLine);
            tvDot = (TextView) itemView.findViewById(R.id.tvDot);
        }

        public void bindHolder(TimeLine timeLine) {
            tvAcceptTime.setText(timeLine.getTime());
            tvAcceptStation.setText(timeLine.getDesc());
        }
    }
}
