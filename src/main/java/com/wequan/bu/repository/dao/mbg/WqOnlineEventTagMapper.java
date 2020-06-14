package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqOnlineEventTag;
import java.util.List;

public interface WqOnlineEventTagMapper {
    int deleteByPrimaryKey(Short id);

    int insert(WqOnlineEventTag record);

    WqOnlineEventTag selectByPrimaryKey(Short id);

    List<WqOnlineEventTag> selectAll();

    int updateByPrimaryKey(WqOnlineEventTag record);
}