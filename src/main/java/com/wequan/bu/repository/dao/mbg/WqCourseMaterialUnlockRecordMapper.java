package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqCourseMaterialUnlockRecord;
import java.util.List;

public interface WqCourseMaterialUnlockRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqCourseMaterialUnlockRecord record);

    WqCourseMaterialUnlockRecord selectByPrimaryKey(Integer id);

    List<WqCourseMaterialUnlockRecord> selectAll();

    int updateByPrimaryKey(WqCourseMaterialUnlockRecord record);
}