package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqOnlineEvent;
import java.util.List;

public interface WqOnlineEventMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqOnlineEvent record);

    WqOnlineEvent selectByPrimaryKey(Integer id);

    List<WqOnlineEvent> selectAll();

    int updateByPrimaryKey(WqOnlineEvent record);
}