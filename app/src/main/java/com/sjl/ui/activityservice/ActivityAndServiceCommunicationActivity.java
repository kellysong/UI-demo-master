package com.sjl.ui.activityservice;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.sjl.uidemo.R;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename ActivityAndServiceCommunicationActivity.java
 * @time 2018/7/21 19:44
 * @copyright(C) 2018 深圳市北辰德科技股份有限公司
 */
public class ActivityAndServiceCommunicationActivity extends AppCompatActivity {
    private MyConn conn;
    private CommunicationService.MyBinder myBinder;//我定义的中间人对象
    private MyReceiver receiver = null;
    private TextView textView = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_communication);
        textView = (TextView) findViewById(R.id.tv_show);


        Intent intent = new Intent(this, CommunicationService.class);
        //连接服务
        conn = new MyConn();
        //通过绑定启动服务
        bindService(intent, conn, BIND_AUTO_CREATE);

        //注册广播接收器
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.beichende.ui.activityservice.CommunicationService");
        registerReceiver(receiver, filter);
    }

    //点击按钮调用服务里面的方法
    public void test1(View v) {
        myBinder.test(100);
        String str = myBinder.test2("我是activity，有参数返回");
        System.out.println(str);
    }

    /**
     * 回调实现Service向activity传递数据
     *
     * @param v
     */
    public void test2(View v) {
        CommunicationService communicationService = myBinder.getService();
        communicationService.switchFlag(0);
        textView.setText("");
    }

    /**
     * 广播实现Service向activity传递数据
     *
     * @param v
     */
    public void test3(View v) {
        textView.setText("");
        CommunicationService communicationService = myBinder.getService();
        communicationService.switchFlag(1);
        textView.setText("");
    }

    //监视服务的状态
    private class MyConn implements ServiceConnection {

        //当服务连接成功调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获取中间人对象
            myBinder = (CommunicationService.MyBinder) service;
            CommunicationService communicationService = myBinder.getService();
            communicationService.setCallback(new CommunicationService.Callback() {
                @Override
                public void onDataChange(final String data) {
                    ActivityAndServiceCommunicationActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(data + "," + textView.getText());
                        }
                    });
                }
            });
        }

        //失去连接
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String count = bundle.getString("count");
            textView.setText(count + "," + textView.getText());

        }
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        //当activity 销毁的时候 解绑服务
        if (conn != null) {
            unbindService(conn);
            conn = null;
        }
        super.onDestroy();
    }
}
