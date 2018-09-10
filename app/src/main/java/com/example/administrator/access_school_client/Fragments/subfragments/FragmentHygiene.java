package com.example.administrator.access_school_client.Fragments.subfragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.bean.DeductionsRecordBean;
import com.example.administrator.access_school_client.dao.DeductionsRecordDao;
import com.example.administrator.access_school_client.widget.CustomDatePicker;

import java.security.PublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;


public class FragmentHygiene extends Fragment implements View.OnClickListener {

    //存放数据日期
    private List<Date> dates;
    //存放扣分项集合
    private List<DeductionsRecordBean> deductionsList;
    //创建扣分项dao操作
    private DeductionsRecordDao deductionsRecordDao;
    //扣分项具体内容
    public final static String[] deductions = new String[]{"阳台", "轨道", "地面", "书桌", "床铺", "洗手间", "厕所"};

    //柱状图&折线图
    private ColumnChartView chartTop;
    private LineChartView chartBottom;

    //柱状图数据&折线图数据
    private LineChartData lineData;

    //创建 TextView 视图对象
    private TextView leftTime, rightTime, lineChartTime;

    //时间选择器
    private CustomDatePicker customDatePicker1, customDatePicker2;

    //当前上下文
    private Context context;

    //时间格式化
    SimpleDateFormat simpleDateFormat;

