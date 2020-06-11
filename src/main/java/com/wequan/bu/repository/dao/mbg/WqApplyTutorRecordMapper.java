package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqApplyTutorRecord;
import java.util.List;

public interface WqApplyTutorRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqApplyTutorRecord record);

    WqApplyTutorRecord selectByPrimaryKey(Integer id);

    List<WqApplyTutorRecord> selectAll();

    int updateByPrimaryKey(WqApplyTutorRecord record);
}