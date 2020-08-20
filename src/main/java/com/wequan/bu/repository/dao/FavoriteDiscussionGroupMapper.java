package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.FavoriteDiscussionGroup;
import com.wequan.bu.repository.model.extend.FavoriteDiscussionGroupBriefInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author ChrisChen
 */
@Mapper
public interface FavoriteDiscussionGroupMapper extends GeneralMapper<FavoriteDiscussionGroup>{

    /**
     * 获取用户收藏的讨论组列表
     * @param userId 用户id
     * @param rowBounds 分页
     * @return 用户收藏的讨论组列表
     */
    List<FavoriteDiscussionGroupBriefInfo> selectByUserId(@Param("userId") Integer userId, RowBounds rowBounds);

    /**
     * 用户是否收藏讨论组
     * @param userId 用户id
     * @param favoriteId discussion group id
     * @return 收藏与否
     */
    boolean checkFavorite(@Param("userId") Integer userId, @Param("discussionGroupId") Integer favoriteId);
}
