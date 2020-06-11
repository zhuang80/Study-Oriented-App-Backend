package com.wequan.bu.controller.vo;

/**
 * @author ChrisChen
 */
public class DiscussionGroup extends BaseVo {

    private String name;
    private String briefIntroduction;
    private short category;
    private short status;
    private String groupMessage;
    private int groupManagerId;
    private int groupCapacity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBriefIntroduction() {
        return briefIntroduction;
    }

    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }

    public short getCategory() {
        return category;
    }

    public void setCategory(short category) {
        this.category = category;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getGroupMessage() {
        return groupMessage;
    }

    public void setGroupMessage(String groupMessage) {
        this.groupMessage = groupMessage;
    }

    public int getGroupManagerId() {
        return groupManagerId;
    }

    public void setGroupManagerId(int groupManagerId) {
        this.groupManagerId = groupManagerId;
    }

    public int getGroupCapacity() {
        return groupCapacity;
    }

    public void setGroupCapacity(int groupCapacity) {
        this.groupCapacity = groupCapacity;
    }
}
