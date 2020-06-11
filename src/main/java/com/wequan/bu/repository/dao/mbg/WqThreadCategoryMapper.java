package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqThreadCategory;
import java.util.List;

public interface WqThreadCategoryMapper {
    int deleteByPrimaryKey(Short id);

    int insert(WqThreadCategory record);

    WqThreadCategory selectByPrimaryKey(Short id);

    List<WqThreadCategory> selectAll();

    int updateByPrimaryKey(WqThreadCategory record);
}