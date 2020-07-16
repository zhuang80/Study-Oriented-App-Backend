package com.wequan.bu.repository.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Database Table Remarks:
 *   Online event表
 * This class corresponds to the database table bu.wq_online_event
 * @author ChrisChen
 */
@Data
public class OnlineEvent {
    /**
     * Database Column Remarks:
     *   主键
     */
    private Integer id;

    /**
     * Database Column Remarks:
     *   Online event名称
     */
    private String name;

    /**
     * Database Column Remarks:
     *   0, public class; 1, activity
     */
    private Short type;

    /**
     * Database Column Remarks:
     *   Online event简介
     */
    private String briefDescription;

    /**
     * Database Column Remarks:
     *   创建者id，关联user_profile(id)
     */
    private Integer createBy;

    /**
     * Database Column Remarks:
     *   创建时间
     */
    private LocalDateTime createTime;

    /**
     * Database Column Remarks:
     *   更新时间，比如更新简介
     */
    private LocalDateTime updateTime;

    /**
     * Database Column Remarks:
     *   -1, cancel/delete; 
     *   0, scheduled; 
     *   1, ongoing; 
     *   2, done
     */
    private Short status;

    /**
     * Database Column Remarks:
     *   费用
     */
    private Integer fee;

    /**
     * Database Column Remarks:
     *   event发生方式
     */
    private String method;

    /**
     * Database Column Remarks:
     *   开始时间
     */
    private LocalDateTime startTime;

    /**
     * Database Column Remarks:
     *   结束时间
     */
    private LocalDateTime endTime;

    /**
     * Database Column Remarks:
     *   Online event对校外可见性
     */
    private Boolean visible;

    /**
     * Database Column Remarks:
     *   Online event所属学校，创建时默认为创建者所属学校
     */
    private Short belongSchoolId;

    /**
     * Database Column Remarks:
     *   online event, discussion group的全局id
     */
    private String guid;

    /**
     * Database Column Remarks:
     *   event发生方式具体位置
     */
    private String methodDetail;

    /**
     * Database Column Remarks:
     *   标签id，关联tag(id)，一一对应
     */
    private Short tagId;

    /**
     * Database Column Remarks:
     * logo
     */
    private String logo;

    /**
     * Database Column Remarks:
     * image
     */
    private String image;

    private int numberOfMember;

}