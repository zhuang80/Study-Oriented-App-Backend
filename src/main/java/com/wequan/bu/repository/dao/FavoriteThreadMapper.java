package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.FavoriteThread;
import com.wequan.bu.repository.model.extend.FavoriteThreadBriefInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author ChrisChen
 */
@Mapper
public interface FavoriteThreadMapper extends GeneralMapper<FavoriteThread> {

    /**
     * 获取用户收藏的帖子列表
     * @param userId 用户id
     * @param rowBounds 分页
     * @return 用户收藏的帖子列表
     */
    List<FavoriteThreadBriefInfo> selectByUserId(@Param("userId") Integer userId, RowBounds rowBounds);
}