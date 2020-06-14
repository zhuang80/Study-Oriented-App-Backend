package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqTags;
import java.util.List;

public interface WqTagsMapper {
    int deleteByPrimaryKey(Short id);

    int insert(WqTags record);

    WqTags selectByPrimaryKey(Short id);

    List<WqTags> selectAll();

    int updateByPrimaryKey(WqTags record);
}