package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.DiscussionGroup;
import com.wequan.bu.repository.model.DiscussionGroupMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author ChrisChen
 */
@Mapper
public interface DiscussionGroupMapper extends GeneralMapper<DiscussionGroup> {

    /**
     * 根据用户id获取加入的discussion group列表
     * @param userId 用户id
     * @param rowBounds 分页
     * @return discussion group列表
     */
    List<DiscussionGroup> selectByUserId(Integer userId, RowBounds rowBounds);

    /**
     * 用户加入/退出
     * @param discussionGroupMember discussionGroupMember
     * @return 影响行数
     */
    int insertOrUpdateActionByUserId(DiscussionGroupMember discussionGroupMember);

    /**
     * 按条件获取discussion group列表
     * @param whereCondition where
     * @param orderCondition order by
     * @param rowBounds 分页
     * @return discussion group列表
     */
    List<DiscussionGroup> selectByConditions(@Param("where") String whereCondition, @Param("orderBy") String orderCondition, RowBounds rowBounds);

    List<DiscussionGroup> selectBySchoolId(@Param("school_id") Integer schoolId);

    List<Integer> selectMemberIdsByDiscussionGroupId(@Param("discussion_group_id") Integer id);
}
