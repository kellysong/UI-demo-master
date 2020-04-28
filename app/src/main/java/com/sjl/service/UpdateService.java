package com.sjl.service;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.sjl.activity.NotificationDownloadActivity;
import com.sjl.entity.ApkInfo;
import com.sjl.manager.DownloadManager;
import com.sjl.util.AppInfoUtils;
import com.sjl.util.FileUtils;
import com.sjl.uidemo.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by song on 2017/9/16.
 */

public class UpdateService extends Service {
    private static String downloadUrl;
    private static final int DOWN_OK = 1; // 下载完成
    private static final int DOWN_ERROR = 0;

    private String app_name;

    private NotificationManager notificationManager;
    private Notification notification;

    private Intent updateIntent;
    private PendingIntent pendingIntent;
    private String updateFile;

    private int notification_id = 0;
    private String updateFileAbsolutePath;


    private HttpHandler<File> httpHandler;

    /***
     * 更新UI
     */
    final Handler handler = new Handler() {
        @SuppressWarnings("deprecation")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_OK:
                    // 下载完成，点击安装
                    ApkInfo apkInfo = AppInfoUtils.getApkInfo(updateFileAbsolutePath, getApplicationContext());
                    NotificationCompat.Builder ok = new NotificationCompat.Builder(getApplicationContext());
                    ok.setSmallIcon(R.mipmap.ic_launcher); //设置图标
                    ok.setContentTitle(apkInfo.getAppName()); //设置标题
                    ok.setContentText("下载成功，点击安装"); //消息内容

                    Intent installApkIntent = getFileIntent(new File(updateFile));
                    pendingIntent = PendingIntent.getActivity(UpdateService.this, 0, installApkIntent, 0);
                    ok.setContentIntent(pendingIntent);
                    Notification okNotification = ok.build();
                    okNotification.flags |= Notification.FLAG_AUTO_CANCEL;
                    notificationManager.notify(notification_id, okNotification);
                    stopService(updateIntent);
                    break;
                case DOWN_ERROR:
                    NotificationCompat.Builder errorBuilder = new NotificationCompat.Builder(getApplicationContext());
                    errorBuilder.setSmallIcon(R.mipmap.ic_launcher); //设置图标
                    errorBuilder.setContentTitle("下载失败");
                    Notification errorNotification = errorBuilder.build();
                    notificationManager.notify(notification_id, errorNotification);
                    break;
                default:
                    stopService(updateIntent);
                    break;
            }
        }
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.i("正在创建更新服务");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            try {
                app_name = intent.getStringExtra("appName");
                downloadUrl = intent.getStringExtra("downloadUrl");
                Logger.i("app_name=" + app_name + ",downloadUrl=" + downloadUrl);
                // 创建文件
                File updateFile = FileUtils.getDiskCacheDir(getApplicationContext(), "test.apk");
                if (!updateFile.exists()) {
                    try {
                        updateFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // 创建通知
                createNotification();
                // 开始下载
                updateFileAbsolutePath = updateFile.getAbsolutePath();
                downloadUpdateFile(downloadUrl, updateFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (httpHandler != null){
            httpHandler.cancel();//停止下载
        }
    }

    /***
     * 创建通知栏
     */
    RemoteViews contentView;

    @SuppressWarnings("deprecation")
    public void createNotification() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
        mBuilder.setSmallIcon(R.mipmap.ic_launcher); //设置图标
        mBuilder.setTicker("开始下载任务来了");//闪出来的消息文本
        /***
         * 在这里我们用自定的view来显示Notification
         */
        contentView = new RemoteViews(getPackageName(), R.layout.item_notification);
        contentView.setTextViewText(R.id.notificationTitle, "正在下载");
        contentView.setTextViewText(R.id.notificationPercent, "0%");
        contentView.setProgressBar(R.id.notificationProgress, 100, 0, false);

        mBuilder.setContent(contentView);

        updateIntent = new Intent(this, NotificationDownloadActivity.class);
        updateIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //将来意图，用于点击通知之后的操作，updateIntent用于跳转等操作
        pendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);
        mBuilder.setContentIntent(pendingIntent);

        notification = mBuilder.build();
        notificationManager.notify(notification_id, notification);
    }

    /***
     * 下载文件
     */
    public void downloadUpdateFile(String down_url, String file) throws Exception {
        updateFile = file;
        //  true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
        // true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
        HttpUtils httpUtils = new HttpUtils();
        Logger.i("文件路径：" + file);///storage/emulated/0/Android/data/com.example.test/cache/test.apk
        httpHandler = httpUtils.download(down_url, file, new RequestCallBack<File>() {

            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                // 下载成功
                Message message = handler.obtainMessage();
                message.what = DOWN_OK;
                handler.sendMessage(message);
                installApk(new File(updateFile), UpdateService.this);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Message message = handler.obtainMessage();
                message.what = DOWN_ERROR;
                handler.sendMessage(message);
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {//// 下载文件的进度，在主线程运行
                super.onLoading(total, current, isUploading);
                double x_double = current * 1.0;
                double tempresult = x_double / total;
                DecimalFormat df1 = new DecimalFormat("0.00"); // ##.00%
                // 百分比格式，后面不足2位的用0补齐
                String result = df1.format(tempresult);
                int temp = (int) (Float.parseFloat(result) * 100);
                contentView.setTextViewText(R.id.notificationPercent, temp + "%");
                contentView.setProgressBar(R.id.notificationProgress, 100, temp, false);
                DownloadManager.getInstance().notifyObserver(temp);//通知观察者数据发生改变
                notificationManager.notify(notification_id, notification);
            }
        });
    }

    // 下载完成后打开安装apk界面
    public static void installApk(File file, Context context) {
        //L.i("msg", "版本更新获取sd卡的安装包的路径=" + file.getAbsolutePath());
        Intent openFile = getFileIntent(file);
        context.startActivity(openFile);

    }

    public static Intent getFileIntent(File file) {
        Uri uri = Uri.fromFile(file);
        String type = getMIMEType(file);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, type);
        return intent;
    }

    public static String getMIMEType(File f) {
        String type = "";
        String fName = f.getName();
        // 取得扩展名
        String end = fName
                .substring(fName.lastIndexOf(".") + 1, fName.length());
        if (end.equals("apk")) {
            type = "application/vnd.android.package-archive";
        } else {
            // /*如果无法直接打开，就跳出软件列表给用户选择 */
            type = "*/*";
        }
        return type;
    }
}
