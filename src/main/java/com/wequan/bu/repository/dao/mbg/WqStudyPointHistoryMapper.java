package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqStudyPointHistory;
import java.util.List;

public interface WqStudyPointHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqStudyPointHistory record);

    WqStudyPointHistory selectByPrimaryKey(Integer id);

    List<WqStudyPointHistory> selectAll();

    int updateByPrimaryKey(WqStudyPointHistory record);
}