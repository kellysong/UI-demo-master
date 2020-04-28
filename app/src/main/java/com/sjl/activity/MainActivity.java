package com.sjl.activity;

import android.Manifest;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sjl.fragment.main.BaseKnowledgeFragment;
import com.sjl.fragment.main.CustomViewFragment;
import com.sjl.fragment.main.OfficialViewFragment;
import com.sjl.ui.musicplayer.MusicMainActivity;
import com.sjl.util.Utils;
import com.sjl.uidemo.R;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private NavigationView navigationView;//导航view
    private DrawerLayout drawer;//抽屉
    private Toolbar toolbar;//工具条
    private ImageView mImageView;
    private ViewPager mViewPager;

    private List<Fragment> list;
    private MyAdapter adapter;
    private String[] titles = {"官方控件", "自定义控件", "基础知识"};
    private static final int REQUECT_CODE_SDCARD = 2;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("MD示例");
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mImageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.ivAvatar);
        ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {
        navigationView.setNavigationItemSelectedListener(this);
        mImageView.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        setSupportActionBar(toolbar);////这里会去判有没有actionbar存在，如果有直接抛异常，所以主题去掉actionbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);//在高版本API可能会报空指针
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //实例化
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_main_tab);
        //页面，数据源
        list = new ArrayList<>();
        list.add(new OfficialViewFragment());
        list.add(new CustomViewFragment());
        list.add(new BaseKnowledgeFragment());
        //ViewPager的适配器
        adapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        MPermissions.requestPermissions(this, REQUECT_CODE_SDCARD
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                ,Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA);


    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        //重写这个方法，将设置每个Tab的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);//自定义菜单项
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, MusicMainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        String string = null;
        switch (id) {
            case R.id.nav_me:
                string = "我";
                break;
            case R.id.nav_about:
                string = "关于";
                break;
            case R.id.nav_friend:
                string = "好友";
                break;
            case R.id.nav_notification:
                string = "通知";
                break;
            case R.id.nav_message:
                string = "私信";
                break;

            case R.id.nav_manage:
                Utils.openNewActivity(this,TestActivity.class);
                break;
            case R.id.nav_setting:
                string = "设置";
                break;
            default:
                break;
        }
        if (!TextUtils.isEmpty(string)) {
            Snackbar.make(getWindow().getDecorView(), string, Snackbar.LENGTH_SHORT).show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivAvatar:
                Utils.openNewActivity(this, LoginActivity.class);//点击头像登录
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(REQUECT_CODE_SDCARD)
    public void requestSdcardSuccess() {
    }

    @PermissionDenied(REQUECT_CODE_SDCARD)
    public void requestSdcardFailed() {
        Toast.makeText(this, "拒绝授权!", Toast.LENGTH_SHORT).show();
    }


}
