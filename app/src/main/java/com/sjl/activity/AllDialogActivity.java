package com.sjl.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sjl.uidemo.R;
import com.sjl.util.Utils;
import com.sjl.view.DownLoadProgressbar;
import com.sjl.view.dialog.BottomPopupWindow;
import com.sjl.view.dialog.CommomDialog;
import com.sjl.view.dialog.ExitDialog;
import com.sjl.view.dialog.ExitDialog.OnCloseListener;
import com.sjl.view.dialog.MenuPopupWindow;
import com.sjl.view.dialog.ShareDialog;


public class AllDialogActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_dialog);
        initView();
    }

    private void initView(){
        setTitle("弹窗");

        findViewById(R.id.txt).setOnClickListener(this);
        findViewById(R.id.tv_dialog).setOnClickListener(this);
        findViewById(R.id.tv_bottom_dialog).setOnClickListener(this);
        findViewById(R.id.tv_bottom_dialog2).setOnClickListener(this);


        findViewById(R.id.tv_mul_choice_dialog).setOnClickListener(this);

        findViewById(R.id.share).setOnClickListener(this);
        menu =(TextView)findViewById(R.id.menu);
        menu.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt:
                //弹出提示框
                new CommomDialog(this, R.style.dialog, "您确定删除此信息？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if(confirm){
                            Toast.makeText(AllDialogActivity.this,"点击确定", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    }
                }).setTitle("提示").show();
                break;
            case R.id.tv_dialog:
                //弹出提示框
                new ExitDialog(this).addDialogListener(new OnCloseListener() {
                    @Override
                    public void onOkClick(Dialog dialog) {//确定
                        Toast.makeText(AllDialogActivity.this,"onOkClick", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelClick(Dialog dialog) {//取消
                        Toast.makeText(AllDialogActivity.this,"onCancelClick", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.tv_bottom_dialog:
                //弹出提示框
                BottomPopupWindow bottomPopupWindow = new BottomPopupWindow(this);
                bottomPopupWindow.showPopWindow();
                break;
            case R.id.tv_bottom_dialog2:
                Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
                View contentView = LayoutInflater.from(this).inflate(R.layout.bottom_dialog, null);
                bottomDialog.setContentView(contentView);
                ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
                layoutParams.width = getResources().getDisplayMetrics().widthPixels;
                contentView.setLayoutParams(layoutParams);
                bottomDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//背景透明
                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                bottomDialog.show();
                break;
            case R.id.tv_mul_choice_dialog:
                //创建对话框实例
                final AlertDialog  dlg = new AlertDialog.Builder(this).create();
                dlg.show();//显示对话框
                Window window = dlg.getWindow();//对话框窗口
                window.setGravity(Gravity.CENTER);//设置对话框显示在屏幕中间
                window.setWindowAnimations(R.style.dialog_style);//添加动画
                window.setContentView(R.layout.dialog_layout);//设置对话框的布局文件
                //获取对话框确定和取消按钮
                Button btn_determine = (Button) window.findViewById(R.id.btn_determine);
                Button  btn_cancel = (Button) window.findViewById(R.id.btn_cancel);
                //确定
                btn_determine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dlg.dismiss();//关闭对话框
                        showDownLoadDialog();
                    }
                });

                //取消
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dlg.dismiss();//关闭对话框
                    }
                });
                break;
            case R.id.menu:
                MenuPopupWindow menuPopupWindow = new MenuPopupWindow(this, new MenuPopupWindow.OnItemClickListener() {
                    @Override
                    public void onItemClick(PopupWindow popupWindow, int position) {

                        popupWindow.dismiss();
                    }
                });
                menuPopupWindow.showAsDropDown(menu, -200, 40);
                break;
            case R.id.share:
                new ShareDialog(AllDialogActivity.this, R.style.dialog, new ShareDialog.OnItemClickListener() {
                    @Override
                    public void onClick(Dialog dialog, int position) {
                        dialog.dismiss();
                        switch (position){
                            case 1:
                                Utils.toast(AllDialogActivity.this,"微信好友");
                                break;
                            case 2:
                                Utils.toast(AllDialogActivity.this,"朋友圈");
                                break;
                            case 3:
                                Utils.toast(AllDialogActivity.this,"QQ");
                                break;
                            case 4:
                                Utils.toast(AllDialogActivity.this,"微博");
                                break;
                        }
                    }
                }).show();
                break;
        }
    }
    private TextView mSize;
    private DownLoadProgressbar mProgress;
    private int max = 100;
    private int current = 0;

    private void showDownLoadDialog() {
        final AlertDialog  dlg = new AlertDialog.Builder(this).create();
        dlg.show();//显示对话框
        Window window = dlg.getWindow();//对话框窗口
        window.setGravity(Gravity.CENTER);//设置对话框显示在屏幕中间
//        window.setWindowAnimations(R.style.dialog_style);//添加动画
        window.setContentView(R.layout.cancel_dialog_layout);//设置对话框的布局文件
        mProgress = (DownLoadProgressbar) window.findViewById(R.id.dp_game_progress);
        mSize = (TextView) window.findViewById(R.id.tv_size);
        //获取对话框确定和取消按钮
        Button  btn_cancel = (Button) window.findViewById(R.id.btn_cancel);
        //取消
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();//关闭对话框
            }
        });
        mSize.setText(current + "/" + max);
        current= 0;
        start();


    }
    public void start() {
        if (current <= max) {
            mSize.setText(current + "/" + max);
            mProgress.setMaxValue(max);
            mProgress.setCurrentValue(current);
            handler.postDelayed(runnable, 100);
        } else {
            handler.removeCallbacks(runnable);
        }

    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            current = current + 1;
            start();
        }
    };

}
