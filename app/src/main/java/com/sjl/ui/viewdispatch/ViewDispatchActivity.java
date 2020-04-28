package com.sjl.ui.viewdispatch;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sjl.activity.BaseActivity;
import com.sjl.uidemo.R;

/**
 * 事件分发
 */
public class ViewDispatchActivity extends BaseActivity {

    @Override
    protected void initView() {
        setContentView(R.layout.view_dispatch_activity);
        final Button button = (Button) findViewById(R.id.btn_1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SIMPLE_LOGGER", "onClick execute");
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("SIMPLE_LOGGER", "onLongClick execute");
                return true;
            }
        });
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("SIMPLE_LOGGER", "onTouch execute, action " + event.getAction());
                return false;
            }
        });
        /**
         * 输出结果：
         * 02-21 01:30:46.156 1956-1956/com.example.test D/SIMPLE_LOGGER: onTouch execute, action 0
         02-21 01:30:46.256 1956-1956/com.example.test D/SIMPLE_LOGGER: onTouch execute, action 1
         02-21 01:30:46.257 1956-1956/com.example.test D/SIMPLE_LOGGER: onClick execute

         * onTouch是优先于onClick执行的，并且onTouch执行了两次，一次是ACTION_DOWN，
         * 一次是ACTION_UP(你还可能会有多次ACTION_MOVE的执行，如果你手抖了一下)。
         * 因此事件传递的顺序是先经过onTouch，再传递到onClick。
         */

        ImageView iv1 = (ImageView) findViewById(R.id.iv_1);
        iv1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    default:
                        break;
                }
                Log.d("SIMPLE_LOGGER", "ImageView----onTouch execute, action " + event.getAction());
                return false;
            }
        });

        /**
         * 输出结果:
         * SIMPLE_LOGGER: ImageView----onTouch execute, action 0
         * 因为ImageView和按钮不同，它是默认不可点击的，因此在onTouchEvent时无法进入到if的内部的事件判断
         * 直接返回了false，也就导致后面其它的action都无法执行了。
         *
         * 因此如果你有一个控件是非enable的，那么给它注册onTouch事件将永远得不到执行。
         * 对于这一类控件，如果我们想要监听它的touch事件，就必须通过在该控件中重写onTouchEvent方法来实现。
         */

        //viewgroup事件分发
        final MyLayout myLayout = (MyLayout) findViewById(R.id.my_layout);
        final Button button1 = (Button) findViewById(R.id.button1);
        final Button button2 = (Button) findViewById(R.id.button2);
        myLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("SIMPLE_LOGGER", "myLayout on touch");
                return false;
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SIMPLE_LOGGER", "You clicked button1");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SIMPLE_LOGGER", "You clicked button2");
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
