package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqFavoriteThread;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WqFavoriteThreadMapper {
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("threadId") Integer threadId);

    int insert(WqFavoriteThread record);

    WqFavoriteThread selectByPrimaryKey(@Param("userId") Integer userId, @Param("threadId") Integer threadId);

    List<WqFavoriteThread> selectAll();

    int updateByPrimaryKey(WqFavoriteThread record);
}