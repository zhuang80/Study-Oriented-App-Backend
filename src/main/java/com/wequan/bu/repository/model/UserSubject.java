package com.wequan.bu.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Database Table Remarks:
 *   用户与学科对应表（多对多）
 * This class corresponds to the database table bu.wq_user_subject
 * @author ChrisChen
 */
@Data
@AllArgsConstructor
public class UserSubject {
    /**
     * Database Column Remarks:
     *   用户id，关联user_profile(id)
     */
    private Integer userId;

    /**
     * Database Column Remarks:
     *   学科id，关联subject(id)
     */
    private Integer subjectId;

    /**
     * Database Column Remarks:
     *   创建时间
     */
    private Date createTime;

}