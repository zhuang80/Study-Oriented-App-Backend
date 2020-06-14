package com.wequan.bu.repository.model.mbg;

import java.util.Date;

public class WqTutorReviewTag {
    private Short tagId;

    private Integer tutorReviewId;

    private Integer createBy;

    private Date createTime;

    private Date updateTime;

    public Short getTagId() {
        return tagId;
    }

    public void setTagId(Short tagId) {
        this.tagId = tagId;
    }

    public Integer getTutorReviewId() {
        return tutorReviewId;
    }

    public void setTutorReviewId(Integer tutorReviewId) {
        this.tutorReviewId = tutorReviewId;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}