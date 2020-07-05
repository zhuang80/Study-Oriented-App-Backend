package com.wequan.bu.repository.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Database Table Remarks:
 *   学习积分历史记录
 * This class corresponds to the database table bu.wq_study_point_history
 * @author ChrisChen
 */
@Data
@Builder
public class StudyPointHistory {
    /**
     * Database Column Remarks:
     *   主键
     */
    private Integer id;

    /**
     * Database Column Remarks:
     *   用户id，关联user_profile(id)
     */
    private Integer userId;

    /**
     * Database Column Remarks:
     *   用户执行的动作简要概述
     */
    private String actionLog;

    /**
     * Database Column Remarks:
     *   学习积分变化量
     */
    private Short changeAmount;

    /**
     * Database Column Remarks:
     *   用户动作时间
     */
    private Date actionTime;

    /**
     * Database Column Remarks:
     *   当前剩余学习积分
     */
    private Short remainingAmount;

    /**
     * Database Column Remarks:
     *   当购买积分时，关联transaction(id)
     */
    private String transactionId;

}