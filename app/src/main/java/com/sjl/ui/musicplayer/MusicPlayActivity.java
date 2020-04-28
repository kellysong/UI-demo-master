package com.sjl.ui.musicplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.sjl.uidemo.R;
import com.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename MusicPlayActivity.java
 * @time 2018/10/5 19:00
 * @copyright(C) 2018 xxx有限公司
 */
public class MusicPlayActivity extends AppCompatActivity implements View.OnClickListener {
    TextView musicNameView, artistView;
    LinearLayout returnBack;
    ImageView stop, previousMusic, playOrPauseButton, nextMusic, musicList;
    SeekBar seekBar;
    TextView currentTimeView, durationView;
    static int location = 0;
    static Boolean isPlaying = false;
    Mp3Info mp3Info;
    List<Mp3Info> mp3InfoList = new ArrayList<>();
    UpdateSeekbarBroadcast updateSeekbarBroadcast;
    OrderPlayBroadcast orderPlayBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
        //获得歌曲列表信息
        mp3InfoList = MediaUtils.getMp3InfoList(MusicPlayActivity.this);
        //获得各控件实例
        musicNameView = (TextView) findViewById(R.id.playActivity_musicName);
        artistView = (TextView) findViewById(R.id.playActivity_artist);
        stop = (ImageView) findViewById(R.id.stop);
        previousMusic = (ImageView) findViewById(R.id.previous);
        playOrPauseButton = (ImageView) findViewById(R.id.play_pause);
        nextMusic = (ImageView) findViewById(R.id.next_music);
        musicList = (ImageView) findViewById(R.id.music_menu);
        returnBack = (LinearLayout) findViewById(R.id.playActivity_return);
        seekBar = (SeekBar) findViewById(R.id.playActivity_seekBar);
        currentTimeView = (TextView) findViewById(R.id.current_position);
        durationView = (TextView) findViewById(R.id.final_position);
        //从MainActivity中传递过来的数据
        Intent intent1 = getIntent();
        location = intent1.getIntExtra("location", 0);
        isPlaying = intent1.getBooleanExtra("state", false);
        //根据传递过来的数据更新UI视图
        if (mp3InfoList != null && mp3InfoList.size() > 0){
            seekBar.setMax((int) (mp3InfoList.get(location).getDuration()));
            currentTimeView.setText("0:00");
            durationView.setText(MediaUtils.formatTime(mp3InfoList.get(location).getDuration()));
            musicNameView.setText(mp3InfoList.get(location).getTitle());
            artistView.setText(mp3InfoList.get(location).getArtist());
            durationView.setText(MediaUtils.formatTime(mp3InfoList.get(location).getDuration()));
        }

