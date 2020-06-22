package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.TutorApplication;
import com.wequan.bu.repository.model.extend.TutorApplicationFullInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface TutorApplicationMapper extends GeneralMapper<TutorApplication> {
    public List<TutorApplicationFullInfo> selectByUserId(@Param("user_id") Integer userId);

    public List<TutorApplication> selectStatusByUserId(@Param("user_id") Integer userId);
}
