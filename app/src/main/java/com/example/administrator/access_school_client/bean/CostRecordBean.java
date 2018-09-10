package com.example.administrator.access_school_client.bean;

public class CostRecordBean {

    private int costType; // 1 代表水费， 2 代表电费
    private int year;
    private int month;
    private float degrees;
    private int viewType;// 1 代表标题， 0 代表 listitem

    public int getCostType() {
        return costType;
    }

    public void setCostType(int costType) {
        this.costType = costType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public float getDegrees() {
        return degrees;
    }

    public void setDegrees(float degrees) {
        this.degrees = degrees;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public CostRecordBean(int costType, int year, int month, float degrees, int viewType) {
        this.costType = costType;
        this.year = year;
        this.month = month;
        this.degrees = degrees;
        this.viewType = viewType;
    }

    public CostRecordBean() {

    }


    @Override
    public String toString() {
        return "CostRecordBean{" +
                "costType=" + costType +
                ", year=" + year +
                ", month=" + month +
                ", degrees=" + degrees +
                ", viewType=" + viewType +
                '}';
    }
}
