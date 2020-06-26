package com.wequan.bu.service;

import com.wequan.bu.repository.model.OnlineEvent;

import java.util.List;

/**
 * @author ChrisChen
 */
public interface OnlineEventService extends Service<OnlineEvent> {

    /**
     * 获取用户OnlineEvent列表
     * @param userId 用户id
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @param type 类型
     * @return OnlineEvent列表
     */
    List<OnlineEvent> getUserOnlineEvents(Integer userId, Integer pageNum, Integer pageSize, Integer... type);
}
