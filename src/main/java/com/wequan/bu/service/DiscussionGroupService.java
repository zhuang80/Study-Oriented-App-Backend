package com.wequan.bu.service;

import com.wequan.bu.repository.model.DiscussionGroup;

import java.util.List;

/**
 * @author ChrisChen
 */
public interface DiscussionGroupService extends Service<DiscussionGroup> {

    /**
     * 根据用户id返回加入的discussion group列表
     * @param userId 用户id
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @return 用户加入的discussion group列表
     */
    List<DiscussionGroup> getUserDiscussionGroups(Integer userId, Integer pageNum, Integer pageSize);

    /**
     * 加入/退出discussion group
     * @param userId 用户id
     * @param dgId discussion group id
     * @param action join/quit
     */
    void doUserAction(Integer userId, Integer dgId, String action);
}
