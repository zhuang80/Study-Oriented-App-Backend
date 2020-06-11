package com.wequan.bu.repository.model.mbg;

public class WqLatePolicy {
    private Short id;

    private String label;

    private String description;

    private Short overdueAllowed;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getOverdueAllowed() {
        return overdueAllowed;
    }

    public void setOverdueAllowed(Short overdueAllowed) {
        this.overdueAllowed = overdueAllowed;
    }
}