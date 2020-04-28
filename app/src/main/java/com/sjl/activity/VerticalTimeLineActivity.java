package com.sjl.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sjl.adapter.VerticalTimeLineAdapter;
import com.sjl.entity.TimeLine;
import com.sjl.uidemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 垂直时间轴Activity
 * Created by song on 2017/8/25.
 */

public class VerticalTimeLineActivity extends BaseActivity {
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private List<TimeLine> mTimeLines;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_vertical_timeline);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        initAdapter();
    }

    private void initAdapter() {
        mTimeLines = new ArrayList<>();
        mTimeLines.add(new TimeLine("2017-05-25 17:48:00", "[沈阳市] [沈阳和平五部]的派件已签收 感谢使用中通快递,期待再次为您服务!"));
        mTimeLines.add(new TimeLine("2017-05-25 14:13:00", "[沈阳市] [沈阳和平五部]的东北大学代理点正在派件 电话:18040xxxxxx 请保持电话畅通、耐心等待"));
        mTimeLines.add(new TimeLine("2017-05-25 13:01:04", "[沈阳市] 快件到达 [沈阳和平五部]"));
        mTimeLines.add(new TimeLine("2017-05-25 12:19:47", "[沈阳市] 快件离开 [沈阳中转]已发往[沈阳和平五部]"));
        mTimeLines.add(new TimeLine("2017-05-25 11:12:44", "[沈阳市] 快件到达 [沈阳中转]"));
        mTimeLines.add(new TimeLine("2017-05-24 03:12:12", "[嘉兴市] 快件离开 [杭州中转部]已发往[沈阳中转]"));
        mTimeLines.add(new TimeLine("2017-05-23 21:06:46", "[杭州市] 快件到达 [杭州汽运部]"));
        mTimeLines.add(new TimeLine("2017-05-23 18:59:41", "[杭州市] 快件离开 [杭州乔司区]已发往[沈阳]"));
        mTimeLines.add(new TimeLine("2017-05-23 18:35:32", "[杭州市] [杭州乔司区]的市场部已收件 电话:18358xxxxxx"));
        VerticalTimeLineAdapter adapter = new VerticalTimeLineAdapter(this,mTimeLines);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//默认垂直
        mRecyclerView.setAdapter(adapter);
    }
}
