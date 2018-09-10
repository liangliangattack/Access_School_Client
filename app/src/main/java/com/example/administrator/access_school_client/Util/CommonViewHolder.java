package com.example.administrator.access_school_client.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

public class CommonViewHolder {

    private View itemConvertView;
    // 创建 HashMap 来存储子控件
    private HashMap<Integer, View> map;

    public CommonViewHolder(Context context, int position, int layoutId, ViewGroup parent) {
        map = new HashMap<Integer, View>();
        itemConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        //设置 itemConvertView 的一个标签属性, 以便重复利用
        itemConvertView.setTag(this);
    }

    public static CommonViewHolder getViewHolder(Context context, View convertView, int position, int layoutId, ViewGroup parent) {
        // 通过判断 convertView 是否为空来选择是否返回一个新的 ViewHolder, 如果不为空则返回当前已被标识过的 CommonViewHolder
        // 通过此方法来重复利用 convertView, 提高性能
        if (convertView == null) {
            return new CommonViewHolder(context, position, layoutId, parent);
        } else {
            return (CommonViewHolder) convertView.getTag();
        }
    }

    public <T extends View> T getView(int viewId) {
        View view = map.get(viewId);
        if (view == null) {
            view = itemConvertView.findViewById(viewId);
            map.put(viewId, view);
        }
        return (T) view;
    }

    public View getItemConvertView() {
        return itemConvertView;
    }

}
