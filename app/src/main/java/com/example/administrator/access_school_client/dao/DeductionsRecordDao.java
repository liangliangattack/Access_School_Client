package com.example.administrator.access_school_client.dao;

import android.database.DefaultDatabaseErrorHandler;
import android.util.Log;

import com.example.administrator.access_school_client.bean.DeductionsRecordBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeductionsRecordDao {

    private static List<DeductionsRecordBean> deductions;


    public static List<DeductionsRecordBean> getDeductions() {
        deductions = new ArrayList<>();
        deductions.add(new DeductionsRecordBean(new Date(118, 3, 5), new int[]{1, 2, 0, 2, 0, 0, 1}, 5));
        deductions.add(new DeductionsRecordBean(new Date(118, 3, 8), new int[]{1, 0, 1, 0, 1, 0, 0}, 3));
        deductions.add(new DeductionsRecordBean(new Date(118, 3, 10), new int[]{0, 0, 0, 0, 1, 0, 0}, 0));
        deductions.add(new DeductionsRecordBean(new Date(118, 4, 5), new int[]{0, 0, 0, 0, 0, 0, 0}, 0));
        deductions.add(new DeductionsRecordBean(new Date(118, 4, 11), new int[]{1, 0, 0, 0, 0, 0, 0}, 1));
        deductions.add(new DeductionsRecordBean(new Date(118, 4, 13), new int[]{1, 0, 0, 0, 0, 0, 1}, 2));
        deductions.add(new DeductionsRecordBean(new Date(118, 4, 24), new int[]{1, 0, 0, 0, 3, 0, 3}, 7));
        deductions.add(new DeductionsRecordBean(new Date(118, 4, 26), new int[]{0, 0, 1, 3, 1, 0, 3}, 8));
        deductions.add(new DeductionsRecordBean(new Date(118, 4, 29), new int[]{1, 1, 1, 0, 1, 0, 3}, 7));
        deductions.add(new DeductionsRecordBean(new Date(118, 5, 2), new int[]{1, 0, 1, 0, 1, 0, 2}, 5));
        deductions.add(new DeductionsRecordBean(new Date(118, 5, 5), new int[]{0, 0, 0, 0, 0, 0, 0}, 0));
        deductions.add(new DeductionsRecordBean(new Date(118, 5, 7), new int[]{0, 0, 0, 0, 1, 0, 0}, 1));
        deductions.add(new DeductionsRecordBean(new Date(118, 5, 10), new int[]{0, 0, 0, 0, 0, 0, 0}, 0));
        deductions.add(new DeductionsRecordBean(new Date(118, 5, 16), new int[]{0, 0, 0, 0, 1, 1, 1}, 3));
        deductions.add(new DeductionsRecordBean(new Date(118, 5, 21), new int[]{1, 0, 0, 0, 1, 1, 1}, 4));
        deductions.add(new DeductionsRecordBean(new Date(118, 6, 4), new int[]{0, 1, 0, 0, 0, 1, 0}, 2));
        deductions.add(new DeductionsRecordBean(new Date(118, 6, 7), new int[]{1, 0, 0, 0, 1, 0, 0}, 2));
        deductions.add(new DeductionsRecordBean(new Date(118, 6, 9), new int[]{0, 0, 0, 0, 0, 0, 0}, 0));
        deductions.add(new DeductionsRecordBean(new Date(118, 6, 21), new int[]{1, 0, 0, 0, 0, 2, 0}, 3));
        deductions.add(new DeductionsRecordBean(new Date(118, 7, 7), new int[]{0, 0, 0, 0, 1, 0, 0}, 1));
        deductions.add(new DeductionsRecordBean(new Date(118, 7, 8), new int[]{0, 0, 0, 0, 0, 0, 0}, 0));
        deductions.add(new DeductionsRecordBean(new Date(118, 7, 10), new int[]{0, 0, 0, 0, 1, 1, 1}, 3));
        deductions.add(new DeductionsRecordBean(new Date(118, 7, 13), new int[]{0, 0, 0, 4, 1, 0, 0}, 5));
        deductions.add(new DeductionsRecordBean(new Date(118, 7, 14), new int[]{0, 0, 0, 0, 0, 0, 0}, 0));
        deductions.add(new DeductionsRecordBean(new Date(118, 8, 3), new int[]{0, 0, 0, 3, 1, 1, 1}, 6));
        deductions.add(new DeductionsRecordBean(new Date(118, 8, 5), new int[]{1, 0, 0, 0, 1, 1, 1}, 4));
        deductions.add(new DeductionsRecordBean(new Date(118, 8, 7), new int[]{0, 0, 0, 0, 0, 0, 0}, 0));
        deductions.add(new DeductionsRecordBean(new Date(118, 8, 8), new int[]{1, 0, 0, 0, 0, 0, 0}, 1));
        deductions.add(new DeductionsRecordBean(new Date(118, 8, 11), new int[]{1, 0, 0, 0, 0, 0, 1}, 2));
        deductions.add(new DeductionsRecordBean(new Date(118, 8, 13), new int[]{0, 1, 0, 0, 0, 1, 0}, 2));
        deductions.add(new DeductionsRecordBean(new Date(118, 8, 15), new int[]{1, 0, 0, 0, 1, 0, 0}, 2));
        deductions.add(new DeductionsRecordBean(new Date(118, 8, 16), new int[]{0, 0, 0, 0, 0, 0, 0}, 0));
        deductions.add(new DeductionsRecordBean(new Date(118, 8, 17), new int[]{1, 0, 0, 0, 0, 2, 0}, 3));
        return deductions;
    }

    public List<DeductionsRecordBean> getDateByStartTimeAndEndTime(String startDate, String endDate) {
        deductions = getDeductions();
        List<DeductionsRecordBean> deductionsList = new ArrayList<>();
        for (int i = 0; i < deductions.size(); i++) {
            DeductionsRecordBean bean = deductions.get(i);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String beanDate = simpleDateFormat.format(bean.getDate());
            if (beanDate.compareTo(startDate) >= 0 && beanDate.compareTo(endDate) <= 0) {
                deductionsList.add(bean);
            }
        }
        return deductionsList;
    }
}
