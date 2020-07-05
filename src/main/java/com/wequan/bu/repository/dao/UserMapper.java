package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.User;
import com.wequan.bu.repository.model.extend.UserStats;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ChrisChen
 */
@Mapper
public interface UserMapper extends GeneralMapper<User> {

    /** 根据email查询User
     * @param email email
     * @return User实体
     */
    User selectByEmail(String email);

    /** 检查email是否已经存在
     * @param email email
     * @return 存在或不存在
     */
    Boolean existsByEmail(String email);

    /**
     * 根据email更新email_verified字段
     * @param email email
     * @param verified 是否认证
     * @return
     */
    int updateEmailVerifiedByEmail(@Param("email") String email, @Param("verified") boolean verified);

    /**
     * 根据用户名查询User
     * @param userName 用户名
     * @return User实体
     */
    User selectByUserName(String userName);

    /**
     * 根据用户id查询User
     * @param userId 用户id
     * @return UserStats实体
     */
    UserStats selectById(Integer userId);

    /**
     * 用户积分
     * @param userId 用户id
     * @return study point
     */
    Integer selectStudyPointByUserId(Integer userId);

    /**
     * 根据用户id更新学习积分
     * @param userId 用户id
     * @param amount 积分变动值
     * @return 影响行数
     */
    int updateStudyPointByUserId(@Param("userId") Integer userId, @Param("amount") Short amount);

}