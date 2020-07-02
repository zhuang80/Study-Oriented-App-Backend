package com.wequan.bu.service;

import com.wequan.bu.repository.model.ThreadStream;

import java.util.List;

/**
 * @author ChrisChen
 */
public interface ThreadStreamService extends Service<ThreadStream> {

    /**
     * 获取用户回复帖子列表
     * @param userId 用户id
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 用户回复帖子列表
     */
    List<ThreadStream> getUserThreadReplies(Integer userId, Integer pageNum, Integer pageSize);
}
