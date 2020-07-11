package com.wequan.bu.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhaochao Huang
 */
@Data
public class School {
    private Short id;
    private String name;
    private Integer createBy;
    private LocalDateTime createTime;
}
