package com.wequan.bu.repository.model;

import com.wequan.bu.repository.model.extend.AppointmentBriefInfo;
import lombok.Data;

/**
 * Database Table Remarks:
 *   辅导评价表
 * This class corresponds to the database table bu.wq_appointment_review
 * @author ChrisChen
 */
@Data
public class AppointmentReview {
    /**
     * Database Column Remarks:
     *   主键
     */
    private Integer id;

    /**
     * Database Column Remarks:
     *   辅导id，关联appointment(id)
     */
    private Integer appointmentId;

    /**
     * Database Column Remarks:
     *   评价辅导的用户id，关联user_profile(id)
     */
    private Integer userId;

    /**
     * Database Column Remarks:
     *   用户评价
     */
    private String review;

    /**
     * Database Column Remarks:
     *   用户评分
     */
    private Short score;

    private AppointmentBriefInfo appointment;

}