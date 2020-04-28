package com.sjl.activity;


import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sjl.fragment.SettingFragment;
import com.sjl.uidemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;


/**设置页面
 *
 * Created by song on 2017/9/23.
 */

public class SettingActivity extends BaseActivity {
    @Bind(R.id.toolbar_preference)
    Toolbar mToolbar;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        initToolbar();
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingFragment()).commit();
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        mToolbar.setTitle(getResources().getString(R.string.title_activity_setting));
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_left_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 选项菜单
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

}
