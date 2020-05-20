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

}