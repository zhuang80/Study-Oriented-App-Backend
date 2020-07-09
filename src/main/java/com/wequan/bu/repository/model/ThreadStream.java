package com.wequan.bu.repository.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wequan.bu.repository.model.extend.UserBriefInfo;
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
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date createTime;

    /**
     * Database Column Remarks:
     *   更新帖子时间
     */
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
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

    /**
     * 用户信息
     */
    private UserBriefInfo userBriefInfo;

    /**
     * 同回复帖子关联的文件（图片，文件等）的S3链接id，多个id用逗号分隔，关联thread_resource(id)
     */
    private String resourceIds;

    private List<ThreadResource> threadResources;

}