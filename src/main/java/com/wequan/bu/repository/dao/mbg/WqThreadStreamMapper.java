package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqThreadStream;
import java.util.List;

public interface WqThreadStreamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqThreadStream record);

    WqThreadStream selectByPrimaryKey(Integer id);

    List<WqThreadStream> selectAll();

    int updateByPrimaryKey(WqThreadStream record);
}