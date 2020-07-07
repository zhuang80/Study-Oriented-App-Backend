package com.wequan.bu.service;

import com.wequan.bu.repository.model.Thread;
import com.wequan.bu.repository.model.ThreadStream;
import com.wequan.bu.repository.model.ThreadUserSelectedSubjects;
import com.wequan.bu.repository.model.extend.ThreadStats;

import java.util.List;


public interface ThreadService extends Service<Thread> {

    public Thread findByPrimaryKey(Integer id);

    public int deleteByPrimaryKey(Integer id);

    public void insert(Thread record);

    /**
     * 6/20
     * @param threadStream
     * @return
     */
    public void insertReply(ThreadStream threadStream);

    public int insertSelective(Thread record);

    public void updateByIdSelective(Thread record);

    public void updateByKey(Thread record);

    /**
     * 6/20
     * @param threadStream
     * @return
     */
    public void replyToThread(ThreadStream threadStream);

    /**
     * 6/19
     * @param schoolId
     * @param tagId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Thread> findBySchoolAndTag(Integer schoolId, Integer tagId, Integer pageNum, Integer pageSize);

    /**
     * 6/22
     * @param schoolId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Thread> findByOtherSchoolId(Integer schoolId, Integer pageNum, Integer pageSize);
    /**
     * 6/20
     * @param threadId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<ThreadStream> getDirectThreadReplies(Integer threadId,Integer pageNum, Integer pageSize);

    /**
     * 6/22
     * @param threadId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<ThreadStream> getIndirectThreadReplies(Integer threadId,Integer pageNum, Integer pageSize);
    /**
     * 6/20
     * @param threadId
     * @param userId
     */
    public void likeThread(Integer threadId, Integer userId);

    /**
     * 6/20
     * @param threadId
     * @param userId
     */
    public void dislikeThread(Integer threadId, Integer userId);

    /**
     *6/19
     * @param threadId
     * @param replyId
     * @param userId
     */
    public void likeReplyOfThread(Integer threadId, Integer replyId, Integer userId);

    /**
     *6/19
     * @param threadId
     * @param replyId
     * @param userId
     */
    public void dislikeReplyOfThread(Integer threadId, Integer replyId, Integer userId);

    /**
     * 6/22
     * @param userId
     * @return
     */
    public ThreadUserSelectedSubjects findUsersSelectedSubjects(Integer userId);

    /**
     * 6/22
     * @param userId
     * @param subjectsId
     */
    public void addUserSelectedSubjects(Integer userId, String subjectsId);

    /**
     * 6/22
     * @param userId
     */
    public void deleteUserSelectedSubjects(Integer userId);

    /**
     * 6/22
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Thread> findByUserFollowingId(Integer userId, Integer pageNum, Integer pageSize);

    /**
     * 6/23
     * @param schoolId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Thread> findBySchoolIdOrderByView(Integer schoolId, Integer pageNum, Integer pageSize);

    /**
     * 6/23
     * @param threadId
     * @param userId
     * @param reason
     */
    public void reportThread(Integer threadId, Integer userId, String reason);

    /**
     * 6/23
     * @param threadId
     * @param replyId
     * @param userId
     * @param reason
     */
    public void reportReplyToThread(Integer threadId, Integer replyId, Integer userId, String reason);

    /**
     * 6/26
     * @param userId
     * @param subjectIds
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Thread> getUserInterestedStudyHelpThreads(Integer userId, String subjectIds, Integer pageNum, Integer pageSize);

    /**
     * 获取用户创建的帖子列表
     * @param userId 用户id
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 用户创建的帖子列表
     */
    List<ThreadStats> getUserThreads(Integer userId, Integer pageNum, Integer pageSize);
}
