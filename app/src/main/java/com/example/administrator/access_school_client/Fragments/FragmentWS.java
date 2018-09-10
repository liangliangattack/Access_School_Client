package com.example.administrator.access_school_client.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.access_school_client.Fragments.subfragments.FragmentHygiene;
import com.example.administrator.access_school_client.Fragments.subfragments.FragmentWaterAndElec;
import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.adapter.FragmentWSAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2018/7/18 16:19.
 */
public class FragmentWS extends Fragment {

    private Context context;
    private TabLayout tlHygieneTabs;
    private ViewPager vpContent;
    private String[] tabTitles = {"卫生", "水电费"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.ws_fragment, container, false);
        initView(rootview);
        return rootview;
    }

    public void initView(View rootview) {
        context = getActivity();
        vpContent = rootview.findViewById(R.id.vp_content);
        tlHygieneTabs = rootview.findViewById(R.id.tl_hygiene_tabs);
        tlHygieneTabs.setupWithViewPager(vpContent);
        for (int i = 0; i < tabTitles.length; i++) {
            tlHygieneTabs.addTab(tlHygieneTabs.newTab().setText(tabTitles[i]));
        }
        tlHygieneTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(FragmentHygiene.newInstance());
        fragments.add(FragmentWaterAndElec.newInstance());
        FragmentWSAdapter adapter = new FragmentWSAdapter(getChildFragmentManager(), fragments, tabTitles);
        vpContent.setAdapter(adapter);
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


}