        if (isPlaying)
            playOrPauseButton.setImageResource(R.drawable.btn_pause);
        else playOrPauseButton.setImageResource(R.drawable.btn_play);
        //注册seekBar监听器
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.musicplayer.update_seekbar");
        updateSeekbarBroadcast = new UpdateSeekbarBroadcast();
        registerReceiver(updateSeekbarBroadcast, filter);
        //注册后台顺序播放的监听器
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("com.example.musicplayer.order_play");
        orderPlayBroadcast = new OrderPlayBroadcast();
        registerReceiver(orderPlayBroadcast, filter1);
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
                Intent intent = new Intent(MusicPlayActivity.this, MusicService.class);
                intent.putExtra("tag", 2);
                intent.putExtra("progress", progress);
                startService(intent);
            }
        });
        //设置各按钮点击事件
        stop.setOnClickListener(this);
        previousMusic.setOnClickListener(this);
        playOrPauseButton.setOnClickListener(this);
        nextMusic.setOnClickListener(this);
        musicList.setOnClickListener(this);
        returnBack.setOnClickListener(this);
    }


    //监听来自Service的SeekBar变化
    class UpdateSeekbarBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int position = intent.getIntExtra("position", 0);
            currentTimeView.setText(MediaUtils.formatTime(position));
            seekBar.setProgress(position);

        }
    }

    //接收来自后台自动播放的广播以更新UI
    class OrderPlayBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.d("MusicPlayActivity receive broadcast from service orderly play to update UI ");
            location = intent.getIntExtra("location", -1);
            mp3Info = mp3InfoList.get(location);
            musicNameView.setText(mp3Info.getTitle());
            artistView.setText(mp3Info.getArtist());
            seekBar.setMax((int) mp3Info.getDuration());
            currentTimeView.setText("0:00");
            durationView.setText(MediaUtils.formatTime(mp3Info.getDuration()));
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent("com.example.musicplayer.react_to_playactiity");
        switch (view.getId()) {
            case R.id.stop: {
                intent.putExtra("tag1", 3);
            }
            break;
            case R.id.previous: {
                isPlaying = !isPlaying;
                previousMusic();
                intent.putExtra("tag1", 1);
            }
            break;
            case R.id.play_pause: {
                isPlaying = !isPlaying;
                if (isPlaying) {
                    playOrPauseButton.setImageResource(R.drawable.btn_pause);
                } else {
                    playOrPauseButton.setImageResource(R.drawable.btn_play);
                }
                intent.putExtra("tag1", 0);
                intent.putExtra("state", isPlaying);

            }
            break;
            case R.id.next_music: {
                nextMusic();
                intent.putExtra("tag1", 2);
            }
            break;
            case R.id.music_menu:
                Log.d("MusicPlayActivity", "press the button music_menu");
                showMusicList();
                break;
            case R.id.playActivity_return:
                finish();
                break;
            default:
                break;

        }
        sendBroadcast(intent);
    }

    //根据点击事件更新UI视图
    public void previousMusic() {
        if ((--location) >= 0) {

        } else {
            location = mp3InfoList.size() - 1;
        }
        seekBar.setMax((int) (mp3InfoList.get(location).getDuration()));
        currentTimeView.setText("0:00");
        durationView.setText(MediaUtils.formatTime(mp3InfoList.get(location).getDuration()));
        musicNameView.setText(mp3InfoList.get(location).getTitle());
        artistView.setText(mp3InfoList.get(location).getArtist());
        playOrPauseButton.setImageResource(R.drawable.btn_pause);
    }

    public void nextMusic() {
        if ((++location) < mp3InfoList.size()) {

        } else {
            location = 0;
        }
        seekBar.setMax((int) (mp3InfoList.get(location).getDuration()));
        currentTimeView.setText("0:00");
        durationView.setText(MediaUtils.formatTime(mp3InfoList.get(location).getDuration()));
        musicNameView.setText(mp3InfoList.get(location).getTitle());
        artistView.setText(mp3InfoList.get(location).getArtist());
        playOrPauseButton.setImageResource(R.drawable.btn_pause);
    }

    //通过AlertDialog显示歌曲列表
    public void showMusicList() {
        Log.d("MusicPlayActivity", "showMusicList start");
        LinearLayout linearLayoutMain = new LinearLayout(this);//自定义一个布局文件
        linearLayoutMain.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        Log.d("MusicPlayActivity", "LinearLayout created");
        ListView listView = new ListView(this);//this为获取当前的上下文
        listView.setFadingEdgeLength(0);
        List<Map<String, String>> musicList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < mp3InfoList.size(); i++) {
            Map<String, String> item = new HashMap<String, String>();
            item.put("musicName", mp3InfoList.get(i).getTitle().toString());
            item.put("artist", mp3InfoList.get(i).getArtist().toString());
            musicList.add(item);
        }
        Log.d("MusicPlayActivity", "LinearLayout created and itialized");

        SimpleAdapter adapter = new SimpleAdapter(MusicPlayActivity.this, musicList, R.layout.music_item
                , new String[]{"musicName", "artist"}
                , new int[]{R.id.music_item_musicName, R.id.music_item_artist});
        Log.d("MusicPlayActivity", "SimpleAdapter created");
        listView.setAdapter(adapter);
        linearLayoutMain.addView(listView);
        Log.d("MusicPlayActivity", "listView was added to the linearLayout");
        final AlertDialog dialog = new AlertDialog.Builder(this).setTitle("歌曲列表").setView(linearLayoutMain)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create();
        Log.d("MusicPlayActivity", "Dialog created");
        dialog.show();
        Log.d("MusicPlayActivity", "Dialog showed");
        //响应子项点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //更新自身UI
                musicNameView.setText(mp3InfoList.get(i).getTitle());
                artistView.setText(mp3InfoList.get(i).getArtist());
                durationView.setText(MediaUtils.formatTime(mp3InfoList.get(i).getDuration()));
                //发送广播附带歌曲下标给MainActivity来控制服务播放歌曲
                Intent intent = new Intent("com.example.musicplayer.react_to_playactiity");
                intent.putExtra("tag1", 4);
                intent.putExtra("location", i);
                sendBroadcast(intent);
                Log.d("MusicPlayActivity", "broadcast sended");
                dialog.cancel();
                Log.d("MusicPlayActivity", "Dialog canceled");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

