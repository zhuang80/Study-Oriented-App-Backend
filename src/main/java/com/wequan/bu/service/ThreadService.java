package com.wequan.bu.service;

import com.wequan.bu.repository.model.Thread;
import com.wequan.bu.repository.model.ThreadStream;
import com.wequan.bu.repository.model.ThreadUserSelectedSubjects;

import java.util.List;


public interface ThreadService extends Service<Thread> {

    public Thread findByPrimaryKey(Integer id);

    public int deleteByPrimaryKey(Integer id);

    public int insert(Thread record);

    /**
     * 6/20
     * @param threadStream
     * @return
     */
    public int insertReply(ThreadStream threadStream);

    /**
     *
     * @param thread
     * @return
     */
    public Integer numberOfLikesOfThread(Thread thread);

    public int insertSelective(Thread record);

    public void updateByIdSelective(Thread record);

    public void updateByKey(Thread record);

    /**
     * 6/20
     * @param threadStream
     * @return
     */
    public int replyToThread(ThreadStream threadStream);
    /**
     * 6/19
     */
    public List<Thread> findBySchoolAndTag(Integer schoolId, Integer tagId);

    /**
     * 6/20
     * @param threadId
     * @return replies
     */
    public List<ThreadStream> getThreadReplies(Integer threadId);

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
     *
     * @param threadId
     * @param replyId
     * @param userId
     */
    public void likeReplyOfThread(Integer threadId, Integer replyId, Integer userId);

    /**
     *
     * @param threadId
     * @param replyId
     * @param userId
     */
    public void dislikeReplyOfThread(Integer threadId, Integer replyId, Integer userId);

    /**
     * 6/20
     * @param userId
     * @return
     */
    public ThreadUserSelectedSubjects findUsersSubjects(Integer userId);
}
