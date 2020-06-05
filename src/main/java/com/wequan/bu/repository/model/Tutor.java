package com.wequan.bu.repository.model;

import java.time.LocalDateTime;

/**
 * @author Zhen Lin
 */
public class Tutor {

    private Integer id;
    private User user;
    private String briefIntroduction;
    private String resumePath;
    private String transcriptPath;
    private String otherProofPath;
    private Integer currentSchoolId;
    private Integer currentDegreeId;
    private String currentLocation;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Double payRate;
    private String status;
    private Integer latePolicyId;
    private Integer cancellationPolicyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Integer getCurrentSchoolId() {
        return currentSchoolId;
    }

    public void setCurrentSchoolId(Integer currentSchoolId) {
        this.currentSchoolId = currentSchoolId;
    }

    public Integer getCurrentDegreeId() {
        return currentDegreeId;
    }

    public void setCurrentDegreeId(Integer currentDegreeId) {
        this.currentDegreeId = currentDegreeId;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Double getPayRate() {
        return payRate;
    }

    public void setPayRate(Double payRate) {
        this.payRate = payRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getLatePolicyId() {
        return latePolicyId;
    }

    public void setLatePolicyId(Integer latePolicyId) {
        this.latePolicyId = latePolicyId;
    }

    public Integer getCancellationPolicyId() {
        return cancellationPolicyId;
    }

    public void setCancellationPolicyId(Integer cancellationPolicyId) {
        this.cancellationPolicyId = cancellationPolicyId;
    }


}
