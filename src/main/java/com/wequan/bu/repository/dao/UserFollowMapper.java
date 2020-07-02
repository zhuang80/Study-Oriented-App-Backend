package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.UserFollow;
import com.wequan.bu.repository.model.extend.UserFollowBriefInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author ChrisChen
 */
@Mapper
public interface UserFollowMapper extends GeneralMapper<UserFollow> {

    /**
     * 获取用户所关注的用户
     * @param userId 用户id
     * @param rowBounds 分页
     * @return 用户所关注的用户列表
     */
    List<UserFollowBriefInfo> selectFollowingByUserId(@Param("userId") Integer userId, RowBounds rowBounds);

    /**
     * 获取用户粉丝
     * @param userId 用户id
     * @param rowBounds 分页
     * @return 用户粉丝列表
     */
    List<UserFollowBriefInfo> selectFollowerByUserId(@Param("userId") Integer userId, RowBounds rowBounds);
}
