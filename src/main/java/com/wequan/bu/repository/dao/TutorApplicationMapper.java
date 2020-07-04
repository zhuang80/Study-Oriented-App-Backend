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
    public TutorApplicationFullInfo selectCurrentApplicationByUserId(@Param("user_id") Integer userId);

    public TutorApplication selectCurrentStatusByUserId(@Param("user_id") Integer userId);
}
