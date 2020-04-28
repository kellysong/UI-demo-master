package com.sjl.view.dialog.loading;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.sjl.uidemo.R;

/**
 * 自定义等待对话框
 * Created by song on 2017/8/20.
 */

public class CustomLoadingDialog extends ProgressDialog {


    public CustomLoadingDialog(Context context) {
        super(context);
    }

    public CustomLoadingDialog(Context context, int theme) {
        super(context, theme);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    private void init(Context context) {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.dialog_loading);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void setTitle(@Nullable CharSequence title) {
        super.setTitle(title);
    }
}
