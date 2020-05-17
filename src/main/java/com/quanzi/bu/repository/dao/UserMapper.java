package com.quanzi.bu.repository.dao;

import com.quanzi.bu.repository.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ChrisChen
 */
@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Integer id);
    int insert(User record);
    User selectByPrimaryKey(Integer id);
    List<User> selectAll();
    int updateByPrimaryKey(User record);
    User selectByEmail(String email);
}