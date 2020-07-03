package com.wequan.bu.repository.model;

import lombok.Data;

/**
 * @author Zhaochao Huang
 */
@Data
public class CancellationPolicy {
    private Short id;
    private String label;
    private String description;
    private Float refundRatio;
}