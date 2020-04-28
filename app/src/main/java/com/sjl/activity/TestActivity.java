package com.sjl.activity;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.view.View;

import com.sjl.uidemo.R;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename TestActivity.java
 * @time 2018/11/22 9:11
 * @copyright(C) 2018 xxx有限公司
 */
public class TestActivity extends BaseActivity {
    @Override
    protected void initView() {
        setContentView(R.layout.test_activity);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            String channelId = "chat";
//            String channelName = "聊天消息";
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            createNotificationChannel(channelId, channelName, importance);
//            channelId = "subscribe";
//            channelName = "订阅消息";
//            importance = NotificationManager.IMPORTANCE_DEFAULT;
//            createNotificationChannel(channelId, channelName, importance);
//        }
    }

    //    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
//        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(
//                NOTIFICATION_SERVICE);
//        notificationManager.createNotificationChannel(channel);
    }

    public void sendChatMsg(View view) {
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
////这样写不用写两套，即8.0以上 8.0以下
//        Notification notification = new NotificationCompat.Builder(this, "chat")
//                .setContentTitle("收到一条聊天消息")
//                .setContentText("今天中午吃什么？")
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
//                .setAutoCancel(true)
//                .build();
//        manager.notify(1, notification);
    }

    public void sendSubscribeMsg(View view) {
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        Notification notification = new NotificationCompat.Builder(this, "subscribe")
//                .setContentTitle("收到一条订阅消息")
//                .setContentText("地铁沿线30万商铺抢购中！")
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
//                .setAutoCancel(true)
//                .build();
//        manager.notify(2, notification);
    }

    Camera mCamera;

    public void openFlash(View view) {
        open();


    }

    public void closeFlash(View view) {
        close();
    }

    /**
     * 打开闪光灯
     *
     * @return
     */
    private void open() {
        controlFlashLight(true);

    }

    /**
     * 关闭闪光灯
     *
     * @return
     */
    private void close() {
        controlFlashLight(false);
    }


    /**
     * 闪关灯控制
     * @param openOrClose
     */
    private void controlFlashLight(boolean openOrClose) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                //获取CameraManager
                CameraManager mCameraManager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
                String[] ids = mCameraManager.getCameraIdList();
                for (String id : ids) {
                    CameraCharacteristics c = mCameraManager.getCameraCharacteristics(id);
                    Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
                    if (flashAvailable != null && flashAvailable
                            && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {//判断是否后置
                        mCameraManager.setTorchMode(id, openOrClose);
                        break;
                    }
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else {
            if (openOrClose) {
                mCamera = Camera.open();
                if (mCamera == null) {
                    return;
                }
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
            } else {
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
            }

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;

        }
    }
}
