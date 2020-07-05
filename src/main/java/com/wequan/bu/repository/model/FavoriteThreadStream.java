package com.wequan.bu.repository.model;

import lombok.Data;

import java.util.Date;

/**
 * Database Table Remarks:
 *   用户收藏帖子回复表
 * This class corresponds to the database table bu.wq_favorite_thread_stream
 * @author ChrisChen
 */
@Data
public class FavoriteThreadStream {
    /**
     * Database Column Remarks:
     *   用户id，关联user_profile(id)
     */
    private Integer userId;

    /**
     * Database Column Remarks:
     *   帖子回复id，关联thread_stream(id)
     */
    private Integer threadStreamId;

    /**
     * Database Column Remarks:
     *   创建（收藏）时间
     */
    private Date createTime;

}