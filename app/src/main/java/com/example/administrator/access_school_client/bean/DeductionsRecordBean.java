package com.example.administrator.access_school_client.bean;

import java.util.Date;

public class DeductionsRecordBean {

    private Date date;

    private int[] deductions = new int[7];

    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int[] getDeductions() {
        return deductions;
    }

    public void setDeductions(int[] deductions) {
        this.deductions = deductions;
    }

    public DeductionsRecordBean() {
    }

    public DeductionsRecordBean(Date date, int[] deductions, int total) {
        this.date = date;
        this.deductions = deductions;
        this.total = total;
    }
}
