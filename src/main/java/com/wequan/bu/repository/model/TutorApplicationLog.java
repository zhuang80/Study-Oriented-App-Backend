package com.wequan.bu.repository.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Zhaochao Huang
 */
@Data
public class TutorApplicationLog {
    private Integer userId;
    private Short action;
    private LocalDateTime actionTime;
    private String actionLog;
    private Integer tutorApplicationId;
    private Integer adminId;
}