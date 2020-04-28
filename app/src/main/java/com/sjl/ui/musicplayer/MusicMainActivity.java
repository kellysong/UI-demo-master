package com.sjl.ui.musicplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sjl.activity.BaseActivity;
import com.sjl.uidemo.R;
import com.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename MusicMainActivity.java
 * @time 2018/10/5 18:59
 * @copyright(C) 2018 xxx有限公司
 */
public class MusicMainActivity extends BaseActivity {
    static ListView listView;
    List<Mp3Info> mp3InfoList;
    Mp3Info mp3Info;
    static int location = 0;
    static Boolean isPlaying = false;
    List musicNameList = new ArrayList<>();
    static TextView musicNameView;
    static TextView artistView;
    static ImageView albumView;
    static ImageView playOrPauseButton;
    static ImageView nextButton;
    static SeekBar seekBar;
    static TextView currentTiemView;
    static TextView durationView;

    NotificationBroadcast notificationBroadcast;
    PlayActivityBroadcast playActivityBroadcast;
    UpdateSeekbarBroadcast updateSeekbarBroadcast;
    OrderPlayBroadcast orderPlayBroadcast;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_music_main);
        //ToolBar及DrawerLayout
        Toolbar toolbar = (Toolbar) findViewById(R.id.mainActivity_toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //左上角加上一个返回图标
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置左上角图标是否可点击
            actionBar.setHomeButtonEnabled(true);
        }
        //获得各个控件并设置点击事件
        musicNameView = (TextView) findViewById(R.id.mainActivity_musicName);
        artistView = (TextView) findViewById(R.id.mainActivity_artist);
        playOrPauseButton = (ImageView) findViewById(R.id.mainActivity_play_pause);
        nextButton = (ImageView) findViewById(R.id.mainActivity_next);
        seekBar = (SeekBar) findViewById(R.id.mainActivity_seekBar);
        currentTiemView = (TextView) findViewById(R.id.main_current_position);
        durationView = (TextView) findViewById(R.id.main_final_position);
        albumView = (ImageView) findViewById(R.id.album_view);
        listView = (ListView) findViewById(R.id.musicName_list);
        if (isPlaying)
            playOrPauseButton.setImageResource(R.drawable.btn_pause);
        else playOrPauseButton.setImageResource(R.drawable.btn_play);
    }

    @Override
    protected void initListener() {

        //专辑图片点击事件，进入PlayActivity中，并通过Intent传递location和isPlaying状态
        albumView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MusicMainActivity.this, MusicPlayActivity.class);
                intent.putExtra("state", isPlaying);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        });
        //暂停、播放按钮点击事件
        playOrPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playOrpause();
            }
        });
        //下一首点击事件
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp3InfoList != null && !mp3InfoList.isEmpty()) {
                    nextMusic();
                }
            }
        });


        //为SeekBar设置点击拖动事件
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                Intent intent = new Intent(MusicMainActivity.this, MusicService.class);
                intent.putExtra("tag", 2);
                intent.putExtra("progress", progress);
                startService(intent);
            }
        });

    }

    @Override
    protected void initData() {
        //注册监听SeekBar变化的监听器
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.musicplayer.update_seekbar");
        updateSeekbarBroadcast = new UpdateSeekbarBroadcast();
        registerReceiver(updateSeekbarBroadcast, filter);
        //监听PlayActivity的点击事件
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("com.example.musicplayer.react_to_playactiity");
        playActivityBroadcast = new PlayActivityBroadcast();
        registerReceiver(playActivityBroadcast, filter1);
        //注册监听Notification的监听器
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("com.example.musicplayer.notification.previous");
        filter2.addAction("com.example.musicplayer.notification.play_pause");
        filter2.addAction("com.example.musicplayer.notification.next");
        notificationBroadcast = new NotificationBroadcast();
        registerReceiver(notificationBroadcast, filter2);
        //注册监听后台顺序播放的监听器
        IntentFilter filter3 = new IntentFilter();
        filter3.addAction("com.example.musicplayer.order_play");
        orderPlayBroadcast = new OrderPlayBroadcast();
        registerReceiver(orderPlayBroadcast, filter3);
        //设置各控件初始视图为上次离开时所播放的歌曲，如果进程已被Kill掉，则为第一首
        mp3InfoList = MediaUtils.getMp3InfoList(MusicMainActivity.this);

        //从媒体库中获得音乐列表并显示到ListView中,同时设置点击事件
        final ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, musicNameList);
        listView.setAdapter(aa);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                location = i;
                setPlay(location);
            }
        });
        if (mp3InfoList != null && !mp3InfoList.isEmpty()) {
            mp3Info = mp3InfoList.get(location);
            musicNameView.setText(mp3Info.getTitle());
            artistView.setText(mp3Info.getArtist());
            long id = mp3Info.getId();
            long albumId = mp3Info.getAlbumId();
            albumView.setImageBitmap(MediaUtils.getArtwork(this, id,
                    albumId, true, false));
            for (int i = 0; i < mp3InfoList.size(); i++) {
                musicNameList.add(mp3InfoList.get(i).getTitle());
            }
            aa.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {//在Toolbar上的左上角的返回箭头的键值为Android.R.id.home，切记为Android.R.id.home，而不是R.id.home，否则可能监听不到左上角监听的点击事件
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //设置播放某一首歌曲
    public void setPlay(int location) {
        //切换播放状态
        isPlaying = true;
        //根据要播放的音乐曲目更新MainActivity的UI
        mp3Info = mp3InfoList.get(location);
        long id = mp3Info.getId();
        long albumId = mp3Info.getAlbumId();
        musicNameView.setText(mp3Info.getTitle());
        artistView.setText(mp3Info.getArtist());
        Bitmap bitmap = MediaUtils.getArtwork(this, id,
                albumId, true, false);// 获取专辑位图对象，为大图
        albumView.setImageBitmap(bitmap);
        //切换播放状态图标
        playOrPauseButton.setImageResource(R.drawable.btn_pause);

        seekBar.setMax((int) mp3Info.getDuration());
        currentTiemView.setText("0:00");
        durationView.setText(MediaUtils.formatTime(mp3Info.getDuration()));
        //传递歌曲下标location并启动服务
        Intent intent = new Intent(MusicMainActivity.this, MusicService.class);
        intent.putExtra("tag", 0);
        intent.putExtra("location", location);
        startService(intent);


    }

    //播放下一首歌曲
    public void nextMusic() {
        if ((++location) < mp3InfoList.size()) {
            setPlay(location);
        } else {//到达最后一首歌，从头来
            location = 0;
            setPlay(location);
        }
    }

    //播放上一首歌曲
    public void previousMusic() {
        if ((--location) >= 0) {
            setPlay(location);
        } else {
            //到达第一首歌，从最后来
            location = mp3InfoList.size() - 1;
            setPlay(location);
        }
    }

    //暂停或继续播放
    public void playOrpause() {
        Intent intent = new Intent(MusicMainActivity.this, MusicService.class);
        //切换播放状态并改变状态图标
        isPlaying = !isPlaying;
        if (isPlaying)
            playOrPauseButton.setImageResource(R.drawable.btn_pause);
        else playOrPauseButton.setImageResource(R.drawable.btn_play);
        intent.putExtra("tag", 1);
        intent.putExtra("state", isPlaying);
        startService(intent);

    }

    //停止播放此首音乐，并将进度条滑至开始处
    public void stopMusic() {
        Intent intent = new Intent(MusicMainActivity.this, MusicService.class);
        intent.putExtra("tag", 3);
        if (isPlaying)
            playOrPauseButton.setImageResource(R.drawable.btn_play);
        else playOrPauseButton.setImageResource(R.drawable.btn_pause);
        startService(intent);

    }


    //监听来自Service的SeekBar变化
    class UpdateSeekbarBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int position = intent.getIntExtra("position", 0);
            currentTiemView.setText(MediaUtils.formatTime(position));
            seekBar.setProgress(position);

        }
    }

    //接收来自PlayActivity的广播
    class PlayActivityBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int tag1 = intent.getIntExtra("tag1", -1);
            switch (tag1) {
                case 0: {
                    playOrpause();
                }
                break;
                case 1:
                    previousMusic();
                    break;
                case 2:
                    nextMusic();
                    break;
                case 3:
                    stopMusic();
                default:
                    break;
                case 4: {
                    location = intent.getIntExtra("location", 5);
                    setPlay(location);
                }
            }


        }
    }

    //接收来自Notification的广播
    class NotificationBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int tag2 = intent.getIntExtra("tag2", -1);
            LogUtils.d("received the broadcast from notification");
            switch (tag2) {
                case 0:
                    previousMusic();
                    break;
                case 1: {
                    playOrpause();
                    isPlaying = intent.getBooleanExtra("state", false);
                }
                break;
                case 2:
                    nextMusic();
                    break;
                default:
                    break;
            }
        }
    }

    //接收来自后台自动播放的广播以更新UI
    class OrderPlayBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.d("update ui according to the orderly play");
            location = intent.getIntExtra("location", -1);
            mp3Info = mp3InfoList.get(location);
            long id = mp3Info.getId();
            long albumId = mp3Info.getAlbumId();
            musicNameView.setText(mp3Info.getTitle());
            artistView.setText(mp3Info.getArtist());
            Bitmap bitmap = MediaUtils.getArtwork(MusicMainActivity.this, id,
                    albumId, true, false);// 获取专辑位图对象，为大图
            albumView.setImageBitmap(bitmap);
            seekBar.setMax((int) mp3Info.getDuration());
            currentTiemView.setText("0:00");
            durationView.setText(MediaUtils.formatTime(mp3Info.getDuration()));

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (notificationBroadcast != null) {
            unregisterReceiver(notificationBroadcast);
            notificationBroadcast = null;
        }
        if (playActivityBroadcast != null) {
            unregisterReceiver(playActivityBroadcast);
            playActivityBroadcast = null;
        }
        if (updateSeekbarBroadcast != null) {
            unregisterReceiver(updateSeekbarBroadcast);
            updateSeekbarBroadcast = null;
        }

        if (orderPlayBroadcast != null) {
            unregisterReceiver(orderPlayBroadcast);
            orderPlayBroadcast = null;
        }
    }
}
