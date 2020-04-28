package com.sjl.activity;


import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.sjl.adapter.GalleryAdapter;
import com.sjl.view.GalleryRecyclerView;
import com.sjl.uidemo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GalleryActivity extends BaseActivity {

	private GalleryRecyclerView mRecyclerView;
	private GalleryAdapter mAdapter;
	private List<Integer> mDatas;
	private ImageView mImg;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_gallery);
		mImg = (ImageView) findViewById(R.id.id_content);
		mRecyclerView = (GalleryRecyclerView) findViewById(R.id.id_recyclerview_horizontal);
	}

	@Override
	protected void initListener() {
		mRecyclerView.setOnItemScrollChangeListener(new GalleryRecyclerView.OnItemScrollChangeListener() {
			@Override
			public void onChange(View view, int position) {
				mImg.setImageResource(mDatas.get(position));
			};
		});
	}

	@Override
	protected void initData() {
		mDatas = new ArrayList<Integer>(Arrays.asList(R.mipmap.fj1, R.mipmap.fj2, R.mipmap.fj3, R.mipmap.fj4,
				R.mipmap.fj5,R.mipmap.fj1));
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		mAdapter = new GalleryAdapter(this, mDatas);
		mRecyclerView.setAdapter(mAdapter);
		mAdapter.setOnItemClickLitener(new GalleryAdapter.OnItemClickLitener() {
			@Override
			public void onItemClick(View view, int position) {
				// Toast.makeText(getApplicationContext(), position + "",
				// Toast.LENGTH_SHORT)
				// .show();
				mImg.setImageResource(mDatas.get(position));
			}
		});
	}

}
