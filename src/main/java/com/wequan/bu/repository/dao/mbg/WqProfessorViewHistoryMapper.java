package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqProfessorViewHistory;
import java.util.List;

public interface WqProfessorViewHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqProfessorViewHistory record);

    WqProfessorViewHistory selectByPrimaryKey(Integer id);

    List<WqProfessorViewHistory> selectAll();

    int updateByPrimaryKey(WqProfessorViewHistory record);
}