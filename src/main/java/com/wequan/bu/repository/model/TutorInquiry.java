package com.wequan.bu.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wequan.bu.json.CustomLocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Database Table Remarks:
 *   user对tutor的inquiry表
 * @author Zhaochao Huang
 */
@Data
@JsonIgnoreProperties("handler")
public class TutorInquiry {
    /**
     * Database Column Remarks:
     *   主键
     */
    private Integer id;

    /**
     * Database Column Remarks:
     *   创建者，关联user_profile(id)
     */
    private User createBy;

    /**
     * Database Column Remarks:
     *   学科id，关联subject(id)
     */
    private Subject subject;

    /**
     * Database Column Remarks:
     *   inquiry简要描述
     */
    private String briefDescription;

    /**
     * Database Column Remarks:
     *   线上/线下
     */
    private Boolean online;

    /**
     * Database Column Remarks:
     *   创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    /**
     * Database Column Remarks:
     *   更新时间，比如更新简要介绍
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    /**
     * Database Column Remarks:
     *   -1, delete;
     *   1, raise
     */
    private Short status;

    /**
     * Database Column Remarks:
     *   辅导开始时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime requestStartTime;

    /**
     * Database Column Remarks:
     *   辅导结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime requestEndTime;

    /**
     * Database Column Remarks:
     *   按小时计
     */
    private Float acceptablePayRate;

    private List<Topic> topicList;
}