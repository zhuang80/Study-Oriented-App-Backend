package com.wequan.bu.repository.model.mbg;

import java.util.Date;

public class WqDiscussionGroupMember {
    private Integer id;

    private Integer discussionGroupId;

    private Integer memberId;

    private String action;

    private Date actionTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDiscussionGroupId() {
        return discussionGroupId;
    }

    public void setDiscussionGroupId(Integer discussionGroupId) {
        this.discussionGroupId = discussionGroupId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }
}