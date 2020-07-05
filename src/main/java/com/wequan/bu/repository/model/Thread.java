package com.wequan.bu.repository.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Database Table Remarks:
 *   帖子列表
 * @author ChrisChen
 */
@Data
public class Thread {
    /**
     * Database Column Remarks:
     *   主键
     */
    private Integer id;

    /**
     * Database Column Remarks:
     *   创建帖子用户id，关联user_profile(id)
     */
    private Integer createBy;

    /**
     * Database Column Remarks:
     *   帖子标题
     */
    private String title;

    /**
     * Database Column Remarks:
     *   帖子所属类别，两个（学习，非学习），关联category(id)，一一对应
     */
    private Short category;

    /**
     * Database Column Remarks:
     *   帖子所属标签，关联tag(id) ，一一对应
     */
    private Short tagId;

    /**
     * Database Column Remarks:
     *   帖子内容
     */
    private String content;

    /**
     * Database Column Remarks:
     *   悬赏贴的学习积分
     */
    private Short studyPointsBonus;

    /**
     * Database Column Remarks:
     *   帖子创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date createTime;

    /**
     * Database Column Remarks:
     *   帖子更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date updateTime;

    /**
     * Database Column Remarks:
     *   帖子点赞数
     */
    private Integer likes;

    /**
     * Database Column Remarks:
     *   帖子拍砖数
     */
    private Integer dislikes;

    /**
     *帖子状态，0 - open; 1-closed
     */
    private Integer status;

    /**
     *学校id
     */
    private Integer schoolId;


}