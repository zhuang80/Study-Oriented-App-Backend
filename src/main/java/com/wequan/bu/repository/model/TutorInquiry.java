package com.wequan.bu.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Database Table Remarks:
 *   user对tutor的inquiry表
 * @author Zhaochao Huang
 */
@Data
public class TutorInquiry {
    private Integer id;
    private User createBy;
    private Subject subject;
    private String briefDescription;
    private Boolean online;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Short status;
    private LocalDateTime requestStartTime;
    private LocalDateTime requestEndTime;
    private Float acceptablePayRate;
}