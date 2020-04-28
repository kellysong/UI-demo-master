package com.sjl.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sjl.activity.AllDialogActivity;
import com.sjl.activity.CircleProgressBarActivity;
import com.sjl.activity.DatePickerActivity;
import com.sjl.activity.DialogLoadingActivity;
import com.sjl.activity.FlikerProgressBarActivity;
import com.sjl.activity.GalleryActivity;
import com.sjl.activity.HorizontalTimeLineActivity;
import com.sjl.activity.LeftCategoryActivity;
import com.sjl.activity.NotificationDownloadActivity;
import com.sjl.activity.ScrollWebViewActivity;
import com.sjl.activity.SearchContentActivity;
import com.sjl.activity.VerticalTimeLineActivity;
import com.sjl.fragment.BaseFragment;
import com.sjl.uidemo.R;
import com.sjl.ui.customseekbar.SeekBarActivity;
import com.sjl.ui.flowlayout.FlowLayoutActivity;
import com.sjl.ui.gridviewpage.GridViewPageActivity;
import com.sjl.ui.wechattab.WeChatTabActivity;
import com.sjl.util.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 自定义View
 *
 * @author Kelly
 * @version 1.0.0
 * @filename CustomViewFragment.java
 * @time 2018/3/16 10:07
 * @copyright(C) 2018 深圳市北辰德科技股份有限公司
 */
public class CustomViewFragment extends BaseFragment implements View.OnClickListener{
    @Bind(R.id.btn_test0)
    Button btn_test0;
    @Bind(R.id.btn_test1)
    Button btn_test1;
    @Bind(R.id.btn_test2)
    Button btn_test2;
    @Bind(R.id.btn_test3)
    Button btn_test3;
    @Bind(R.id.btn_test4)
    Button btn_test4;
    @Bind(R.id.btn_test5)
    Button btn_test5;
    @Bind(R.id.btn_test6)
    Button btn_test6;
    @Bind(R.id.btn_test7)
    Button btn_test7;
    @Bind(R.id.btn_test8)
    Button btn_test8;
    @Bind(R.id.btn_test9)
    Button btn_test9;
    @Bind(R.id.btn_test10)
    Button btn_test10;
    @Bind(R.id.btn_test11)
    Button btn_test11;
    @Bind(R.id.btn_test12)
    Button btn_test12;
    @Bind(R.id.btn_test13)
    Button btn_test13;
    @Bind(R.id.btn_test14)
    Button btn_test14;
    @Bind(R.id.btn_test15)
    Button btn_test15;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customview_fragment,container,false);
        ButterKnife.bind(this, view);
        btn_test0.setOnClickListener(this);
        btn_test1.setOnClickListener(this);
        btn_test2.setOnClickListener(this);
        btn_test3.setOnClickListener(this);
        btn_test4.setOnClickListener(this);
        btn_test5.setOnClickListener(this);
        btn_test6.setOnClickListener(this);
        btn_test7.setOnClickListener(this);
        btn_test8.setOnClickListener(this);
        btn_test9.setOnClickListener(this);
        btn_test10.setOnClickListener(this);
        btn_test11.setOnClickListener(this);
        btn_test12.setOnClickListener(this);
        btn_test13.setOnClickListener(this);
        btn_test14.setOnClickListener(this);
        btn_test15.setOnClickListener(this);
        return view;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_test0:
                Utils.openNewActivity(getActivity(),WeChatTabActivity.class);
                break;
            case R.id.btn_test1:
                Utils.openNewActivity(getActivity(),HorizontalTimeLineActivity.class);
                break;
            case R.id.btn_test2:
                Utils.openNewActivity(getActivity(),VerticalTimeLineActivity.class);
                break;
            case R.id.btn_test3:
                Utils.openNewActivity(getActivity(),AllDialogActivity.class);
                break;
            case R.id.btn_test4:
                Utils.openNewActivity(getActivity(),DatePickerActivity.class);
                break;
            case R.id.btn_test5:
                Utils.openNewActivity(getActivity(),GalleryActivity.class);
                break;
            case R.id.btn_test6:
                Utils.openNewActivity(getActivity(),CircleProgressBarActivity.class);
                break;
            case R.id.btn_test7:
                Utils.openNewActivity(getActivity(),SearchContentActivity.class);
                break;
            case R.id.btn_test8:
                Utils.openNewActivity(getActivity(),FlikerProgressBarActivity.class);
                break;
            case R.id.btn_test9:
                Utils.openNewActivity(getActivity(),NotificationDownloadActivity.class);
                break;

            case R.id.btn_test10:
                Utils.openNewActivity(getActivity(),DialogLoadingActivity.class);
                break;
            case R.id.btn_test11:
                Utils.openNewActivity(getActivity(),LeftCategoryActivity.class);
                break;
            case R.id.btn_test12:
                Utils.openNewActivity(getActivity(),GridViewPageActivity.class);
                break;
            case R.id.btn_test13:
                Utils.openNewActivity(getActivity(),FlowLayoutActivity.class);
                break;
            case R.id.btn_test14:
                Utils.openNewActivity(getActivity(),ScrollWebViewActivity.class);
                break;
            case R.id.btn_test15:
                Utils.openNewActivity(getActivity(),SeekBarActivity.class);
                break;
            default:
                break;
        }
    }
}
