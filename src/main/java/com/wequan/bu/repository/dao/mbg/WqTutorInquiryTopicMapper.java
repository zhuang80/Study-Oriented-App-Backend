package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqTutorInquiryTopic;
import java.util.List;

public interface WqTutorInquiryTopicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqTutorInquiryTopic record);

    WqTutorInquiryTopic selectByPrimaryKey(Integer id);

    List<WqTutorInquiryTopic> selectAll();

    int updateByPrimaryKey(WqTutorInquiryTopic record);
}