package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqTutorInquiry;
import java.util.List;

public interface WqTutorInquiryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqTutorInquiry record);

    WqTutorInquiry selectByPrimaryKey(Integer id);

    List<WqTutorInquiry> selectAll();

    int updateByPrimaryKey(WqTutorInquiry record);
}