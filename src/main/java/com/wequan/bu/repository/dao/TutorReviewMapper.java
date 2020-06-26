package com.wequan.bu.repository.dao;

import com.wequan.bu.controller.vo.TutorReview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface TutorReviewMapper extends GeneralMapper<TutorReview> {
    public List<TutorReview> selectByTutorId(@Param("tutor_id") Integer tutorId);
}
