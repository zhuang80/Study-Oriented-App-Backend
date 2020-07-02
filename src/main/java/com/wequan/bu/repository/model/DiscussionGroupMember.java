package com.wequan.bu.repository.model;

import lombok.Data;

import java.util.Date;

/**
 * Database Table Remarks:
 *   讨论组成员表
 * This class corresponds to the database table bu.wq_discussion_group_member
 */
@Data
public class DiscussionGroupMember {
    /**
     * Database Column Remarks:
     *   主键
     */
    private Integer id;

    /**
     * Database Column Remarks:
     *   关联discussion_group(id)
     */
    private Integer discussionGroupId;

    /**
     * Database Column Remarks:
     *   关联user_profile(id)
     */
    private Integer memberId;

    /**
     * Database Column Remarks:
     *   join; leave
     */
    private String action;

    /**
     * Database Column Remarks:
     *   action时间
     */
    private Date actionTime;

}