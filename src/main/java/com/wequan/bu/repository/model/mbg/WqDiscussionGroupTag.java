package com.wequan.bu.repository.model.mbg;

public class WqDiscussionGroupTag {
    private Short id;

    private String name;

    private Integer discussionGroupId;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDiscussionGroupId() {
        return discussionGroupId;
    }

    public void setDiscussionGroupId(Integer discussionGroupId) {
        this.discussionGroupId = discussionGroupId;
    }
}