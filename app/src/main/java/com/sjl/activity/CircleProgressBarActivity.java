package com.sjl.activity;


import com.sjl.view.CircleProgressView;
import com.sjl.uidemo.R;

/**
 * Created by song on 2017/9/9.
 */

public class CircleProgressBarActivity extends BaseActivity {
    private CircleProgressView mCircleBar;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_circle_progressbar);
        mCircleBar = (CircleProgressView) findViewById(R.id.circleProgressbar);
        mCircleBar.setmTxtHint1("战胜了");
        mCircleBar.setmTxtHint2("对手");
        mCircleBar.setProgress(80);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
