package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqTopic;
import java.util.List;

public interface WqTopicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqTopic record);

    WqTopic selectByPrimaryKey(Integer id);

    List<WqTopic> selectAll();

    int updateByPrimaryKey(WqTopic record);
}