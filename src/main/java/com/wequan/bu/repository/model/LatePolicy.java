package com.wequan.bu.repository.model;

import lombok.Data;

/**
 * @author Zhaochao Huang
 */
@Data
public class LatePolicy {
    private Short id;
    private String label;
    private String description;
    private Short overdueAllowed;
}
