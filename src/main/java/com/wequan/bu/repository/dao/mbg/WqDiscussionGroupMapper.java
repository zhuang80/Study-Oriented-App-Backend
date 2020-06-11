package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqDiscussionGroup;
import java.util.List;

public interface WqDiscussionGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqDiscussionGroup record);

    WqDiscussionGroup selectByPrimaryKey(Integer id);

    List<WqDiscussionGroup> selectAll();

    int updateByPrimaryKey(WqDiscussionGroup record);
}