package com.sjl.activity;


import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.sjl.view.dialog.loading.DialogThridUtils;
import com.sjl.view.dialog.loading.WeiboDialogUtils;
import com.sjl.uidemo.R;

/**
 * Created by song on 2017/10/22.
 */

public class DialogLoadingActivity extends BaseActivity {
    private Dialog mDialog;
    private Dialog mWeiboDialog;
    private Button btn_show_weibo_loading;
    private Button btn_show_thrid_loading;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    DialogThridUtils.closeDialog(mDialog);
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    break;
            }
        }
    };
    @Override
    protected void initView() {
        setContentView(R.layout.activity_dialog_loading);
        btn_show_weibo_loading = (Button) findViewById(R.id.btn_show_weibo_loading);
        btn_show_thrid_loading = (Button) findViewById(R.id.btn_show_thrid_loading);
        btn_show_weibo_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeiboDialog = WeiboDialogUtils.createLoadingDialog(DialogLoadingActivity.this, "加载中...");
                mHandler.sendEmptyMessageDelayed(1, 2000);
            }
        });

        btn_show_thrid_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = DialogThridUtils.showWaitDialog(DialogLoadingActivity.this, "加载中...", false, true);
                mHandler.sendEmptyMessageDelayed(1, 2000);
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
