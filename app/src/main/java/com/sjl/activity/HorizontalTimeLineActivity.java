package com.sjl.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sjl.adapter.TimeLineAdapter;
import com.sjl.entity.TimeLine;
import com.sjl.uidemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 水平时间轴Activity
 * Created by song on 2017/8/25.
 */

public class HorizontalTimeLineActivity extends BaseActivity {
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private List<TimeLine> mTimeLines;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_horizontal_timeline);
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
        mTimeLines.add(new TimeLine("购买","05.18"));
        mTimeLines.add(new TimeLine("起息","05.19"));
        mTimeLines.add(new TimeLine("到期","共12期"));
        mTimeLines.add(new TimeLine("到账","1-5个工作日"));
        TimeLineAdapter adapter = new TimeLineAdapter(this,mTimeLines);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(adapter);
        adapter.setCurrentNode(2);
    }
}
