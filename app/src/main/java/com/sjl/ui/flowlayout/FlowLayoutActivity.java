package com.sjl.ui.flowlayout;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sjl.activity.BaseActivity;
import com.sjl.uidemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 高仿京东商品属性筛选(流式布局)
 * <a href="https://blog.csdn.net/Zheng_Jiao/article/details/78625989">https://blog.csdn.net/Zheng_Jiao/article/details/78625989</a>
 */
public class FlowLayoutActivity extends BaseActivity {


    private ImageView ivBack;
    private TextView tvFlow;

    private FlowPopWindow flowPopWindow;
    private List<FiltrateBean> dictList = new ArrayList<>();

    @Override
    protected void initView() {
        setContentView(R.layout.activity_flowlayout);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvFlow = (TextView) findViewById(R.id.tv_flow);
        tvFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flowPopWindow = new FlowPopWindow(FlowLayoutActivity.this, dictList);
                flowPopWindow.showAsDropDown(ivBack);
                flowPopWindow.setOnConfirmClickListener(new FlowPopWindow.OnConfirmClickListener() {
                    @Override
                    public void onConfirmClick() {
                        StringBuilder sb = new StringBuilder();
                        for (FiltrateBean fb : dictList) {
                            List<FiltrateBean.Children> cdList = fb.getChildren();
                            for (int x = 0; x < cdList.size(); x++) {
                                FiltrateBean.Children children = cdList.get(x);
                                if (children.isSelected())
                                    sb.append(fb.getTypeName() + ":" + children.getValue() + "；");
                            }
                        }
                        if (!TextUtils.isEmpty(sb.toString()))
                            Toast.makeText(FlowLayoutActivity.this, sb.toString(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        initParam();
    }


    //这些是假数据，真实项目中直接接口获取添加进来，FiltrateBean对象可根据自己需求更改
    private void initParam() {
        String[] rateArr = {"1.50以下", "1.50-2.00", "2.00以上"};
        String[] colors = {"全选", "反选"};
        String[] competitionArr = {"J2联赛", "美职", "世界杯"};

        FiltrateBean fb1 = new FiltrateBean();
        fb1.setTypeName("胜平负低赔率区间");
        List<FiltrateBean.Children> childrenList = new ArrayList<>();
        for (int x = 0; x < rateArr.length; x++) {
            FiltrateBean.Children cd = new FiltrateBean.Children();
            cd.setValue(rateArr[x]);
            childrenList.add(cd);
        }
        fb1.setChildren(childrenList);

        FiltrateBean fb2 = new FiltrateBean();
        fb2.setTypeName("选择");
        List<FiltrateBean.Children> childrenList2 = new ArrayList<>();
        for (int x = 0; x < colors.length; x++) {
            FiltrateBean.Children cd = new FiltrateBean.Children();
            cd.setValue(colors[x]);
            childrenList2.add(cd);
        }
        fb2.setChildren(childrenList2);

        FiltrateBean fb3 = new FiltrateBean();
        fb3.setTypeName("赛事");
        List<FiltrateBean.Children> childrenList3 = new ArrayList<>();
        for (int x = 0; x < competitionArr.length; x++) {
            FiltrateBean.Children cd = new FiltrateBean.Children();
            cd.setValue(competitionArr[x]);
            childrenList3.add(cd);
        }
        fb3.setChildren(childrenList3);

        dictList.add(fb1);
        dictList.add(fb2);
        dictList.add(fb3);
    }
}
