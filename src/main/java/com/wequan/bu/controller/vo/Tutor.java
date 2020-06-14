package com.wequan.bu.controller.vo;

import java.util.Date;
import java.util.List;

/**
 * @author ChrisChen
 */
public class Tutor {

    private int id;
    private User user;
    private String briefIntroduction;
    private String resumePath;
    private String transcriptPath;
    private String otherProofPath;
    private short currentSchoolId;
    private short currentDegreeId;
    private String currentLocation;
    private Date createTime;
    private Date updateTime;
    private double payRate;
    private short status;
    private short latePolicyId;
    private short cancellationPolicyId;
    private List<TutorReview> tutorReviews;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public short getCurrentSchoolId() {
        return currentSchoolId;
    }

    public void setCurrentSchoolId(short currentSchoolId) {
        this.currentSchoolId = currentSchoolId;
    }

    public short getCurrentDegreeId() {
        return currentDegreeId;
    }

    public void setCurrentDegreeId(short currentDegreeId) {
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

    public double getPayRate() {
        return payRate;
    }

    public void setPayRate(double payRate) {
        this.payRate = payRate;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public short getLatePolicyId() {
        return latePolicyId;
    }

    public void setLatePolicyId(short latePolicyId) {
        this.latePolicyId = latePolicyId;
    }

    public short getCancellationPolicyId() {
        return cancellationPolicyId;
    }

    public void setCancellationPolicyId(short cancellationPolicyId) {
        this.cancellationPolicyId = cancellationPolicyId;
    }

    public List<TutorReview> getTutorReviews() {
        return tutorReviews;
    }

    public void setTutorReviews(List<TutorReview> tutorReviews) {
        this.tutorReviews = tutorReviews;
    }
}
