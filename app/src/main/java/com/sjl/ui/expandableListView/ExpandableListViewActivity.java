package com.sjl.ui.expandableListView;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.sjl.activity.BaseActivity;
import com.sjl.entity.ChildEntity;
import com.sjl.entity.ParentEntity;
import com.sjl.uidemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 二级菜单listview
 */
public class ExpandableListViewActivity extends BaseActivity {


    private ExpandableListView listview;
    private MyExpandableListViewAdapter adapter;

    private Map<String, List<ChildEntity>> datasets = new HashMap<>();

    private String[] PList;

    List<ParentEntity> parentEntities;
    List<ChildEntity> childEntities;
    TextView parentText;
    private int lastClick = -1;//上一次点击的group的position


    @Override
    protected void initView() {
        setContentView(R.layout.expandable_layout);
        listview = (ExpandableListView) findViewById(R.id.expandablelistview);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        initialData();


        adapter = new MyExpandableListViewAdapter();
        listview.setAdapter(adapter);

//        Group点击事件，点击一个Group隐藏其他的(只显示一个)
        listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if (lastClick == -1) {
                    listview.expandGroup(i);
                }
                if (lastClick != -1 && lastClick != i) {
                    listview.collapseGroup(lastClick);
                    listview.expandGroup(i);
                } else if (lastClick == i) {
                    if (listview.isGroupExpanded(i)) {
                        listview.collapseGroup(i);
                    } else if (!listview.isGroupExpanded(i)) {
                        listview.expandGroup(i);
                    }
                }
                lastClick = i;
                return true;
            }
        });
