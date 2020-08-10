package com.wequan.bu.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Database Table Remarks:
 *   讨论组（免费形式）
 * This class corresponds to the database table bu.wq_discussion_group
 * @author ChrisChen
 */
@Data
public class DiscussionGroup {
    /**
     * Database Column Remarks:
     *   主键
     */
    private Integer id;

    /**
     * Database Column Remarks:
     *   讨论组名称
     */
    private String name;

    /**
     * Database Column Remarks:
     *   创建者id，关联user_profile(id)
     */
    private Integer createBy;

    /**
     * Database Column Remarks:
     *   讨论组简介
     */
    private String briefDescription;

    /**
     * Database Column Remarks:
     *   创建时间
     */
    private LocalDateTime createTime;

    /**
     * Database Column Remarks:
     *   更新时间，比如更新简介
     */
    private LocalDateTime updateTime;

    /**
     * Database Column Remarks:
     *   -1, unavailable;
     *   1, available
     */
    private Short status;

    /**
     * Database Column Remarks:
     *   组通知
     */
    private String groupMessage;

    /**
     * Database Column Remarks:
     *   组管理员id，创建时同create_by
     */
    private Integer groupManagerId;

    /**
     * Database Column Remarks:
     *   讨论组人数限制
     */
    private Integer groupCapacity;

    /**
     * Database Column Remarks:
     *   讨论组对校外可见性
     */
    private Boolean visible;

    /**
     * Database Column Remarks:
     *   讨论组所属学校，创建时默认为创建者所属学校
     */
    private Short belongSchoolId;

    /**
     * Database Column Remarks:
     *   online event, discussion group的全局id
     */
    private String guid;

    /**
     * Database Column Remarks:
     *   标签id，关联tag(id)，一一对应
     */
    private Short tagId;

    /**
     * Database Column Remarks:
     *   图标
     */
    private String logo;

    private String image;

    private Integer currentHeadcount;

}