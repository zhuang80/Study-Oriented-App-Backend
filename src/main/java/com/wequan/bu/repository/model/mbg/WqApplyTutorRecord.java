package com.wequan.bu.repository.model.mbg;

import java.util.Date;

public class WqApplyTutorRecord {
    private Integer id;

    private Integer userId;

    private String briefIntroduction;

    private String resumePath;

    private String transcriptPath;

    private String otherProofPath;

    private Short currentSchoolId;

    private Short currentDegreeId;

    private String currentLocation;

    private String applyTutorCourses;

    private Date createTime;

    private Date updateTime;

    private Short adminAction;

    private Date adminActionTime;

    private Short status;

    private String adminComment;

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

    public String getApplyTutorCourses() {
        return applyTutorCourses;
    }

    public void setApplyTutorCourses(String applyTutorCourses) {
        this.applyTutorCourses = applyTutorCourses;
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

    public Short getAdminAction() {
        return adminAction;
    }

    public void setAdminAction(Short adminAction) {
        this.adminAction = adminAction;
    }

    public Date getAdminActionTime() {
        return adminActionTime;
    }

    public void setAdminActionTime(Date adminActionTime) {
        this.adminActionTime = adminActionTime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }
}