    //空的构造方法
    public FragmentHygiene() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hygiene, container, false);
        try {
            initView(rootView); // 初始化视图控件
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rootView;
    }

    /**
     * 初始化时间数据
     */
    private void initDate() throws ParseException {
        // new 一个新的时间集合
        dates = new ArrayList<>();
        // 初始化扣分项 dao 对象
        deductionsRecordDao = new DeductionsRecordDao();
        // 得到得到扣分项集合
        deductionsList = deductionsRecordDao.getDateByStartTimeAndEndTime(leftTime.getText().toString(), rightTime.getText().toString());
        // 将时间数据放入集合 dates 中
        for (int i = 0; i < deductionsList.size(); i++) {
            dates.add(deductionsList.get(i).getDate());
        }
    }


    public void initView(View rootView) throws ParseException {
        context = getActivity();
        leftTime = rootView.findViewById(R.id.left_time);
        leftTime.setOnClickListener(this);
        rightTime = rootView.findViewById(R.id.right_time);
        rightTime.setOnClickListener(this);
        lineChartTime = rootView.findViewById(R.id.tv_linechart_time);
        initDatePicker(); //初始化时间选择器
        initDate();
        chartTop = rootView.findViewById(R.id.chart_top);
        generateColumnData();
        chartBottom = rootView.findViewById(R.id.chart_bottom);
        generateInitialLineData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_time:
                customDatePicker1.show(leftTime.getText().toString());
                break;
            case R.id.right_time:
                customDatePicker2.show(rightTime.getText().toString());
                break;
        }
    }

    /**
     * 柱状图数据输入
     */
    private void generateColumnData() {

        int numColumns = dates.size();

        List<AxisValue> axisValues = new ArrayList<>(); // 创建 X 坐标轴标签集合
        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {
            values = new ArrayList<>();
            values.add(new SubcolumnValue(deductionsList.get(i).getTotal(), ChartUtils.pickColor())); // 添加 Y 轴数据
            simpleDateFormat = new SimpleDateFormat("yy.MM.dd");
            axisValues.add(new AxisValue(i).setLabel(simpleDateFormat.format(dates.get(i)))); // 给 X 轴标签赋值
            columns.add(new Column(values).setHasLabelsOnlyForSelected(true)); // 点击数据后是否显示数据标签
        }

        ColumnChartData columnData = new ColumnChartData(columns);

        columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true).setName("时间"));  // setHasLine: 是否显示水平坐标网格
        columnData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3).setName("总扣分值")); // 显示垂直坐标网格并设置 Y 轴标签宽度最大值为 3

        /*
             setValueSelectionEnabled: 设置是否可以选中图表中的值，即当点击图表中的数据值后，会一直处于选中状态，直到用户点击其他空间
        */
        chartTop.setValueSelectionEnabled(true);
        chartTop.setInteractive(true);
        chartTop.setZoomType(ZoomType.HORIZONTAL);
        chartTop.setZoomEnabled(false); // 设置是否缩放
        chartTop.setMaxZoom((float) 4);// 设置变焦类型
        chartTop.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        chartTop.setColumnChartData(columnData); // 将柱状图数据对象放入柱状图中
        chartTop.setVisibility(View.VISIBLE);

        chartTop.setOnValueTouchListener(new ValueTouchListener()); // 设置柱状图监听
        Viewport v = new Viewport(chartTop.getMaximumViewport());
        v.top = 10;
        chartTop.setMaximumViewport(v);
        v.left = 0;
        v.right = 5;
        chartTop.setCurrentViewport(v);
    }

    /**
     * 折线图初始化
     */
    private void generateInitialLineData() {
        int numValues = 7;

        List<AxisValue> axisValues = new ArrayList<>();
        List<PointValue> values = new ArrayList<>();


        for (int i = 0; i < numValues; ++i) {
            values.add(new PointValue(i, 0)); // 初始化数据
            axisValues.add(new AxisValue(i).setLabel(deductions[i])); // 设置水平轴标签名
        }

        Line line = new Line(values);
        line.setFormatter(new SimpleLineChartValueFormatter(0));
        line.setColor(ChartUtils.COLOR_GREEN).setCubic(true); // 设置颜色

        List<Line> lines = new ArrayList<>();
        lines.add(line);

        lineData = new LineChartData(lines);
        lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true).setName("扣分项"));
        lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3).setName("扣分值"));

        chartBottom.setLineChartData(lineData);

        chartBottom.setViewportCalculationEnabled(false);

        Viewport v = new Viewport(0, 5, 6, 0);

        chartBottom.setMaximumViewport(v);
        chartBottom.setCurrentViewport(v);

        chartBottom.setZoomType(ZoomType.HORIZONTAL);
    }

    private void generateLineData(int color, int columnIndex) {
        chartTop.cancelDataAnimation();

        Line line = lineData.getLines().get(0);// For this example there is always only one line.
        line.setColor(color);
        int i = 0;
        if (columnIndex != -1) {
            for (PointValue value : line.getValues()) {
                value.setTarget(value.getX(), deductionsList.get(columnIndex).getDeductions()[i]);
                i++;
            }
        }
        chartBottom.startDataAnimation(300);
        chartBottom.setContentDescription("时间");
    }

    /**
     * 设置柱状图数据监听
     */
    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @SuppressLint("SetTextI18n")
        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            generateLineData(value.getColor(), columnIndex);
            simpleDateFormat = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
            String date = simpleDateFormat.format(dates.get(columnIndex));
            lineChartTime.setText(date);
        }

        @Override
        public void onValueDeselected() {

            generateLineData(ChartUtils.COLOR_GREEN, -1);

        }
    }

    @SuppressLint("SetTextI18n")
    private void initDatePicker() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        final String now = simpleDateFormat.format(new Date());
        leftTime.setText(now.split("-")[0] + "-" + now.split("-")[1] + "-" + "01");
        rightTime.setText(now.split(" ")[0]);
        customDatePicker1 = new CustomDatePicker(context, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) throws ParseException { // 回调接口，获得选中的时间
                leftTime.setText(time.split(" ")[0]);
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                customDatePicker2.startCalendar.setTime(simpleDateFormat.parse(leftTime.getText().toString() + " 00:00"));
                updateData(leftTime.getText().toString(), rightTime.getText().toString());
            }
        }, "2010-01-01 00:00", rightTime.getText().toString() + " 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2 = new CustomDatePicker(context, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) throws ParseException { // 回调接口，获得选中的时间
                rightTime.setText(time.split(" ")[0]);
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                customDatePicker1.endCalendar.setTime(simpleDateFormat.parse(rightTime.getText().toString() + " 00:00"));
                updateData(leftTime.getText().toString(), rightTime.getText().toString());
            }
        }, leftTime.getText().toString() + " 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动
        customDatePicker2.showSpecificTime(false);
        customDatePicker2.setIsLoop(false);
    }


    public static FragmentHygiene newInstance() {

        Bundle args = new Bundle();

        FragmentHygiene fragment = new FragmentHygiene();
        fragment.setArguments(args);
        return fragment;
    }

    public void updateData(String startTime, String endTime) throws ParseException {
        deductionsList.clear();
        deductionsList = deductionsRecordDao.getDateByStartTimeAndEndTime(leftTime.getText().toString(), rightTime.getText().toString());
        dates.clear();
        for (int i = 0; i < deductionsList.size(); i++) {
            dates.add(deductionsList.get(i).getDate());
        }
        generateColumnData();
        generateInitialLineData();
    }
}
