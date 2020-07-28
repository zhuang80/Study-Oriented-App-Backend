package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.ThreadStream;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author ChrisChen
 */
@Mapper
public interface ThreadStreamMapper extends GeneralMapper<ThreadStream> {

    /**
     * 获取用户回帖列表
     * @param userId 用户id
     * @param rowBounds 分页
     * @return 用户回复帖子列表
     */
    List<ThreadStream> selectByUserId(@Param("userId") Integer userId, RowBounds rowBounds);

    /**
     * 获取帖子的直接回复列表
     * @param threadId 帖子id
     * @param rowBounds 分页
     * @return 直接回复列表
     */
    List<ThreadStream> selectDirectRepliesById(Integer threadId, RowBounds rowBounds);

    /**
     * 获取帖子的间接回复列表
     * @param threadId 帖子id
     * @param rowBounds 分页
     * @return 间接回复列表
     */
    List<ThreadStream> selectIndirectRepliesById(Integer threadId, RowBounds rowBounds);

    /**
     * 获取回复第一层回帖的列表
     * @param threadId 帖子id
     * @param directReplyId 第一层回帖id
     * @param rowBounds 分页
     * @return 回复第一层回帖的列表
     */
    List<ThreadStream> selectIndirectRepliesForDirectReply(@Param("threadId") Integer threadId, @Param("directReplyId") Integer directReplyId, RowBounds rowBounds);

    /**
     * 对回帖点赞（只增加点赞数）
     * @param threadId 帖子id
     * @param replyId 回帖id
     * @param userId 用户id
     */
    void likeThreadReply(@Param("threadId") Integer threadId, @Param("replyId") Integer replyId, @Param("userId") Integer userId);

    /**
     * 对回帖拍砖（只增加拍砖数）
     * @param threadId 帖子id
     * @param replyId 回帖id
     * @param userId 用户id
     */
    void dislikeThreadReply(@Param("threadId") Integer threadId, @Param("replyId") Integer replyId, @Param("userId") Integer userId);

    /**
     * 按标签获取回帖列表
     * @param label 标签
     * @param rowBounds 分页
     * @return 回帖列表
     */
    List<ThreadStream> selectLabelThreadReply(@Param("label") Integer label, RowBounds rowBounds);

    /**
     * 针对用户已发布帖子的回帖列表
     * @param userId 用户id
     * @param rowBounds 分页
     * @return 回帖列表
     */
    List<ThreadStream> selectThreadReplyByUserId(@Param("userId") Integer userId, RowBounds rowBounds);

    /**
     * 获取回帖创建用户id
     * @param replyId 回帖id
     * @return 回帖创建用户id
     */
    Integer selectCreateByById(Integer replyId);
}
