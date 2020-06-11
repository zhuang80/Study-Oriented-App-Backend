package com.wequan.bu.controller.vo;

import java.util.Date;
import java.util.List;

/**
 * @author ChrisChen
 */
public class OnlineEvent extends BaseVo {

    private String name;
    private short type;
    private String briefDescription;
    private Date scheduled;
    private double fee;
    private short status;
    private List<User> members;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public Date getScheduled() {
        return scheduled;
    }

    public void setScheduled(Date scheduled) {
        this.scheduled = scheduled;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
