package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqOnlineEventMember;
import java.util.List;

public interface WqOnlineEventMemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqOnlineEventMember record);

    WqOnlineEventMember selectByPrimaryKey(Integer id);

    List<WqOnlineEventMember> selectAll();

    int updateByPrimaryKey(WqOnlineEventMember record);
}