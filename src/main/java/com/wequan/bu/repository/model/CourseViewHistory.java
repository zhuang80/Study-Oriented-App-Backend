package com.wequan.bu.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhaochao Huang
 */
@Data
public class CourseViewHistory {
    private Integer id;
    private Integer userId;
    private Integer courseId;
    private LocalDateTime viewTime;
}
