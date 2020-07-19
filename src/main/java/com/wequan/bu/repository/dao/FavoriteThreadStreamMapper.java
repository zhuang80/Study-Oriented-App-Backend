package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.FavoriteThreadStream;
import com.wequan.bu.repository.model.extend.FavoriteThreadStreamBriefInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author ChrisChen
 */
@Mapper
public interface FavoriteThreadStreamMapper extends GeneralMapper<FavoriteThreadStream> {

    /**
     * 获取用户收藏的帖子回复列表
     * @param userId 用户id
     * @param rowBounds 分页
     * @return 用户收藏的帖子回复列表
     */
    List<FavoriteThreadStreamBriefInfo> selectByUserId(@Param("userId") Integer userId, RowBounds rowBounds);

    /**
     * 用户是否收藏reply
     * @param userId 用户id
     * @param favoriteId reply id
     * @return 收藏与否
     */
    boolean checkFavorite(@Param("userId") Integer userId, @Param("threadStreamId") Integer favoriteId);
}