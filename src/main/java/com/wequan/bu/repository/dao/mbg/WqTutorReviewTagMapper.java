package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqTutorReviewTag;
import java.util.List;

public interface WqTutorReviewTagMapper {
    int insert(WqTutorReviewTag record);

    List<WqTutorReviewTag> selectAll();
}