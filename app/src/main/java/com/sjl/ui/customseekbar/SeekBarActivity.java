package com.sjl.ui.customseekbar;

import android.widget.SeekBar;

import com.sjl.activity.BaseActivity;
import com.sjl.uidemo.R;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename SeekBarActivity.java
 * @time 2019/3/1 15:58
 * @copyright(C) 2019 song
 */
public class SeekBarActivity extends BaseActivity {
    @Override
    protected void initView() {
        setContentView(R.layout.seekbar_layout);
        SeekBar seekBar = (SeekBar) findViewById(R.id.sb_custom);
        SeekBar seekBar2 = (SeekBar) findViewById(R.id.sb_custom2);
        seekBar2.setProgress(50);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
