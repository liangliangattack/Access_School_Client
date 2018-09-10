package com.example.administrator.access_school_client.dao;

import android.util.Log;

import com.example.administrator.access_school_client.R;
import com.example.administrator.access_school_client.bean.CostRecordBean;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class CostRecordDao {

    public List<CostRecordBean> getCostRecordByCostTypeAndYear(int costType, int viewType, String year) {
        List<CostRecordBean> list = staticData(year);
        List<CostRecordBean> mlist = new ArrayList<CostRecordBean>();
        for (CostRecordBean costRecordBean : list) {
            if (costRecordBean.getCostType() == costType && costRecordBean.getViewType() == viewType && costRecordBean.getYear() == Integer.valueOf(year)) {
                mlist.add(costRecordBean);
            }
        }
        return mlist;
    }

    public List<CostRecordBean> getAllElecCostRecord(int costType) {
        return null;
    }

    public List<CostRecordBean> getAllCostRecord(String year) {
        return staticData(year);
    }

    public List<CostRecordBean> staticData(String year) {
        List<CostRecordBean> list = new ArrayList<CostRecordBean>();
        DecimalFormat df = new DecimalFormat("#.00");
        if (year.equals("2017")) {
            Log.i("2017", "this is 2017");
            for (int i = 1; i <= 12; i++) {
                CostRecordBean costRecordBean = new CostRecordBean(0, 0, i, 0, 1);
                list.add(costRecordBean);
                costRecordBean = new CostRecordBean(1, 2017, i, Float.valueOf(df.format(Math.random())), 0);
                list.add(costRecordBean);
                costRecordBean = new CostRecordBean(2, 2017, i, Float.valueOf(df.format(Math.random())), 0);
                list.add(costRecordBean);
            }
        } else if (year.equals("2018")) {
            Log.i("2018", "this is 2018");
            for (int i = 1; i <= 9; i++) {
                CostRecordBean costRecordBean = new CostRecordBean(0, 0, i, 0, 1);
                list.add(costRecordBean);
                costRecordBean = new CostRecordBean(1, 2018, i, i==2||i==7||i==8?0:Float.valueOf(df.format(Math.random())), 0);
                list.add(costRecordBean);
                costRecordBean = new CostRecordBean(2, 2018, i, i==2||i==7||i==8?0:Float.valueOf(df.format(Math.random())), 0);
                list.add(costRecordBean);
            }
        }
        return list;
    }
}
