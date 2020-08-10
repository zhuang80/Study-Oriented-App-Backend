package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.FavoriteTutorInquiry;
import com.wequan.bu.repository.model.extend.FavoriteTutorInquiryBriefInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author ChrisChen
 */
@Mapper
public interface FavoriteTutorInquiryMapper extends GeneralMapper<FavoriteTutorInquiry>{

    /**
     * 获取用户收藏的tutor inquiry列表
     * @param userId 用户id
     * @param rowBounds 分页
     * @return 用户收藏的tutor inquiry列表
     */
    List<FavoriteTutorInquiryBriefInfo> selectByUserId(@Param("userId") Integer userId, RowBounds rowBounds);

    /**
     * 用户是否收藏tutor inquiry
     * @param userId 用户id
     * @param favoriteId tutor inquiry id
     * @return 收藏与否
     */
    boolean checkFavorite(@Param("userId") Integer userId, @Param("tutorInquiryId") Integer favoriteId);

}
