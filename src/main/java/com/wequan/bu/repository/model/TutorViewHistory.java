package com.wequan.bu.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhaochao Huang
 */
@Data
public class TutorViewHistory {
    private Long id;
    private Integer userId;
    private User user;
    private Integer tutorId;
    private LocalDateTime viewTime;
}