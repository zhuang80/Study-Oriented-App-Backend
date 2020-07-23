package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.Thread;
import com.wequan.bu.repository.model.extend.ThreadStats;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

/**
 * @author ChrisChen
 */
@Mapper
public interface ThreadMapper extends GeneralMapper<Thread>{

    /**
     * 根据schoolId获取帖子列表
     * @param schoolId schoolId
     * @param rowBounds 分页
     * @return 帖子列表
     */
    List<Thread> selectBySchoolId(Integer schoolId, RowBounds rowBounds);

    /**
     * 根据schoolId，tagId获取帖子列表
     * @param schoolId schoolId
     * @param tagId 标签id
     * @param rowBounds 分页
     * @return 帖子列表
     */
    List<Thread> selectBySchoolIdAndTag(@Param("schoolId") Integer schoolId, @Param("tagId") Integer tagId, RowBounds rowBounds);

    /**
     * 对帖子点赞（只增加点赞数）
     * @param threadId 帖子id
     * @param userId 用户id
     */
    void likeThread(@Param("threadId") Integer threadId, @Param("userId") Integer userId);

    /**
     * 对帖子拍砖（只增加拍砖数）
     * @param threadId 帖子id
     * @param userId 用户id
     */
    void dislikeThread(@Param("threadId") Integer threadId, @Param("userId") Integer userId);

    /**
     * 添加/更新用户感兴趣的科目ids
     * @param userId 用户id
     * @param subjectIds subjectIds
     */
    void insertUserSelectedSubjects(@Param("userId") Integer userId, @Param("subjectIds") String subjectIds);

    /**
     * 根据用户关注的人，获取thread列表
     * @param userId 用户id
     * @param rowBounds 分页
     * @return 帖子列表
     */
    List<Thread> selectByUserFollowing(@Param("userId") Integer userId, RowBounds rowBounds);

    /**
     * 获取用户之前选择的科目ids
     * @param userId 用户id
     * @return 用户之前选择的科目ids
     */
    String selectSubjectIdsUserSelected(Integer userId);

    /**
     * 删除用户感兴趣的科目
     * @param userId 用户id
     */
    void deleteUserSelectedSubjects(Integer userId);

    /**
     * 根据school id获取帖子列表
     * @param schoolId schoolId
     * @param rowBounds 分页
     * @return 帖子列表
     */
    List<Thread> selectBySchoolIdOrderedByView(Integer schoolId, RowBounds rowBounds);

    /**
     * 获取用户在学习板块感兴趣科目涉及的帖子列表
     * @param userId 用户id
     * @param subjectIds 科目ids
     * @return 帖子列表
     */
    List<Thread> selectUserInterestedStudyHelpThreads(@Param("userId") Integer userId, @Param("subjectIds") List<Integer> subjectIds, RowBounds rowBounds);

    /**
     * 返回用户创建的帖子列表，带分页
     * @param userId 用户id
     * @param rowBounds 分页
     * @return 用户创建的帖子列表
     */
    List<ThreadStats> selectByUserId(@Param("userId") Integer userId, RowBounds rowBounds);

    /**
     * 根据主键查询包含统计的帖子信息
     * @param id 主键
     * @return 包含统计的帖子信息
     */
    ThreadStats selectThreadStatsById(Integer id);

    /**
     * 批量插入帖子所属科目id
     * @param threadId 帖子id
     * @param subjectIds 科目ids
     * @param createTime 创建时间
     */
    void insertSubjectIds(@Param("threadId") Integer threadId, @Param("subjectIds") List<Integer> subjectIds, @Param("createTime") Date createTime);

    /**
     * 插入/更新帖子被查看记录
     * @param userId 用户id
     * @param threadId 帖子id
     * @param viewTime 帖子查看时间
     */
    void insertViewHistory(@Param("userId") Integer userId, @Param("threadId") Integer threadId, @Param("viewTime") Date viewTime);
}