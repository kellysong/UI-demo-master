package com.sjl.ui.musicplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.sjl.uidemo.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename MusicService.java
 * @time 2018/10/5 18:59
 * @copyright(C) 2018 xxx有限公司
 */
public class MusicService extends Service {
    List<Mp3Info> mp3InfoList = new ArrayList<>();
    MediaPlayer mediaPlayer;
    Mp3Info mp3Info;
    Notification notification;
    static int location;
    int position;
    static Boolean isPlaying = false;
    RemoteViews contecntViews;

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        //获得歌曲Mp3Info类列表
        mp3InfoList = MediaUtils.getMp3InfoList(MusicService.this);
// 添加来电监听事件
        TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE); // 获取系统服务
        telManager.listen(new MobliePhoneStateListener(),
                PhoneStateListener.LISTEN_CALL_STATE);

    }

    /**
     * 电话监听器类
     */
    private class MobliePhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE: // 挂机状态,继续播放音乐

                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:    //通话状态
                case TelephonyManager.CALL_STATE_RINGING:    //响铃状态
                    //停止播放音乐
                    break;
                default:
                    break;
            }
        }
    }

    //多次触发
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int tag = intent.getIntExtra("tag", -1);
        Log.i("SIMPLE_LOGGER", "service onStartCommand tag:" + tag);
        musicPlayController(tag,intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void musicPlayController(int tag, Intent intent) {
        if (mp3InfoList == null || mp3InfoList.isEmpty()){
            return;
        }

        switch (tag) {
            case 0: {//上一曲/下一曲
                isPlaying = true;
                location = intent.getIntExtra("location", -1);
                mp3Info = mp3InfoList.get(location);
                Log.i("SIMPLE_LOGGER", "mp3Info:" + mp3Info.toString());

                String url = mp3Info.getUrl();
                String title = mp3Info.getTitle();
                String artist = mp3Info.getArtist();
                long id = mp3Info.getId();
                long albumId = mp3Info.getAlbumId();
                try {
                    if (mediaPlayer != null) {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(this, Uri.parse(url));
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        showNotification(title, artist, id, albumId);
                    }
                } catch (Exception e) {
                    Logger.e(e, "上一曲/下一曲异常");
                }
            }
            break;
            case 1: {//暂停/播放
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    } else {
                        mediaPlayer.start();
                    }
                }
                isPlaying = intent.getBooleanExtra("state", false);
                if (isPlaying == true)
                    isPlaying = false;
                else
                    isPlaying = true;

            }
            break;
            case 2: {//拖动进度条
                isPlaying = true;
                int progress = intent.getIntExtra("progress", 0);
                if (mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                }
            }
            break;
            case 3: {//停止播放
                mediaPlayer.stop();
                try {
                    mediaPlayer.prepare();
                    mediaPlayer.seekTo(0);
                } catch (Exception e) {
                    Logger.e(e, "停止播放异常");
                }
            }
            break;
        }
        //
        new PlayProgress().execute();
        //设置自动播放下一首
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (mediaPlayer != null) {
                    try {
                        if (++location < mp3InfoList.size()) {

                        } else {//从头开始
                            location = 0;
                        }
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(MusicService.this, Uri.parse(mp3InfoList.get(location).getUrl()));
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //通过发送广播让MainActivity,PlayActivity更新UI
                Intent intent = new Intent("com.example.musicplayer.order_play");
                intent.putExtra("location", location);
                sendBroadcast(intent);
                //后台更新NotificationUI
                isPlaying = true;
                mp3Info = mp3InfoList.get(location);
                String title = mp3Info.getTitle();
                String artist = mp3Info.getArtist();
                long id = mp3Info.getId();
                long albumId = mp3Info.getAlbumId();
                showNotification(title, artist, id, albumId);
            }
        });
    }

    public class PlayProgress extends AsyncTask<Void, Integer, Integer> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public Integer doInBackground(Void... parms) {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                position = mediaPlayer.getCurrentPosition();
                if (position < mediaPlayer.getDuration()) {
                    Intent mIntent = new Intent("com.example.musicplayer.update_seekbar");
                    mIntent.putExtra("position", position);
                    sendBroadcast(mIntent);
                } else {
                    break;
                }
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            Log.d("===", "异步类执行完毕");
            super.onPostExecute(integer);
        }
    }

    //通知栏
    public void showNotification(String title, String artist, long id, long albumId) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setAutoCancel(false);
        contecntViews = new RemoteViews(getPackageName(), R.layout.notification_music);
        contecntViews.setTextViewText(R.id.notification_musicName, title);
        contecntViews.setTextViewText(R.id.notification_artist, artist);
        Bitmap bitmap = MediaUtils.getArtwork(this, id,
                albumId, true, false);// 获取专辑位图对象，为大图
        contecntViews.setImageViewBitmap(R.id.notification_imageView, bitmap);
        contecntViews.setOnClickPendingIntent(R.id.notification_previousMusic, PendingIntent.getBroadcast(this, 0,
                new Intent("com.example.musicplayer.notification.previous").putExtra("tag2", 0), PendingIntent.FLAG_UPDATE_CURRENT));
        contecntViews.setOnClickPendingIntent(R.id.notification_play_pause, PendingIntent.getBroadcast(this, 0,
                new Intent("com.example.musicplayer.notification.play_pause").putExtra("tag2", 1).putExtra("state", !isPlaying), PendingIntent.FLAG_UPDATE_CURRENT));
        contecntViews.setOnClickPendingIntent(R.id.notification_next, PendingIntent.getBroadcast(this, 0,
                new Intent("com.example.musicplayer.notification.next").putExtra("tag2", 2), PendingIntent.FLAG_UPDATE_CURRENT));
        notification = builder.setContent(contecntViews).setSmallIcon(R.mipmap.ic_launcher).build();

        manager.notify(0, notification);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();

    }
}
