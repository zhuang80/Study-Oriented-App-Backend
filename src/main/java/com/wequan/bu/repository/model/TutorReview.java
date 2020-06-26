package com.wequan.bu.repository.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Data
public class TutorReview {
    private Integer id;
    private Float rating;
    private String comment;
    private User createBy;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer tutorId;
    private List<Tag> tagList;
}
