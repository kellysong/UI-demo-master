package com.sjl.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjl.activity.AsyncTaskActivity;
import com.sjl.activity.CanvasDemoActivity;
import com.sjl.fragment.BaseFragment;
import com.sjl.uidemo.R;
import com.sjl.ui.activityservice.ActivityAndServiceCommunicationActivity;
import com.sjl.ui.animation.ObjectAnimatorActivity;
import com.sjl.ui.viewdispatch.ViewConflictActivity;
import com.sjl.ui.viewdispatch.ViewDispatchActivity;
import com.sjl.util.Utils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 基础知识
 *
 * @author Kelly
 * @version 1.0.0
 * @filename BaseKnowledgeFragment.java
 * @time 2018/3/16 10:10
 * @copyright(C) 2018 song
 */
public class BaseKnowledgeFragment extends BaseFragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.baseknowledge_fragment,container,false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.btn_test1,R.id.btn_test2,R.id.btn_test3,R.id.btn_test4,
            R.id.btn_test5,R.id.btn_objectAnimator})
    public void onClickBaseKnowledge(View v) {
        switch (v.getId()){
            case R.id.btn_test1:
                Utils.openNewActivity(getActivity(),AsyncTaskActivity.class);
                break;
            case R.id.btn_test2:
                Utils.openNewActivity(getActivity(),CanvasDemoActivity.class);
                break;
            case R.id.btn_test3:
                Utils.openNewActivity(getActivity(),ActivityAndServiceCommunicationActivity.class);
                break;
            case R.id.btn_test4:
                Utils.openNewActivity(getActivity(),ViewConflictActivity.class);
                break;
            case R.id.btn_test5:
                Utils.openNewActivity(getActivity(),ViewDispatchActivity.class);
                break;
            case R.id.btn_objectAnimator:
                Utils.openNewActivity(getActivity(),ObjectAnimatorActivity.class);
                break;
            default:
                break;
        }
    }
}
