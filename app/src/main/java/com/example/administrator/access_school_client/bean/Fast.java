package com.example.administrator.access_school_client.bean;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2018/9/5 22:07.
 */
public class Fast {
    private String ShipperCode;
    private String LogisticCode;
    private String Tracetime;
    private String Traceinfo;
    private String state;
    private String Reason;

    public Fast(String shipperCode, String logisticCode, String tracetime, String traceinfo, String state) {
        ShipperCode = shipperCode;
        LogisticCode = logisticCode;
        Tracetime = tracetime;
        Traceinfo = traceinfo;
        this.state = state;
    }

    public Fast(String shipperCode, String logisticCode, String tracetime, String traceinfo, String state, String reason) {
        ShipperCode = shipperCode;
        LogisticCode = logisticCode;
        Tracetime = tracetime;
        Traceinfo = traceinfo;
        this.state = state;
        Reason = reason;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String shipperCode) {
        ShipperCode = shipperCode;
    }

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        LogisticCode = logisticCode;
    }

    public String getTracetime() {
        return Tracetime;
    }

    public void setTracetime(String tracetime) {
        Tracetime = tracetime;
    }

    public String getTraceinfo() {
        return Traceinfo;
    }

    public void setTraceinfo(String traceinfo) {
        Traceinfo = traceinfo;
    }
}
