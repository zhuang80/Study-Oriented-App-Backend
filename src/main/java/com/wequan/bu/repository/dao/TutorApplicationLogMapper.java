package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.TutorApplicationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface TutorApplicationLogMapper extends GeneralMapper<TutorApplicationLog> {

    public List<TutorApplicationLog> selectByUserId(@Param("user_id") Integer userId);
}
