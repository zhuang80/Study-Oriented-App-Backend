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
    private Integer id;
    private User createBy;
    private Subject subject;
    private String briefDescription;
    private Boolean online;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime createTime;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime updateTime;
    private Short status;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime requestStartTime;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime requestEndTime;
    private Float acceptablePayRate;
    private List<Topic> topicList;
}