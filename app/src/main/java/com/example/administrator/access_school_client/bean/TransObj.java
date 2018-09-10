package com.example.administrator.access_school_client.bean;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2018/9/7 12:21.
 */
public class TransObj {
    private String context;
    private String tranContent;

    public TransObj(String context, String tranContent) {
        this.context = context;
        this.tranContent = tranContent;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTranContent() {
        return tranContent;
    }

    public void setTranContent(String tranContent) {
        this.tranContent = tranContent;
    }
}
