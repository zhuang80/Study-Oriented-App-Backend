package com.wequan.bu.controller.vo;

import com.wequan.bu.repository.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TutorReview {
    private int id;
    private int createBy;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private double rating;
    private String tags;
    private String comment;
    private Integer tutorId;
}
