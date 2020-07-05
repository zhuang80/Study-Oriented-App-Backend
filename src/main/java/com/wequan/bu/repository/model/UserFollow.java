package com.wequan.bu.repository.model;

import lombok.Data;

import java.util.Date;

/**
 * Database Table Remarks:
 *   用户关注列表
 * This class corresponds to the database table bu.wq_user_follow
 * @author ChrisChen
 */
@Data
public class UserFollow {
    /**
     * Database Column Remarks:
     *   用户id，关联user_profile(id)
     */
    private Integer userId;

    /**
     * Database Column Remarks:
     *   用户所关注的用户id，关联user_profile(id)
     */
    private Integer followId;

    /**
     * Database Column Remarks:
     *   关注/被关注时间
     */
    private Date createTime;

}