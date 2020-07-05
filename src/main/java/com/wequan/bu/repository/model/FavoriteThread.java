package com.wequan.bu.repository.model;

import lombok.Data;

import java.util.Date;

/**
 * Database Table Remarks:
 *   用户收藏帖子表
 * This class corresponds to the database table bu.wq_favorite_thread
 * @author ChrisChen
 */
@Data
public class FavoriteThread {
    /**
     * Database Column Remarks:
     *   用户id，关联user_profile(id)
     */
    private Integer userId;

    /**
     * Database Column Remarks:
     *   帖子id，关联thread(id)
     */
    private Integer threadId;

    /**
     * Database Column Remarks:
     *   创建（收藏）时间
     */
    private Date createTime;

}