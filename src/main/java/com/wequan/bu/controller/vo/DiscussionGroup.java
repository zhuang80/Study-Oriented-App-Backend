package com.wequan.bu.controller.vo;

import java.util.List;

/**
 * @author ChrisChen
 */
public class DiscussionGroup extends BaseVo {

    private String name;
    private String briefDescription;
    private short status;
    private String groupMessage;
    private int groupManagerId;
    private int groupCapacity;
    private byte[] logo;
    private byte[] image;
    private boolean visible;
    private short belongSchoolId;
    private int numberOfMember;
    private List<Integer> tags;
    private String guid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
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

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public short getBelongSchoolId() {
        return belongSchoolId;
    }

    public void setBelongSchoolId(short belongSchoolId) {
        this.belongSchoolId = belongSchoolId;
    }

    public int getNumberOfMember() {
        return numberOfMember;
    }

    public void setNumberOfMember(int numberOfMember) {
        this.numberOfMember = numberOfMember;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
