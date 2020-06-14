package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqCourseMaterialViewHistory;
import java.util.List;

public interface WqCourseMaterialViewHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqCourseMaterialViewHistory record);

    WqCourseMaterialViewHistory selectByPrimaryKey(Integer id);

    List<WqCourseMaterialViewHistory> selectAll();

    int updateByPrimaryKey(WqCourseMaterialViewHistory record);
}