package com.wequan.bu.repository.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Database Table Remarks:
 *   帖子回复记录
 * This class corresponds to the database table bu.wq_thread_stream
 * @author ChrisChen
 */
@Data
public class ThreadStream {
    /**
     * Database Column Remarks:
     *   主键
     */
    private Integer id;

    /**
     * Database Column Remarks:
     *   回复帖子的用户id，关联user_profile(id)
     */
    private Integer userId;

    /**
     * Database Column Remarks:
     *   帖子id，关联thread(id)
     */
    private Integer threadId;

    /**
     * Database Column Remarks:
     *   帖子评论
     */
    private String content;

    /**
     * Database Column Remarks:
     *   回复几楼
     */
    private Integer toStreamId;

    /**
     * Database Column Remarks:
     *   回复帖子时间
     */
    private Date createTime;

    /**
     * Database Column Remarks:
     *   更新帖子时间
     */
    private Date updateTime;

    /**
     * Database Column Remarks:
     *   点赞数
     */
    private Integer likes;

    /**
     * Database Column Remarks:
     *   拍砖数
     */
    private Integer dislikes;

    private List<ThreadResource> threadResources;

}