package com.wequan.bu.repository.model.mbg;

import java.util.Date;

public class WqInviteFriendRecord {
    private Integer id;

    private Integer userId;

    private String friendEmail;

    private Date inviteTime;

    private Short status;

    private Short studyPointsAccquired;

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

    public String getFriendEmail() {
        return friendEmail;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }

    public Date getInviteTime() {
        return inviteTime;
    }

    public void setInviteTime(Date inviteTime) {
        this.inviteTime = inviteTime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getStudyPointsAccquired() {
        return studyPointsAccquired;
    }

    public void setStudyPointsAccquired(Short studyPointsAccquired) {
        this.studyPointsAccquired = studyPointsAccquired;
    }
}