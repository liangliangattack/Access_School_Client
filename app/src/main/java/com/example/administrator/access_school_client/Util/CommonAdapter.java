package com.example.administrator.access_school_client.Util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {

    private Context context;
    private List<T> list;
    private int layoutId;
    private List<Integer> layoutIds;

    public CommonAdapter(Context context, List<T> list, List<Integer> layoutIds) {
        this.context = context;
        this.list = list;
        this.layoutIds = layoutIds;
    }
    public CommonAdapter(Context context, List<T> list, int layoutId){
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int ItemLayoutId = getItemLayoutIdForViewType(position);
        CommonViewHolder commonViewHolder = CommonViewHolder.getViewHolder(context, convertView, position, ItemLayoutId, parent);
        convert(commonViewHolder, ItemLayoutId, position);
        return commonViewHolder.getItemConvertView();
    }

    // 留给具体 adapter 实现类的方法
    public abstract void convert(CommonViewHolder commonViewHolder, int viewType, int position);

    public abstract int getViewTypeCount();

    public abstract int getItemLayoutIdForViewType(int position);
}
