package com.wequan.bu.controller.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author ChrisChen
 */
@Data
public class Appointment {
    private Integer id;

    private Integer tutorId;

    private Integer userId;

    private Date createTime;

    private Date updateTime;

    private Short status;

    private String briefDescription;

    private Date startTime;

    private Date endTime;

    private Short subjectId;

    private Short topicId;

    private String title;

    private Float fee;

    private String transactionId;

    private Integer reviewId;

}
