package com.sjl.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sjl.global.Global;
import com.sjl.manager.DownloadManager;
import com.sjl.manager.ObserverListener;
import com.sjl.service.UpdateService;
import com.sjl.util.Utils;
import com.sjl.uidemo.R;

/**
 * Created by song on 2017/9/16.
 */

public class NotificationDownloadActivity extends BaseActivity {
    private Button update,stopDownload,anotherPage;
    private ProgressBar pb;
    private TextView tv;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_notification_download);
        update = (Button) findViewById(R.id.btn_check_update);
        stopDownload = (Button) findViewById(R.id.btn_stop_download);
        anotherPage = (Button) findViewById(R.id.btn_jump_page);
        tv = (TextView) findViewById(R.id.tv_progress);
        pb = (ProgressBar)findViewById(R.id.pb_progress);
    }

    @Override
    protected void initListener() {
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkVersion();
            }
        });
        stopDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.stopService(NotificationDownloadActivity.this,"com.beichende.service.UpdateService",UpdateService.class);
            }
        });

        anotherPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationDownloadActivity.this.startActivity(new Intent(NotificationDownloadActivity.this,NotificationDownloadAnotherActivity.class));
            }
        });
    }

    @Override
    protected void initData() {
        initGlobal();
        checkVersion();
        //被观察者
        DownloadManager downloadManager = DownloadManager.getInstance();
        //当被观察者数据发生变化时，通知观察者
        downloadManager.add(new MyDownloadObserver());//将观察者加入被观察者队列
    }

    /**
     * 初始化全局变量
     * 实际工作中这个方法中serverVersion从服务器端获取，最好在启动画面的activity中执行
     */
    public void initGlobal(){
        try{
            Global.localVersion = getPackageManager().getPackageInfo(getPackageName(),0).versionCode; //设置本地版本号
            Global.serverVersion = 202;//假定服务器版本为2，本地版本默认是1
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    /**
     * 检查更新版本
     */
    public void checkVersion(){

        if(Global.localVersion < Global.serverVersion){
            //发现新版本，提示用户更新
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("提示")
                    .setMessage("发现新版本2.0.")
                    .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //开启更新服务UpdateService
                            //这里为了把update更好模块化，可以传一些updateService依赖的值
                            //如布局ID，资源ID，动态获取的标题,这里以app_name为例
                            Intent updateIntent =new Intent(NotificationDownloadActivity.this, UpdateService.class);
                            updateIntent.putExtra("appName",getResources().getString(R.string.app_name));//R.string.app_name返回的是一int型的ID
                            updateIntent.putExtra("downloadUrl","http://imtt.dd.qq.com/16891/5889A65C963493987CAD341C47DAF902.apk?fsname=com.tencent.pao_1.0.48.0_148.apk&csr=1bbd");
                            startService(updateIntent);
                        }
                    })
                    .setNegativeButton("以后再说",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alert.create().show();
        }else{
            //清理工作，略去
            //cheanUpdateFile(),文章后面我会附上代码
        }
    }

    public class MyDownloadObserver implements ObserverListener {


        @Override
        public void update(int progress) {
            if (progress != 100){
                pb.setProgress(progress);
                tv.setText(progress+"%");
            }else{
                pb.setProgress(progress);
                tv.setText("下载完成");
            }
        }
    }
}