//        子项点击事件
        listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view,
                                        int parentPos, int childPos, long l) {
               /* Toast.makeText(ExpandableListViewActivity.this,
                        dataset.get(parentList[parentPos]).get(childPos), Toast.LENGTH_SHORT).show();*/
                Toast.makeText(ExpandableListViewActivity.this,
                        datasets.get(PList[parentPos]).get(childPos).getCinema_name() + "影院Id：" +
                                datasets.get(PList[parentPos]).get(childPos).getId() + "区域Id："
                                + datasets.get(PList[parentPos]).get(childPos).getArea_id(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    /**
     * 初始化数据
     */
    private void initialData() {
        parentEntities = new ArrayList<>();
        childEntities = new ArrayList<>();
        ChildEntity childEntity;
        /*json数据*/
//        String he = "[{\"id\":117,\"area_name\":\"昌平区\",\"cinemas\":[{\"id\":\"1\",\"cinema_name\":\"DMC昌平保利影剧院\",\"cinema_address\":\"昌平区鼓楼南街佳莲时代广场4楼\",\"area_id\":\"117\",\"area_name\":\"昌平区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14205106672446.jpg\"},{\"id\":\"2\",\"cinema_name\":\"保利国际影城-龙旗广场店\",\"cinema_address\":\"昌平区黄平路19号院3号楼龙旗购物中心3楼\",\"area_id\":\"117\",\"area_name\":\"昌平区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14203529883434.jpg\"}]},{\"id\":109,\"area_name\":\"朝阳区\",\"cinemas\":[{\"id\":\"7\",\"cinema_name\":\"北京剧院\",\"cinema_address\":\"朝阳区安慧里三区10号\",\"area_id\":\"109\",\"area_name\":\"朝阳区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14207675478321.jpg\"},{\"id\":\"8\",\"cinema_name\":\"朝阳剧场\",\"cinema_address\":\"朝阳区东三环北路36号\",\"area_id\":\"109\",\"area_name\":\"朝阳区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14203512065410.jpg\"},{\"id\":\"10\",\"cinema_name\":\"劲松电影院\",\"cinema_address\":\"朝阳区劲松中街404号\",\"area_id\":\"109\",\"area_name\":\"朝阳区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14213800192810.jpg\"}]}]";
        String he = "[{\"id\":117,\"area_name\":\"昌平区\",\"cinemas\":[{\"id\":\"1\",\"cinema_name\":\"DMC昌平保利影剧院\",\"cinema_address\":\"昌平区鼓楼南街佳莲时代广场4楼\",\"area_id\":\"117\",\"area_name\":\"昌平区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14205106672446.jpg\"},{\"id\":\"2\",\"cinema_name\":\"保利国际影城-龙旗广场店\",\"cinema_address\":\"昌平区黄平路19号院3号楼龙旗购物中心3楼\",\"area_id\":\"117\",\"area_name\":\"昌平区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14203529883434.jpg\"}]},{\"id\":109,\"area_name\":\"朝阳区\",\"cinemas\":[{\"id\":\"7\",\"cinema_name\":\"北京剧院\",\"cinema_address\":\"朝阳区安慧里三区10号\",\"area_id\":\"109\",\"area_name\":\"朝阳区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14207675478321.jpg\"},{\"id\":\"8\",\"cinema_name\":\"朝阳剧场\",\"cinema_address\":\"朝阳区东三环北路36号\",\"area_id\":\"109\",\"area_name\":\"朝阳区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14203512065410.jpg\"},{\"id\":\"10\",\"cinema_name\":\"劲松电影院\",\"cinema_address\":\"朝阳区劲松中街404号\",\"area_id\":\"109\",\"area_name\":\"朝阳区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14213800192810.jpg\"}]},{\"id\":110,\"area_name\":\"丰台区\",\"cinemas\":[{\"id\":\"67\",\"cinema_name\":\"保利国际影城-大峡谷店\",\"cinema_address\":\"丰台区南三环西路首地大峡谷购物中心5楼\",\"area_id\":\"110\",\"area_name\":\"丰台区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14203575287090.jpg\"},{\"id\":\"68\",\"cinema_name\":\"保利国际影城-万源店\",\"cinema_address\":\"丰台区东高地万源北路航天万源广场5楼\",\"area_id\":\"110\",\"area_name\":\"丰台区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14203530487616.jpg\"},{\"id\":\"69\",\"cinema_name\":\"恒业国际影城-北京六里桥店\",\"cinema_address\":\"北京市丰台区万丰路68号银座和谐广场购物中心5层\\t\",\"area_id\":\"110\",\"area_name\":\"丰台区\",\"logo\":\"\"}]}]";

//        第一步首先将PList填充数据，也就是解析area_name,将json解析成List集合
        parentEntities = JSON.parseArray(he, ParentEntity.class);

//        遍历集合，将area_name填充到PList集合中
        PList = new String[parentEntities.size()];
        for (int i = 0; i < PList.length; i++) {
            Log.e("city", "parentEntities.size():" + parentEntities.size());
            PList[i] = parentEntities.get(i).getArea_name();
            Log.e("plist", "parentEntites.getName:" + parentEntities.get(i).getArea_name() + "=====PList[i]" + PList[i]);
            for (int j = 0; j < parentEntities.get(i).getCinemas().size(); j++) {
                datasets.put(PList[i], parentEntities.get(i).getCinemas());//自相集合
                Log.e("datasets", "子项名称：" + String.valueOf(parentEntities.get(i).getCinemas().get(j).getCinema_name()));
                Log.e("datasets", datasets.get(PList[i]).get(j).getCinema_name());
            }

        }

       /* childrenList1.add(parentList[0] + "-" + "first");
        childrenList1.add(parentList[0] + "-" + "second");
        childrenList1.add(parentList[0] + "-" + "third");
        childrenList2.add(parentList[1] + "-" + "first");
        childrenList2.add(parentList[1] + "-" + "second");
        childrenList2.add(parentList[1] + "-" + "third");
        childrenList3.add(parentList[2] + "-" + "first");
        childrenList3.add(parentList[2] + "-" + "second");
        childrenList3.add(parentList[2] + "-" + "third");
        dataset.put(parentList[0], childrenList1);
        dataset.put(parentList[1], childrenList2);
        dataset.put(parentList[2], childrenList3);*/


//        实现思路，Parent中有一个List<Child>对象，解析json的时候解析出
    }

    private class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

        //  获得某个父项的某个子项
        @Override
        public Object getChild(int parentPos, int childPos) {
            return datasets.get(PList[parentPos]).get(childPos);
        }

        //  获得父项的数量
        @Override
        public int getGroupCount() {
            if (datasets == null) {
                Toast.makeText(ExpandableListViewActivity.this, "dataset为空", Toast.LENGTH_SHORT).show();
                return 0;
            }
            return datasets.size();
        }

        //  获得某个父项的子项数目
        @Override
        public int getChildrenCount(int parentPos) {
            if (datasets.get(PList[parentPos]) == null) {
                Toast.makeText(ExpandableListViewActivity.this, "\" + parentList[parentPos] + \" + 数据为空", Toast.LENGTH_SHORT).show();
                return 0;
            }
            return datasets.get(PList[parentPos]).size();
        }

        //  获得某个父项
        @Override
        public Object getGroup(int parentPos) {
            return datasets.get(PList[parentPos]);
        }

        //  获得某个父项的id
        @Override
        public long getGroupId(int parentPos) {
            return parentPos;
        }

        //  获得某个父项的某个子项的id
        @Override
        public long getChildId(int parentPos, int childPos) {
            return childPos;
        }

        //  按函数的名字来理解应该是是否具有稳定的id，这个函数目前一直都是返回false，没有去改动过
        @Override
        public boolean hasStableIds() {
            return false;
        }

        //  获得父项显示的view
        @Override
        public View getGroupView(int parentPos, boolean b, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) ExpandableListViewActivity
                        .this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.elv_cinema_list_item_cityproper, null);
            }
            view.setTag(R.layout.elv_cinema_list_item_cityproper, parentPos);
            view.setTag(R.layout.elv_cinema_list_item_cinema, -1);
            parentText = (TextView) view.findViewById(R.id.parent_title);
            ImageView parent_img = (ImageView) view.findViewById(R.id.parent_img);
//            设置展开和收缩的文字颜色
            if (b) {
                parentText.setTextColor(Color.parseColor("#2FD0B5"));
                parent_img.setImageResource(R.drawable.star_yellow);
            } else {
                parentText.setTextColor(Color.BLACK);
                parent_img.setImageResource(R.drawable.star_grey);
            }
            parentText.setText(PList[parentPos]);
            return view;
        }

        //  获得子项显示的view
        @Override
        public View getChildView(final int parentPos, final int childPos, boolean b, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) ExpandableListViewActivity
                        .this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.elv_cinema_list_item_cinema, null);
            }
            view.setTag(R.layout.elv_cinema_list_item_cityproper, parentPos);
            view.setTag(R.layout.elv_cinema_list_item_cinema, childPos);
            TextView text = (TextView) view.findViewById(R.id.child_title);
            TextView textView = (TextView) view.findViewById(R.id.child_message);
            text.setText(datasets.get(PList[parentPos]).get(childPos).getCinema_name());
            textView.setText(datasets.get(PList[parentPos]).get(childPos).getCinema_address());
            return view;
        }

        //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
    }
}
