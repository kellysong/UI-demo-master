package com.sjl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sjl.entity.MenuInfo;
import com.sjl.uidemo.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by song on 2017/8/10.
 */

public class RecyclerViewGroupingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    LayoutInflater mInflater;
    List<MenuInfo> mDatas;
    List<MenuInfo.ChildListBean> childListBeans;
    private boolean showDataWay;
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_MENU = 1;


    public RecyclerViewGroupingAdapter(Context context, boolean showDataWay, List<MenuInfo> datas, List<MenuInfo.ChildListBean> childListBeans) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.showDataWay = showDataWay;
        this.childListBeans = childListBeans;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_MENU) {
            View itemView = mInflater.inflate(R.layout.item_grouping_menu, parent, false);
            return new MenuViewHolder(itemView);
        } else if (viewType == TYPE_TITLE) {
            View itemView = mInflater.inflate(R.layout.item_grouping_title, parent, false);

            return new TitleViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (showDataWay){
            final MenuInfo.ChildListBean bean = childListBeans.get(position);
            int type = holder.getItemViewType();
            if (type == TYPE_MENU) {
                MenuViewHolder menuViewHolder = (MenuViewHolder) holder;
                menuViewHolder.groupTitle.setText(bean.getChildName());
                menuViewHolder.groupImg.setTag(position);
            } else {
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                titleViewHolder.title.setText(bean.getChildName());

            }
        }else{
            int count = 0;
            for (int i = 0; i < mDatas.size(); i++) {
                MenuInfo info = mDatas.get(i);
                if (position == count) {
                    ((TitleViewHolder) holder).title.setText(info.getHeader());
                }
                count++;//1
                List<String> list1 = info.getDataList();
                for (int j = 0; j < list1.size(); j++) {
                    if (position == count) {
                        ((MenuViewHolder) holder).groupTitle.setText(list1.get(j));
                        ((MenuViewHolder) holder).groupImg.setTag(position);
                    }
                    count++;
                }
            }
        }


    }

    @Override
    public int getItemCount() {
        if (showDataWay) {
            return childListBeans.size();
        } else {
            int size = 0;
            for (int i = 0; i < mDatas.size(); i++) {
                MenuInfo info = mDatas.get(i);
                size += info.getDataList().size();
            }
            return mDatas.size() + size;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (showDataWay) {
            if (childListBeans.get(position).isGroup()) {
                return TYPE_TITLE;
            } else {
                return TYPE_MENU;
            }
        } else {
            //循环遍历所有的数据判断position==0执行头部布局。否则内容
            int count = 0;
            for (int i = 0; i < mDatas.size(); i++) {
                MenuInfo info = mDatas.get(i);
                List<String> list1 = info.getDataList();
                if (position == count) {
                    return TYPE_TITLE;
                }
                count++;
                for (int j = 0; j < list1.size(); j++) {
                    if (position == count) {
                        return TYPE_MENU;
                    }
                    count++;
                }
            }
            return TYPE_TITLE;
        }

    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_group_img_title)
        TextView groupTitle;
        @Bind(R.id.iv_group_img)
        ImageView groupImg;

        public MenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            initListener(itemView);
        }

        private void initListener(View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "poistion " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_group_title)
        TextView title;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}