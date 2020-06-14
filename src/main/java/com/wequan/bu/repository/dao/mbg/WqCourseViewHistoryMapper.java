package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqCourseViewHistory;
import java.util.List;

public interface WqCourseViewHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqCourseViewHistory record);

    WqCourseViewHistory selectByPrimaryKey(Integer id);

    List<WqCourseViewHistory> selectAll();

    int updateByPrimaryKey(WqCourseViewHistory record);
}