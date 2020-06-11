package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqDiscussionGroupTag;
import java.util.List;

public interface WqDiscussionGroupTagMapper {
    int deleteByPrimaryKey(Short id);

    int insert(WqDiscussionGroupTag record);

    WqDiscussionGroupTag selectByPrimaryKey(Short id);

    List<WqDiscussionGroupTag> selectAll();

    int updateByPrimaryKey(WqDiscussionGroupTag record);
}