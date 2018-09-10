package com.example.administrator.access_school_client.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.access_school_client.Fragments.FragmentSQ.FragmentBX;
import com.example.administrator.access_school_client.Fragments.FragmentSQ.FragmentQJ;
import com.example.administrator.access_school_client.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2018/7/18 16:27.
 */
public class FragmentGG extends Fragment{
    TabLayout tabLayout;
    FragmentManager fragmentManager;
    FragmentBX fragmentBX;
    FragmentQJ fragmentQJ;
    ViewPager viewPager;
    FragmentPagerAdapter fragmentPagerAdapter;
    List<String> list;
    List<Fragment> fragments;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.gg_fragment , container , false);
        tabLayout=rootview.findViewById(R.id.tab_layout);
        viewPager=rootview.findViewById(R.id.viewpager);
        fragmentQJ=new FragmentQJ();
        fragmentBX =new FragmentBX();
        list=new ArrayList<>();
        fragments=new ArrayList<>();
        fragments.add(fragmentQJ);
        fragments.add(fragmentBX);
        list.add("请假申请");
        list.add("报修申请");
        fragmentManager=getChildFragmentManager();
        viewPager.setOffscreenPageLimit(list.size());//预加载
         fragmentPagerAdapter= new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }


            @Override
            public int getCount() {
                return list.size();
            }

             @Nullable
             @Override
             public CharSequence getPageTitle(int position) {
                 return list.get(position);
             }
         };


        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return rootview;
    }
//    public void tabLayoutSelected(int index){
//
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        switch (index){
//            case 0:
//
//                fragmentQJ=new FragmentQJ();
//                transaction.add(R.id.top_content,secondGoodsFragment).addToBackStack(null);
//
//                break;
//            case 1:
//                if (changeGoodsFragment==null){
//                    changeGoodsFragment=new ChangeGoodsFragment();
//                    transaction.add(R.id.top_content,changeGoodsFragment).addToBackStack(null);
//                }
//                else{
//                    transaction.show(changeGoodsFragment).addToBackStack(null);
//                }
//                break;
//            case 2:
//                if(publishGoodsFragment==null){
//                    publishGoodsFragment=new PublishGoodsFragment();
//                    transaction.add(R.id.top_content,publishGoodsFragment).addToBackStack(null);
//                }
//                else {
//                    transaction.show(publishGoodsFragment).addToBackStack(null);
//                }
//                break;
//        }
//        transaction.commit();
//    }
//    public void hideFragment(int index){
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        switch (index){
//            case 0:transaction.hide(secondGoodsFragment);
//
//                break;
//            case 1:transaction.hide(changeGoodsFragment);
//
//                break;
//            case 2:transaction.hide(publishGoodsFragment);
//
//                break;
//
//        }
//        transaction.commit();
//
//
//    }
}
