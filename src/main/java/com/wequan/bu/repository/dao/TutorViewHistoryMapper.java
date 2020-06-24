package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.TutorViewHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface TutorViewHistoryMapper extends GeneralMapper<TutorViewHistory> {

    public List<TutorViewHistory> selectByTutorId(@Param("tutor_id") Integer tutorId);
}
