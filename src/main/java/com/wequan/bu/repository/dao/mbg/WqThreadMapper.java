package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqThread;
import java.util.List;

public interface WqThreadMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqThread record);

    WqThread selectByPrimaryKey(Integer id);

    List<WqThread> selectAll();

    int updateByPrimaryKey(WqThread record);
}