package com.wequan.bu.service;

import com.wequan.bu.controller.vo.ThreadVo;
import com.wequan.bu.repository.model.Thread;
import com.wequan.bu.repository.model.extend.ThreadStats;

import java.util.Date;
import java.util.List;


/**
 * @author ChrisChen
 */
public interface ThreadService extends Service<Thread> {

    /**
     * 根据帖子id查找
     * @param id 帖子id
     * @return 帖子信息
     */
    Thread findByPrimaryKey(Integer id);

    /**
     * 添加帖子
     * @param record ThreadVo对象
     * @return 被添加Thread的id
     */
    int addThread(ThreadVo record);

    /**
     * 根据school id, tag id获取帖子列表
     * @param schoolId schoolId
     * @param tagId 标签id
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 帖子列表
     */
    List<Thread> findBySchoolAndTag(Integer schoolId, Integer tagId, Integer pageNum, Integer pageSize);

    /**
     * 根据schoolId获取帖子列表
     * @param schoolId schoolId
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 帖子列表
     */
    List<Thread> findByOtherSchoolId(Integer schoolId, Integer pageNum, Integer pageSize);

    /**
     * 对帖子点赞
     * @param threadId 帖子id
     * @param userId 用户id
     */
    void likeThread(Integer threadId, Integer userId);

    /**
     * 对帖子拍砖
     * @param threadId 帖子id
     * @param userId 用户id
     */
    void dislikeThread(Integer threadId, Integer userId);

    /**
     * 科目ids
     * @param userId 用户id
     * @return 科目id，多个id以逗号分隔
     */
    String findUserSelectedSubjects(Integer userId);

    /**
     * 添加/更新用户感兴趣的科目ids
     * @param userId 用户id
     * @param subjectIds 科目ids
     */
    void addUserSelectedSubjects(Integer userId, String subjectIds);

    /**
     * 删除用户感兴趣的科目
     * @param userId 用户id
     */
    void deleteUserSelectedSubjects(Integer userId);

    /**
     * 根据用户关注的人，获取thread列表
     * @param userId 用户id
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 帖子列表
     */
    List<Thread> getUserFollowingThreads(Integer userId, Integer pageNum, Integer pageSize);

    /**
     * 根据school id获取帖子列表
     * @param schoolId schoolId
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 帖子列表
     */
    List<Thread> findBySchoolIdOrderByView(Integer schoolId, Integer pageNum, Integer pageSize);

    /**
     * 对帖子进行举报
     * @param threadId 帖子id
     * @param userId 用户id
     * @param reason 举报原因
     */
    void reportThread(Integer threadId, Integer userId, String reason);

    /**
     * 对回帖进行举报
     * @param threadId 帖子id
     * @param replyId 回帖id
     * @param userId 用户id
     * @param reason 举报原因
     */
    void reportThreadReply(Integer threadId, Integer replyId, Integer userId, String reason);

    /**
     * 获取用户在学习板块感兴趣科目涉及的帖子列表
     * @param userId 用户id
     * @param subjectIds 科目ids
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 帖子列表
     */
    List<Thread> getUserInterestedStudyHelpThreads(Integer userId, String subjectIds, Integer pageNum, Integer pageSize);

    /**
     * 获取用户创建的帖子列表
     * @param userId 用户id
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 用户创建的帖子列表
     */
    List<ThreadStats> getUserThreads(Integer userId, Integer pageNum, Integer pageSize);

    /**
     * 添加帖子所属科目id
     * @param threadId 帖子id
     * @param subjectIds 科目ids
     */
    void addThreadSubjects(int threadId, List<Integer> subjectIds);

    /**
     * 记录帖子被查看
     * @param userId 用户id
     * @param threadId 帖子id
     * @param viewTime 查看时间
     */
    void addViewRecord(int userId, int threadId, Date viewTime);
}
