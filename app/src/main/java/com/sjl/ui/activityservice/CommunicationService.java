package com.sjl.ui.activityservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename CommunicationService.java
 * @time 2018/7/21 19:55
 * @copyright(C) 2018 深圳市北辰德科技股份有限公司
 */
public class CommunicationService extends Service {
    private Callback callback;
    private int count = 0;
    private boolean tag = false;
    private int switchFlag;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    /**
     * 0回调，1广播
     * @param switchFlag
     */
    public void switchFlag(int switchFlag) {
        this.count = 0;
        this.switchFlag = switchFlag;
    }

    public class MyBinder extends Binder {

        public void test(int a) {
            System.out.println("我是MyBinder：" + a);
        }

        public String test2(String str) {
            return str;
        }

        /**
         * 返回服务对象
         * @return
         */
        public CommunicationService getService() {
            return CommunicationService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!tag) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                    if (switchFlag == 0){
                        if (callback != null) {
                            callback.onDataChange("回调方式：" + count);
                        }
                    }else if (switchFlag == 1){
                        //发送广播
                        Intent intent = new Intent();
                        intent.putExtra("count", "广播方式：" + count);
                        intent.setAction("com.beichende.ui.activityservice.CommunicationService");
                        sendBroadcast(intent);
                    }
                }
            }
        }).start();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void onDataChange(String data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        count=0;
        tag = true;
        System.out.println("CommunicationService服务解绑");
    }


}

