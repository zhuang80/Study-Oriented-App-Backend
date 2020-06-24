package com.wequan.bu.repository.model;

import lombok.Data;

import java.sql.Date;

@Data
public class ReportRecord {
    /**
     * primary key
     */
    private Integer id;

    /**
     * user who posts report
     */
    private Integer userId;
    /**
     * 1 for thread, 2 for threadStream(reply), 3 for material
     */
    private Short resourceType;

    /**
     * resource id which is reported
     */
    private Integer resourceId;

    private Date reportTime;

    private String reason;

    public ReportRecord(Integer userId, Short resourceType, Integer resourceId, Date reportTime, String reason){
        setUserId(userId);
        setResourceId(resourceId);
        setResourceType(resourceType);
        setReportTime(reportTime);
        setReason(reason);
    }

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

    public Short getResourceType() {
        return resourceType;
    }

    public void setResourceType(Short resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
