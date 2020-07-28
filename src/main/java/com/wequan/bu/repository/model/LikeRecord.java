package com.wequan.bu.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Database Table Remarks:
 *   点赞记录
 * @author ChrisChen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeRecord {
    /**
     * Database Column Remarks:
     *   用户id，关联user_profile(id)
     */
    private Integer userId;

    /**
     * Database Column Remarks:
     *   点赞的资源id
     */
    private Integer resourceId;

    /**
     * Database Column Remarks:
     *   点赞的资源类型，1 -> tutor; 2 -> course; 3 -> material; 4 -> thread; 5 -> professor; 6 -> activity; 7 -> public class; 8 -> thread reply
     */
    private Short resourceType;

    /**
     * Database Column Remarks:
     *   点赞时间
     */
    private Date createTime;

    /**
     * Database Column Remarks:
     *   资源所属用户id
     */
    @JsonIgnore
    private Integer resourceBelongUserId;


}