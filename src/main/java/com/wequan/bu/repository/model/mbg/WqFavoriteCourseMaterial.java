package com.wequan.bu.repository.model.mbg;

import java.util.Date;

public class WqFavoriteCourseMaterial {
    private Integer userId;

    private Integer courseMaterialId;

    private Date createTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}