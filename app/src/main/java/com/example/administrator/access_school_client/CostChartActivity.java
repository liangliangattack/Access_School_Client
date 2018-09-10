package com.example.administrator.access_school_client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.access_school_client.adapter.ChartUtil;
import com.example.administrator.access_school_client.bean.CostRecordBean;
import com.example.administrator.access_school_client.dao.CostRecordDao;
import com.example.administrator.access_school_client.widget.CustomDatePicker;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CostChartActivity extends AppCompatActivity implements View.OnClickListener {
    private List<CostRecordBean> lists;
    private TextView currentDate;
    private CustomDatePicker customDatePicker1;
    private String now;
    private LineChart lineChart;
    private List<String> xDataList = new ArrayList<>();
    private List<Entry> yDataList = new ArrayList<>();
    private static final float WATER_PRICE = 100, ELEC_PRICE = 100;
    private String[] tabTitles = {"水费", "电费"};
    private int tabId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_chart);
        initView();
        initDatePicker();
        initData();
        if (lists.size() != 0) {
            showChart("water");
        }
    }

    public void initView() {
        TabLayout chartTabs = findViewById(R.id.tl_chart_tabs);
        for (String title : tabTitles) {
            chartTabs.addTab(chartTabs.newTab().setText(title));
        }
        tabId = 1;
        chartTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tabId = 1;
                    updateData(1, currentDate.getText().toString());
                    if (lists.size() != 0) {
                        showChart("water");
                    }
                } else {
                    tabId = 2;
                    updateData(2, currentDate.getText().toString());
                    if (lists.size() != 0) {
                        showChart("elec");
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        ImageView returnIv = findViewById(R.id.iv_return);
        returnIv.setOnClickListener(this);
        RelativeLayout selectDate = findViewById(R.id.rl_select_date);
        selectDate.setOnClickListener(this);
        currentDate = findViewById(R.id.tv_current_date);
        lineChart = findViewById(R.id.lineChart);
    }

    public void initData() {
        CostRecordDao costRecordDao = new CostRecordDao();
        lists = costRecordDao.getCostRecordByCostTypeAndYear(1, 0, currentDate.getText().toString());
        xDataList.add("0");
        yDataList.add(new Entry(0, 0));
        for (int i = 0; i < lists.size(); i++) {
            CostRecordBean costRecordBean = lists.get(i);
            xDataList.add(costRecordBean.getMonth() + "月");
            yDataList.add(new Entry(costRecordBean.getDegrees() * WATER_PRICE, costRecordBean.getMonth()));
        }
    }

    public void updateData(int costType, String year) {
        xDataList.removeAll(xDataList);
        yDataList.removeAll(yDataList);
        lists.removeAll(lists);
        CostRecordDao costRecordDao = new CostRecordDao();
        lists = costRecordDao.getCostRecordByCostTypeAndYear(costType, 0, year);
        xDataList.add("0");
        yDataList.add(new Entry(0, 0));
        for (int i = 0; i < lists.size(); i++) {
            CostRecordBean costRecordBean = lists.get(i);
            xDataList.add(costRecordBean.getMonth() + "月");
            yDataList.add(new Entry(costRecordBean.getDegrees() * (costType == 1 ? WATER_PRICE : ELEC_PRICE), costRecordBean.getMonth()));
        }
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        now = sdf.format(new Date());
        currentDate.setText(now.split("-")[0]);
        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDate.setText(time.split("-")[0]);
                updateData(tabId == 1 ? 1 : 2, time.split("-")[0]);
                if (lists.size() != 0) {
                    showChart("water");
                } else {
                    lineChart.clear();
                }
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime1(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

    }

    public void showChart(String type) {
        if ("water".equals(type)) {
            ChartUtil.showChart(this, lineChart, xDataList, yDataList, "水费趋势图", "水费/时间", "元/月");
        } else if ("elec".equals(type)) {
            ChartUtil.showChart(this, lineChart, xDataList, yDataList, "电费趋势图", "电费/时间", "元/月");
        }
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_select_date:
                customDatePicker1.show(currentDate.getText().toString() + "-01-01");
                break;
            case R.id.iv_return:
                finish();
                break;
        }
    }
}
