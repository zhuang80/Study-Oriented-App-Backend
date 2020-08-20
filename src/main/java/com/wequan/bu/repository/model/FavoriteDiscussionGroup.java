package com.wequan.bu.repository.model;

import lombok.Data;

import java.util.Date;

/**
 * Database Table Remarks:
 *   用户收藏讨论组表
 * This class corresponds to the database table bu.wq_favorite_discussion_group
 * @author ChrisChen
 */
@Data
public class FavoriteDiscussionGroup {
    /**
     * Database Column Remarks:
     *   用户id，关联user_profile(id)
     */
    private Integer userId;

    /**
     * Database Column Remarks:
     *   讨论组id，关联discussion_group(id)
     */
    private Integer discussionGroupId;

    /**
     * Database Column Remarks:
     *   收藏时间
     */
    private Date createTime;

}