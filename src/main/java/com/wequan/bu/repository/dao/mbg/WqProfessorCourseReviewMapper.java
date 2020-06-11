package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqProfessorCourseReview;
import java.util.List;

public interface WqProfessorCourseReviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqProfessorCourseReview record);

    WqProfessorCourseReview selectByPrimaryKey(Integer id);

    List<WqProfessorCourseReview> selectAll();

    int updateByPrimaryKey(WqProfessorCourseReview record);
}