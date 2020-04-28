package com.sjl.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sjl.uidemo.R;


public class ExitDialog extends Dialog {
    private Context mContext;
    private Button mConfirm;
    private Button mCancel;
    private OnCloseListener listener;

    public ExitDialog(Context context) {
        super(context, R.style.ExitDialog);
        mContext=context;
    }

    public ExitDialog(Context context, int theme) {
        super(context, theme);
        mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_qq);//设置布局
        this.setCanceledOnTouchOutside(false); //设置为点击对话框之外的区域对话框不消失
        mConfirm= (Button) findViewById(R.id.dialog_confirm);
        mCancel= (Button) findViewById(R.id.dialog_cancel);
        //设置事件
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onOkClick(ExitDialog.this);
                }
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onCancelClick(ExitDialog.this);
                }
            }
        });

    }

    /**
     * dialog监听事件
     * @param listener
     */
    public ExitDialog addDialogListener(OnCloseListener listener) {
        this.listener = listener;
        return this;
    }


    public interface OnCloseListener{
        void onOkClick(Dialog dialog);
        void onCancelClick(Dialog dialog);
    }
}
