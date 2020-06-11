package com.wequan.bu.repository.model.mbg;

import java.util.Date;

public class WqCourseMaterialViewHistory {
    private Integer id;

    private Integer userId;

    private Integer courseMaterialId;

    private Date viewTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCourseMaterialId() {
        return courseMaterialId;
    }

    public void setCourseMaterialId(Integer courseMaterialId) {
        this.courseMaterialId = courseMaterialId;
    }

    public Date getViewTime() {
        return viewTime;
    }

    public void setViewTime(Date viewTime) {
        this.viewTime = viewTime;
    }
}