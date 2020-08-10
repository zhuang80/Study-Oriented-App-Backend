package com.wequan.bu.repository.model;

import lombok.Data;

import java.util.Date;

/**
 * Database Table Remarks:
 *   用户收藏tutor inquiry表
 * This class corresponds to the database table bu.wq_favorite_tutor_inquiry
 * @author ChrisChen
 */
@Data
public class FavoriteTutorInquiry {
    /**
     * Database Column Remarks:
     *   用户id，关联user_profile(id)
     */
    private Integer userId;

    /**
     * Database Column Remarks:
     *   tutor inquiry id，关联tutor inquiry(id)
     */
    private Integer tutorInquiryId;

    /**
     * Database Column Remarks:
     *   收藏时间
     */
    private Date createTime;

}