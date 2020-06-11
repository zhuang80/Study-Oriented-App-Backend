package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqFavoriteProfessor;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WqFavoriteProfessorMapper {
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("professorId") Integer professorId);

    int insert(WqFavoriteProfessor record);

    WqFavoriteProfessor selectByPrimaryKey(@Param("userId") Integer userId, @Param("professorId") Integer professorId);

    List<WqFavoriteProfessor> selectAll();

    int updateByPrimaryKey(WqFavoriteProfessor record);
}