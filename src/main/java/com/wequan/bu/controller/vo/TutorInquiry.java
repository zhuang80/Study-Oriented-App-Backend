package com.wequan.bu.controller.vo;

import java.util.Date;

/**
 * @author ChrisChen
 */
public class TutorInquiry extends BaseVo {

    private int subjectId;
    private String briefDescription;
    private boolean online;
    private short status;
    private Date requestStartTime;
    private Date requestEndTime;
    private double acceptPayRate;

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Date getRequestStartTime() {
        return requestStartTime;
    }

    public void setRequestStartTime(Date requestStartTime) {
        this.requestStartTime = requestStartTime;
    }

    public Date getRequestEndTime() {
        return requestEndTime;
    }

    public void setRequestEndTime(Date requestEndTime) {
        this.requestEndTime = requestEndTime;
    }

    public double getAcceptPayRate() {
        return acceptPayRate;
    }

    public void setAcceptPayRate(double acceptPayRate) {
        this.acceptPayRate = acceptPayRate;
    }
}
