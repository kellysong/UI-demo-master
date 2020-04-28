package com.sjl.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjl.uidemo.R;

/**
 * Created by song on 2017/8/20.
 */

public class LazyPageFragment extends BaseFragment {
    public static final String ARGS_PAGE = "args_page";
    private int mPage;

    /** 标志位，标志已经初始化完成 */
    private boolean isPrepared;
    /** 是否已被加载过一次，第二次就不再去请求数据了 */
    private boolean mHasLoadedOnce;
    View view;

    public static LazyPageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        LazyPageFragment fragment = new LazyPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(view == null) {
            view = inflater.inflate(R.layout.tablayout_fragment_page,container,false);
            TextView textView = (TextView) view.findViewById(R.id.textView);
            //获得索引值
            Bundle bundle = getArguments();
            if (bundle != null) {
                mPage = bundle.getInt(ARGS_PAGE);
                textView.setText("第"+mPage+"页");
                Log.d("PageFragment",mPage+"");
            }
            isPrepared = true;
            lazyLoad();
        }
        //因为共用一个Fragment视图，所以当前这个视图已被加载到Activity中，必须先清除后再加入Activity
        ViewGroup parent = (ViewGroup)view.getParent();
        if(parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    /**
     * 当Fragment对用户可见,才加载数据
     */
    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        Log.d("PageFragment","第一次加载："+mPage);
        mHasLoadedOnce = true;
    }
}
