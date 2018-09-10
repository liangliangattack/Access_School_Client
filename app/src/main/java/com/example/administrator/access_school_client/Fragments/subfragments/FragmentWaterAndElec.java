package com.example.administrator.access_school_client.Fragments.subfragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.access_school_client.CostChartActivity;
import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.adapter.PinnedSectionAdapter;
import com.example.administrator.access_school_client.bean.CostRecordBean;
import com.example.administrator.access_school_client.dao.CostRecordDao;
import com.example.administrator.access_school_client.widget.CustomDatePicker;
import com.example.administrator.access_school_client.widget.PinnedSectionListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentWaterAndElec extends Fragment implements View.OnClickListener {
    private RelativeLayout selectDate;
    private TextView currentDate,noData;
    private CustomDatePicker customDatePicker1;
    private String now;
    private PinnedSectionListView pinnedSectionListView;
    private List<CostRecordBean> list = new ArrayList<CostRecordBean>();
    private CostRecordDao costRecordDao;
    private PinnedSectionAdapter pinnedSectionAdapter;
    private Context context;
    private ImageView ivCostChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_cost, null);
        initView(rootView);
        initDatePicker();
        initData();
        return rootView;
    }


    public void initView(View rootview) {
        noData = rootview.findViewById(R.id.no_data);
        ivCostChart = rootview.findViewById(R.id.iv_cost_chart);
        ivCostChart.setOnClickListener(this);
        context = getActivity();
        selectDate = rootview.findViewById(R.id.rl_select_date);
        selectDate.setOnClickListener(this);
        currentDate = rootview.findViewById(R.id.tv_current_date);
        pinnedSectionListView = (PinnedSectionListView) rootview.findViewById(R.id.lv_group_hoster);
    }

    public void initData() {
        costRecordDao = new CostRecordDao();
        list = costRecordDao.getAllCostRecord(currentDate.getText().toString());
        List<Integer> layoutIds = new ArrayList<Integer>();
        layoutIds.add(R.layout.listitem_water_elec_rent_no_pinned);
        layoutIds.add(R.layout.listitem_water_elec_rent_pinned);
        pinnedSectionAdapter = new PinnedSectionAdapter(context, list, layoutIds);
        pinnedSectionListView.setAdapter(pinnedSectionAdapter);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_select_date:
                customDatePicker1.show(currentDate.getText().toString() + "-01-01");
                break;
            case R.id.iv_cost_chart:
                Intent intent = new Intent();
                intent.setClass(context, CostChartActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        now = sdf.format(new Date());
        currentDate.setText(now.split("-")[0]);
        customDatePicker1 = new CustomDatePicker(context, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDate.setText(time.split("-")[0]);
                list = costRecordDao.getAllCostRecord(currentDate.getText().toString());
                if(list.size() == 0){
                    pinnedSectionListView.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                }else{
                    noData.setVisibility(View.GONE);
                    pinnedSectionListView.setVisibility(View.VISIBLE);
                    pinnedSectionAdapter.updateDataAndView(list);
                }

            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime1(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动
    }

    public static FragmentWaterAndElec newInstance() {

        Bundle args = new Bundle();

        FragmentWaterAndElec fragment = new FragmentWaterAndElec();
        fragment.setArguments(args);
        return fragment;
    }
}
