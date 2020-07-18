package com.wequan.bu.service;

import com.wequan.bu.repository.model.OnlineEvent;
import com.wequan.bu.repository.model.OnlineEventTransaction;

import java.time.LocalDateTime;
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

    List<OnlineEvent> findAll(Integer pageNum, Integer pageSize);

    List<OnlineEvent> findAllPublicClasses(Integer pageNum, Integer pageSize);

    List<OnlineEvent> findAllActivities(Integer pageNum, Integer pageSize);

    List<OnlineEvent> findAllBySchoolId(Integer id, Integer pageNum, Integer pageSize);

    void saveOnlineEvent(OnlineEvent onlineEvent) throws Exception;

    void updateName(Integer id, String name);

    void updateDescription(Integer id, String description);

    void updateFee(Integer id, Integer fee);

    void updateMethod(Integer id, String method);

    void updateStartTime(Integer id, LocalDateTime startTime);

    void updateEndTime(Integer id, LocalDateTime endTime);

    void updateVisibility(Integer id, boolean visibility);

    void updateMethodDetail(Integer id, String methodDetail);

    void updateLogo(Integer id, String logo);

    void updateTag(Integer id, Short tagId);

    void updateStatus(Integer id, Short status);

    void addStatusUpdationQuartzJobAndTrigger(OnlineEvent onlineEvent, LocalDateTime time, Short status) throws Exception;

    void addTransferQuartzJobAndTrigger(OnlineEvent onlineEvent, LocalDateTime time) throws Exception;

    void doUserAction(Integer userId, Integer oeId, Short action) throws Exception;

    void saveOnlineEventTransaction(OnlineEventTransaction onlineEventTransaction);
}
