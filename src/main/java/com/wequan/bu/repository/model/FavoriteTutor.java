package com.wequan.bu.repository.model;

import lombok.Data;

import java.util.Date;

/**
 * Database Table Remarks:
 *   用户收藏辅导教师表
 * This class corresponds to the database table bu.wq_favorite_tutor
 * @author ChrisChen
 */
@Data
public class FavoriteTutor {
    /**
     * Database Column Remarks:
     *   用户id，关联user_profile(id)
     */
    private Integer userId;

    /**
     * Database Column Remarks:
     *   tutor id，关联tutor_profile(id)
     */
    private Integer tutorId;

    /**
     * Database Column Remarks:
     *   创建（收藏）时间
     */
    private Date createTime;

}