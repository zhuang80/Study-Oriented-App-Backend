package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqThreadTag;
import java.util.List;

public interface WqThreadTagMapper {
    int deleteByPrimaryKey(Short id);

    int insert(WqThreadTag record);

    WqThreadTag selectByPrimaryKey(Short id);

    List<WqThreadTag> selectAll();

    int updateByPrimaryKey(WqThreadTag record);
}