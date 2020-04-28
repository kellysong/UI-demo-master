package com.sjl.activity;


import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.sjl.view.FlikerProgressBar;
import com.sjl.uidemo.R;

/**
 * Created by song on 2017/9/10.
 */

public class FlikerProgressBarActivity extends BaseActivity implements View.OnClickListener , Runnable{
    private FlikerProgressBar flikerProgressBar;
    private FlikerProgressBar roundProgressbar;

    private Thread downLoadThread;

     private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            flikerProgressBar.setProgress(msg.arg1);
            roundProgressbar.setProgress(msg.arg1);
            if(msg.arg1 == 100){
                flikerProgressBar.finishLoad();
                roundProgressbar.finishLoad();
            }
        }
    };

    @Override
    protected void initView() {
        setContentView(R.layout.activity_fliker_progressbar);
        flikerProgressBar = (FlikerProgressBar) findViewById(R.id.flikerbar);
        roundProgressbar = (FlikerProgressBar) findViewById(R.id.round_flikerbar);

    }

    @Override
    protected void initListener() {
        flikerProgressBar.setOnClickListener(this);
        roundProgressbar.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        downLoad();
    }

    public void reLoad(View view) {

        downLoadThread.interrupt();
        // 重新加载
        flikerProgressBar.reset();
        roundProgressbar.reset();

        downLoad();
    }

    private void downLoad() {
        downLoadThread = new Thread(this);
        downLoadThread.start();
    }

    @Override
    public void onClick(View v) {
        if(!flikerProgressBar.isFinish()){
            flikerProgressBar.toggle();
            roundProgressbar.toggle();

            if(flikerProgressBar.isStop()){
                downLoadThread.interrupt();
            } else {
                downLoad();
            }

        }
    }

    @Override
    public void run() {
        try {
            while( ! downLoadThread.isInterrupted()){
                float progress = flikerProgressBar.getProgress();
                progress  += 2;
                Thread.sleep(200);
                Message message = handler.obtainMessage();
                message.arg1 = (int) progress;
                handler.sendMessage(message);
                if(progress == 100){
                    break;
                }
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
