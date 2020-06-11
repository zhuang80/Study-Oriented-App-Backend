package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqDiscussionGroupMember;
import java.util.List;

public interface WqDiscussionGroupMemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqDiscussionGroupMember record);

    WqDiscussionGroupMember selectByPrimaryKey(Integer id);

    List<WqDiscussionGroupMember> selectAll();

    int updateByPrimaryKey(WqDiscussionGroupMember record);
}