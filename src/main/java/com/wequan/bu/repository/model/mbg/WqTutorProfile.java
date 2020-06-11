package com.wequan.bu.repository.model.mbg;

import java.util.Date;

public class WqTutorProfile {
    private Integer id;

    private Integer userId;

    private String briefIntroduction;

    private String resumePath;

    private String transcriptPath;

    private String otherProofPath;

    private Short currentSchoolId;

    private Short currentDegreeId;

    private String currentLocation;

    private Date createTime;

    private Date updateTime;

    private Float payRate;

    private Short status;

    private Short latePolicyId;

    private Short cancellationPolicyId;

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

    public String getBriefIntroduction() {
        return briefIntroduction;
    }

    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }

    public String getResumePath() {
        return resumePath;
    }

    public void setResumePath(String resumePath) {
        this.resumePath = resumePath;
    }

    public String getTranscriptPath() {
        return transcriptPath;
    }

    public void setTranscriptPath(String transcriptPath) {
        this.transcriptPath = transcriptPath;
    }

    public String getOtherProofPath() {
        return otherProofPath;
    }

    public void setOtherProofPath(String otherProofPath) {
        this.otherProofPath = otherProofPath;
    }

    public Short getCurrentSchoolId() {
        return currentSchoolId;
    }

    public void setCurrentSchoolId(Short currentSchoolId) {
        this.currentSchoolId = currentSchoolId;
    }

    public Short getCurrentDegreeId() {
        return currentDegreeId;
    }

    public void setCurrentDegreeId(Short currentDegreeId) {
        this.currentDegreeId = currentDegreeId;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
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

    public Float getPayRate() {
        return payRate;
    }

    public void setPayRate(Float payRate) {
        this.payRate = payRate;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getLatePolicyId() {
        return latePolicyId;
    }

    public void setLatePolicyId(Short latePolicyId) {
        this.latePolicyId = latePolicyId;
    }

    public Short getCancellationPolicyId() {
        return cancellationPolicyId;
    }

    public void setCancellationPolicyId(Short cancellationPolicyId) {
        this.cancellationPolicyId = cancellationPolicyId;
    }
}