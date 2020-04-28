package com.sjl.view.dialog;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.sjl.uidemo.R;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename BottomPopupWindow.java
 * @time 2019/8/19 10:52
 * @copyright(C) 2019 song
 */
public class BottomPopupWindow implements View.OnClickListener {


    private View contentView;
    private PopupWindow popupWindow;
    private Activity activity;

    public BottomPopupWindow(Activity activity) {
        this.activity = activity;
        initPopupWindow();
    }

    public void initPopupWindow() {
        //要在布局中显示的布局
        contentView = LayoutInflater.from(activity).inflate(R.layout.bottom_dialog, null, false);
        //实例化PopupWindow并设置宽高
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //获取焦点，控制点击返回键消失
        popupWindow.setFocusable(true);

        //进入退出的动画
       popupWindow.setAnimationStyle(R.style.popwin_anim_style);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.GRAY));//设置popupWindow的背景
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //popupwindow消失时使背景不透明
                bgAlpha(1f);
            }
        });
        contentView.findViewById(R.id.open_album).setOnClickListener(this);
        contentView.findViewById(R.id.open_from_camera).setOnClickListener(this);
        contentView.findViewById(R.id.cancel).setOnClickListener(this);

    }

    public void showPopWindow() {
        bgAlpha(0.5f);
        View rootview = activity.getWindow().getDecorView();
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }


    /**
     * 弹出的窗口是否覆盖状态栏
     *
     * @param bgAlpha 透明度
     */
    private void bgAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_album:
                break;
            case R.id.open_from_camera:
                break;
            case R.id.cancel:
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;
        }
    }

}