package com.sjl.activity;


import android.widget.TextView;

import com.sjl.manager.DownloadManager;
import com.sjl.manager.ObserverListener;
import com.sjl.view.FlikerProgressBar;
import com.sjl.uidemo.R;

/**
 * Created by song on 2017/9/16.
 */

public class NotificationDownloadAnotherActivity extends BaseActivity {
    /*private ProgressBar pb;*/
    private FlikerProgressBar flikerProgressBar;
    private TextView tv;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_notification_download_another);
        tv = (TextView) findViewById(R.id.tv_progress);
       /* pb = (ProgressBar) findViewById(R.id.pb_progress);*/
        flikerProgressBar = (FlikerProgressBar) findViewById(R.id.pb_progress);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        //被观察者
        DownloadManager downloadManager = DownloadManager.getInstance();
        //当被观察者数据发生变化时，通知观察者
        downloadManager.add(new MyDownloadObserver());//将观察者加入被观察者队列
    }


    public class MyDownloadObserver implements ObserverListener {


        @Override
        public void update(int progress) {
            if (progress != 100) {
                flikerProgressBar.setProgress(progress);
                tv.setText(progress + "%");
            } else {
                flikerProgressBar.setProgress(progress);
                tv.setText("下载完成");
            }
        }
    }
}
