package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.User;
import org.apache.ibatis.annotations.Mapper;

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
}