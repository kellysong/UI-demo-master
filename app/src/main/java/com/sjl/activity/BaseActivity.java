package com.sjl.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lidroid.xutils.util.LogUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * Activity基类
 * @author Kelly
 * @version 1.0.0
 * @filename BaseActivity.java
 * @time 2017年7月20日 上午10:09:03
 * @copyright(C) 2017 深圳市北辰德科技股份有限公司
 */
public abstract class BaseActivity extends AppCompatActivity {
	public final static List<BaseActivity> mActivities = new LinkedList<BaseActivity>();
	public static BaseActivity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		synchronized (mActivities) {
			mActivities.add(this);
		}
		initView();
		initListener();
		initData();
	}



	/**
	 * 初始化view
	 */
	protected abstract void initView();

	/**
	 * 初始化监听
	 */
	protected abstract void initListener();

	/**
	 * 初始化数据
	 */
	protected abstract void initData();
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		synchronized (mActivities) {
			LogUtils.i("删除Activity成功,"+this);
			mActivities.remove(this);
		}
	}

	public void killAll() {
		List<BaseActivity> copy;
		synchronized (mActivities) {
			copy = new LinkedList<BaseActivity>(mActivities);// 复制
		}
		for (BaseActivity activity : copy) {//迭代删除
			mActivities.remove(activity);
		}
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
