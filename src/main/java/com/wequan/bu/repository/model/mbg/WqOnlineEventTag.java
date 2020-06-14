package com.wequan.bu.repository.model.mbg;

public class WqOnlineEventTag {
    private Short id;

    private String name;

    private Integer onlineEventId;

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

    public Integer getOnlineEventId() {
        return onlineEventId;
    }

    public void setOnlineEventId(Integer onlineEventId) {
        this.onlineEventId = onlineEventId;
    }
}