package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqTutorReview;
import java.util.List;

public interface WqTutorReviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqTutorReview record);

    WqTutorReview selectByPrimaryKey(Integer id);

    List<WqTutorReview> selectAll();

    int updateByPrimaryKey(WqTutorReview record);
}