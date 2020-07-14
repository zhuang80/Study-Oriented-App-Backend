package com.wequan.bu.service;

import com.wequan.bu.repository.model.DiscussionGroup;

import java.util.List;
import java.util.Map;

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
    void doUserAction(Integer userId, Integer dgId, Short action);

    /**
     * 搜索discussion group列表
     * @param whereCondition where
     * @param orderCondition order by
     * @param pageCondition 分页
     * @return discussion group列表
     */
    List<DiscussionGroup> search(String whereCondition, String orderCondition, Map<String, Integer> pageCondition);

    List<DiscussionGroup> findAll(Integer pageNum, Integer pageSize);

    List<DiscussionGroup> findBySchoolId(Integer schoolId, Integer pageNum, Integer pageSize);

    List<Integer> findMemberIdsByDiscussionGroupId(Integer id);

    void updateAvailability(Integer id, Short action) throws Exception;

    void updateName(Integer id, String name) throws Exception;

    void updateVisibility(Integer id, boolean visibility) throws Exception;

    void updateBriefDescription(Integer id, String briefDescription) throws Exception;

    void updateGroupMessage(Integer id, String groupMessage) throws Exception;

    void updateTag(Integer id, Short tagId) throws Exception;

    void updateLogo(Integer id, String logoUrl) throws Exception;
}
