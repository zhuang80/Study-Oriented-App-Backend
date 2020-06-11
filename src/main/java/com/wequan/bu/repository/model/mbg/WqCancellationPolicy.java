package com.wequan.bu.repository.model.mbg;

public class WqCancellationPolicy {
    private Short id;

    private String label;

    private String description;

    private Float refundRatio;

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

    public Float getRefundRatio() {
        return refundRatio;
    }

    public void setRefundRatio(Float refundRatio) {
        this.refundRatio = refundRatio;
    }
}