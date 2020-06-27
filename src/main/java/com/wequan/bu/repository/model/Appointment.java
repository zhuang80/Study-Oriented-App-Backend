package com.wequan.bu.repository.model;

import lombok.Data;

import java.util.Date;

/**
 * Database Table Remarks:
 *   辅导信息表
 * This class corresponds to the database table bu.wq_appointment
 */
@Data
public class Appointment {
    /**
     * Database Column Remarks:
     *   主键
     */
    private Integer id;

    /**
     * Database Column Remarks:
     *   辅导教师Tutor id，关联tutor_profile(id)
     */
    private Integer tutorId;

    /**
     * Database Column Remarks:
     *   用户id，关联user_profile(id)
     */
    private Integer userId;

    /**
     * Database Column Remarks:
     *   辅导信息创建时间
     */
    private Date createTime;

    /**
     * Database Column Remarks:
     *   辅导信息更新时间
     */
    private Date updateTime;

    /**
     * Database Column Remarks:
     *   -1, cancel/delete
     *   0, initial/default
     *   1, completed
     */
    private Short status;

    /**
     * Database Column Remarks:
     *   辅导简介
     */
    private String briefDescription;

    /**
     * Database Column Remarks:
     *   辅导开始时间
     */
    private Date startTime;

    /**
     * Database Column Remarks:
     *   辅导结束时间
     */
    private Date endTime;

    /**
     * Database Column Remarks:
     *   辅导学科id，关联subject(id)
     */
    private Short subjectId;

    /**
     * Database Column Remarks:
     *   具体辅导的topic id，关联topic(id)
     */
    private Short topicId;

    /**
     * Database Column Remarks:
     *   交易名称
     */
    private String title;

    /**
     * Database Column Remarks:
     *   交易费用
     */
    private Float fee;

    /**
     * Database Column Remarks:
     *   交易id，关联transaction(id)
     */
    private String transactionId;

}