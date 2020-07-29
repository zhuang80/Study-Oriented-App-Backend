package com.wequan.bu.service;

import com.wequan.bu.repository.model.ThreadStream;

import java.util.List;

/**
 * @author ChrisChen
 */
public interface ThreadStreamService extends Service<ThreadStream> {

    /**
     * 获取用户回帖列表
     * @param userId 用户id
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 用户回复帖子列表
     */
    List<ThreadStream> getUserReplies(Integer userId, Integer pageNum, Integer pageSize);

    /**
     * 获取帖子的直接回复列表
     * @param threadId 帖子id
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 直接回复列表
     */
    List<ThreadStream> getDirectThreadReplies(Integer threadId,Integer pageNum, Integer pageSize);

    /**
     * 获取帖子的间接回复列表
     * @param threadId 帖子id
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 间接回复列表
     */
    List<ThreadStream> getIndirectThreadReplies(Integer threadId,Integer pageNum, Integer pageSize);

    /**
     * 获取回复第一层回帖的列表
     * @param threadId 帖子id
     * @param directReplyId 第一层回帖id
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 回复第一层回帖的列表
     */
    List<ThreadStream> getThreadReplyIndirectReplies(Integer threadId, Integer directReplyId, Integer pageNum, Integer pageSize);

    /**
     * 添加回帖
     * @param threadStream 回帖信息
     * @return 回帖id
     */
    int addThreadReply(ThreadStream threadStream);

    /**
     * 对回帖点赞
     * @param threadId 帖子id
     * @param replyId 回帖id
     * @param userId 用户id
     */
    void likeThreadReply(Integer threadId, Integer replyId, Integer userId);

    /**
     * 对回帖拍砖
     * @param threadId 帖子id
     * @param replyId 回帖id
     * @param userId 用户id
     */
    void dislikeThreadReply(Integer threadId, Integer replyId, Integer userId);

    /**
     * 按标签获取回帖列表
     * @param label 标签
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 帖子列表
     */
    List<ThreadStream> getLabelThreadReplies(Integer label, Integer pageNum, Integer pageSize);

    /**
     * 针对用户已发布帖子的回帖列表
     * @param userId 用户id
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 用户已发布帖子的回帖列表
     */
    List<ThreadStream> getUserThreadReplies(Integer userId, Integer pageNum, Integer pageSize);

}
