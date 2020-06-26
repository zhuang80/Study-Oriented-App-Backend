package com.wequan.bu.controller.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ChrisChen
 */
@Data
public class Appointment {
    private Integer id;

    private Integer tutorId;

    private Integer userId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Short status;

    private String briefDescription;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Short subjectId;

    private Short topicId;

    private String title;

    private Float fee;

    private String transactionId;


}
