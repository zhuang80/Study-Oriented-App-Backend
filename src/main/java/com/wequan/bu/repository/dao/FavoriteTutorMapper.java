package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.FavoriteTutor;
import com.wequan.bu.repository.model.extend.FavoriteTutorBriefInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author ChrisChen
 */
@Mapper
public interface FavoriteTutorMapper extends GeneralMapper<FavoriteTutor> {

    /**
     * 获取用户收藏的tutor列表
     * @param userId 用户id
     * @param rowBounds 分页
     * @return 用户收藏的tutor列表
     */
    List<FavoriteTutorBriefInfo> selectByUserId(@Param("userId") Integer userId, RowBounds rowBounds);
}