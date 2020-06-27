package com.wequan.bu.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhaochao Huang
 */
@Data
public class TutorReviewTag {
    private Integer id;
    private Integer tutorReviewId;
    private Short tagId;
    private Integer createBy;
    private LocalDateTime createTime;
}
