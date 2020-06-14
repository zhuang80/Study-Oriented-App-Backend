package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqFavoriteCourseMaterial;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WqFavoriteCourseMaterialMapper {
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("courseMaterialId") Integer courseMaterialId);

    int insert(WqFavoriteCourseMaterial record);

    WqFavoriteCourseMaterial selectByPrimaryKey(@Param("userId") Integer userId, @Param("courseMaterialId") Integer courseMaterialId);

    List<WqFavoriteCourseMaterial> selectAll();

    int updateByPrimaryKey(WqFavoriteCourseMaterial record);
}