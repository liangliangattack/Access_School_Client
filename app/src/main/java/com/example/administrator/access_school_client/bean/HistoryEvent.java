package com.example.administrator.access_school_client.bean;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2018/9/4 08:12.
 */
public class HistoryEvent {
    private String title;
    private String date;
    private String picurl;

    public HistoryEvent(String title, String date, String picurl) {
        this.title = title;
        this.date = date;
        this.picurl = picurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }
}
