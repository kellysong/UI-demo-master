package com.sjl.activity;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.sjl.uidemo.R;
import com.sjl.util.HighLightKeyWordUtils;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename TextViewActivity.java
 * @time 2019/3/1 15:48
 * @copyright(C) 2019 song
 */
public class TextViewActivity extends BaseActivity {
    TextView textView;
    boolean flag = false;

    @Override
    protected void initView() {
        setContentView(R.layout.text_view_activity);
        ((TextView) findViewById(R.id.tv_test2)).setText(HighLightKeyWordUtils.getBackgroudKeyWord(
                new int[]{Color.parseColor("#ffffff"), Color.parseColor("#ffffff")},
                new int[]{Color.parseColor("#febc48"), Color.parseColor("#f13b2f")},
                "中华人民共和国成立了,伟大的中华人民共和国", new String[]{"中华", "伟大"}));
    }


    @Override
    protected void initListener() {

        textView = (TextView) findViewById(R.id.tv_test);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!flag) {
//                    textView.setShadowLayer(5, 0, 0, Color.parseColor("#000000"));
//                    flag = true;
//                } else {
//                    textView.setShadowLayer(0, 0, 0, Color.parseColor("#ffffff"));
//                    flag = false;
//                }
//            }
//        });
        textView.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                int what = event.getAction();
                switch (what) {
                    case MotionEvent.ACTION_HOVER_ENTER://鼠标进入view
                        textView.setShadowLayer(0, 0, 0, Color.parseColor("#ffffff"));
                        break;
                    case MotionEvent.ACTION_HOVER_MOVE://鼠标在view上  
                        textView.setShadowLayer(0, 0, 0, Color.parseColor("#ffffff"));
                        break;
                    case MotionEvent.ACTION_HOVER_EXIT://鼠标离开view  
                        System.out.println("bottom ACTION_HOVER_EXIT");
                        textView.setShadowLayer(2, 0, 0, Color.parseColor("#000000"));
                        break;
                }

                return false;
            }
        });
    }

    @Override
    protected void initData() {

    }

    public void test(View view) {
        if (!flag) {
            textView.setShadowLayer(8, -10, 10, Color.parseColor("#BABBBA"));//黑色
            flag = true;
        } else {
            textView.setShadowLayer(0, 0, 0, Color.parseColor("#ffffff"));
            flag = false;
        }

    }
}
