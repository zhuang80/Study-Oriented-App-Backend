package com.wequan.bu.repository.model;

import lombok.Data;

import java.util.Date;

/**
 * @author Zhaochao Huang
 */
@Data
public class TutorViewHistory {
    private Long id;
    private Integer userId;
    private Integer tutorId;
    private Date viewTime;
}