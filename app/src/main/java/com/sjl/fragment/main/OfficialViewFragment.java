package com.sjl.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sjl.activity.ButtonStyleActivity;
import com.sjl.activity.RecyclerView2Activity;
import com.sjl.activity.RecyclerViewActivity;
import com.sjl.activity.RecyclerViewGroupingActivity;
import com.sjl.activity.SettingActivity;
import com.sjl.activity.SnackBarActivity;
import com.sjl.activity.TabLayoutActivity;
import com.sjl.activity.TableListViewActivity;
import com.sjl.activity.TextViewActivity;
import com.sjl.activity.TitleListViewActivity;
import com.sjl.activity.WeChatMainActivity;
import com.sjl.fragment.BaseFragment;
import com.sjl.ui.expandableListView.ExpandableListViewActivity;
import com.sjl.util.Utils;
import com.sjl.uidemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 官方控件
 *
 * @author Kelly
 * @version 1.0.0
 * @filename OfficialViewFragment.java
 * @time 2018/3/16 10:07
 * @copyright(C) 2018 深圳市北辰德科技股份有限公司
 */
public class OfficialViewFragment extends BaseFragment implements View.OnClickListener{
    @Bind(R.id.btn_test1_1)
    Button btn_test1_1;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.offcialview_fragment,container,false);
        ButterKnife.bind(this, view);
        btn_test1_1.setOnClickListener(this);
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
        return view;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_test1_1:
                Utils.openNewActivity(getActivity(),RecyclerView2Activity.class);
                break;
            case R.id.btn_test1:
                Utils.openNewActivity(getActivity(),RecyclerViewActivity.class);
                break;
            case R.id.btn_test2:
                Utils.openNewActivity(getActivity(),TabLayoutActivity.class);
                break;
            case R.id.btn_test3:
                Utils.openNewActivity(getActivity(),WeChatMainActivity.class);
                break;
            case R.id.btn_test4:
                Utils.openNewActivity(getActivity(),SettingActivity.class);
                break;
            case R.id.btn_test5:
                Utils.openNewActivity(getActivity(),SnackBarActivity.class);
                break;
            case R.id.btn_test6:
                Utils. openNewActivity(getActivity(),TableListViewActivity.class);
                break;
            case R.id.btn_test7:
                Utils.openNewActivity(getActivity(),TitleListViewActivity.class);
                break;
            case R.id.btn_test8:
                Utils.openNewActivity(getActivity(),ButtonStyleActivity.class);
                break;
            case R.id.btn_test9:
                Utils.openNewActivity(getActivity(),RecyclerViewGroupingActivity.class);
                break;
            case R.id.btn_test10:
                Utils.openNewActivity(getActivity(),ExpandableListViewActivity.class);
                break;
            case R.id.btn_test11:
                Utils.openNewActivity(getActivity(),TextViewActivity.class);
                break;
            default:
                break;
        }
    }
}
