package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqThreadViewHistory;
import java.util.List;

public interface WqThreadViewHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqThreadViewHistory record);

    WqThreadViewHistory selectByPrimaryKey(Integer id);

    List<WqThreadViewHistory> selectAll();

    int updateByPrimaryKey(WqThreadViewHistory record);
}