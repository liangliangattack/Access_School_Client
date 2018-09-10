package com.example.administrator.access_school_client.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.access_school_client.R;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2018/8/30 11:43.
 */
public class Fragmentthree extends Fragment{

    private ListView listView;
    private String[] date = new String[]{"9月17日","9月16日","9月15日","9月10日","9月7日","9月5日"};
    private String[] repair = new String[]{"上网故障","风扇故障","其他故障","桌椅故障","门窗故障","洗浴设施故障"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_three,null);
        listView = rootview.findViewById(R.id.listview);
        MyLVAdapter myLVAdapter = new MyLVAdapter();
        listView.setAdapter(myLVAdapter);

        return rootview;
    }

    class MyLVAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View rootview = inflater.inflate(R.layout.simpleitem , null);
            TextView title = rootview.findViewById(R.id.simple_title);
            TextView content = rootview.findViewById(R.id.simple_content);
            Log.e("________","&*********"+i);
            title.setText(repair[i]);
            content.setText(date[i]);

            return rootview;
        }
    }
}
