package com.wequan.bu.repository.model.mbg;

import java.util.Date;

public class WqCourseMaterialUnlockRecord {
    private Integer id;

    private Integer courseMaterialId;

    private Integer userId;

    private Date unlockTime;

    private Short studyPointsCost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseMaterialId() {
        return courseMaterialId;
    }

    public void setCourseMaterialId(Integer courseMaterialId) {
        this.courseMaterialId = courseMaterialId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(Date unlockTime) {
        this.unlockTime = unlockTime;
    }

    public Short getStudyPointsCost() {
        return studyPointsCost;
    }

    public void setStudyPointsCost(Short studyPointsCost) {
        this.studyPointsCost = studyPointsCost;
    }
}