package com.wequan.bu.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 *
 * This class corresponds to the database table bu.wq_user_profile
 * @author ChrisChen
 */
@Data
public class User {
    /**
     * Database Column Remarks:
     *   主键
     */
    private Integer id;

    /**
     * Database Column Remarks:
     *   用户昵称
     */
    private String userName;

    /**
     * Database Column Remarks:
     *   用户邮箱
     */
    private String email;

    /**
     * Database Column Remarks:
     *   用户电话号码
     */
    private String phone;

    /**
     * Database Column Remarks:
     *   用户通信地址
     */
    private String address;

    /**
     * Database Column Remarks:
     *   邮政编码
     */
    private String zipCode;

    /**
     * Database Column Remarks:
     *   用户密码
     */
    @JsonIgnore
    private String credential;

    /**
     * Database Column Remarks:
     *   所在学校id
     */
    private Short schoolId;

    /**
     * Database Column Remarks:
     *   用户学习积分
     */
    private Short studyPoints;

    /**
     * Database Column Remarks:
     *   用户token
     */
    private String accessToken;

    /**
     * Database Column Remarks:
     *   用户的名
     */
    private String firstName;

    /**
     * Database Column Remarks:
     *   用户的姓
     */
    private String lastName;

    /**
     * Database Column Remarks:
     *   邀请码
     */
    private String invitationCode;

    /**
     * Database Column Remarks:
     *    简介
     */
    private String briefIntroduction;

    /**
     * Database Column Remarks:
     *   邮箱是否经过验证
     */
    private Boolean emailVerified;

    /**
     * Database Column Remarks:
     *   第三方名称
     */
    private String provider;

    /**
     * Database Column Remarks:
     *   第三方id
     */
    private String providerId;

    /**
     * Database Column Remarks:
     *   用户在第三方应用上的头像url
     */
    private String avatarUrlInProvider;

    /**
     * Database Column Remarks:
     *   用户头像
     */
    private byte[] avatar;

    /**
     * Database Column Remarks:
     *   创建时间
     */
    private Date createTime;

    /**
     * Database Column Remarks:
     *   更新时间
     */
    private Date updateTime;

    private List<UserFollow> following;
    private List<UserFollow> followed;


}