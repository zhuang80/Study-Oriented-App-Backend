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
    private double fee;
    private short status;
    private String method;
    private String methodDetail;
    private Date startTime;
    private Date endTime;
    private byte[] logo;
    private byte[] image;
    private boolean visible;
    private short belong_school_id;
    private String guid;
    private int numberOfMember;
    private List<Integer> tags;

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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethodDetail() {
        return methodDetail;
    }

    public void setMethodDetail(String methodDetail) {
        this.methodDetail = methodDetail;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public short getBelong_school_id() {
        return belong_school_id;
    }

    public void setBelong_school_id(short belong_school_id) {
        this.belong_school_id = belong_school_id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public int getNumberOfMember() {
        return numberOfMember;
    }

    public void setNumberOfMember(int numberOfMember) {
        this.numberOfMember = numberOfMember;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }
}
