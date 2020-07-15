package com.wequan.bu.controller.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author ChrisChen
 */
@Data
@Builder
public class UserVo {

    /**
     *   用户名
     */
    private String userName;

    /**
     *   用户邮箱
     */
    private String email;

    /**
     *   用户电话号码
     */
    private String phone;

    /**
     *   用户通信地址
     */
    private String address;

    /**
     *   邮政编码
     */
    private String zipCode;

    /**
     *   所在学校id
     */
    private Short schoolId;

    /**
     *   用户的名
     */
    private String firstName;

    /**
     *   用户的姓
     */
    private String lastName;

    /**
     *    简介
     */
    private String briefIntroduction;

    /**
     *   用户头像
     */
    private String avatarUrl;

    /**
     *   用户选择的科目id，逗号分隔
     */
    private String subjectIds;

}
