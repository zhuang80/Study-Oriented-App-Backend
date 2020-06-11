package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqAppointmentReview;
import java.util.List;

public interface WqAppointmentReviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqAppointmentReview record);

    WqAppointmentReview selectByPrimaryKey(Integer id);

    List<WqAppointmentReview> selectAll();

    int updateByPrimaryKey(WqAppointmentReview record);
}