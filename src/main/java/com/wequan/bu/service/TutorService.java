package com.wequan.bu.service;

import com.wequan.bu.controller.vo.OnlineEvent;
import com.wequan.bu.repository.model.Tutor;
import com.wequan.bu.repository.model.TutorViewHistory;
import com.wequan.bu.repository.model.extend.TutorRateInfo;

import java.util.List;
import java.util.Map;

/**
 * @author ChrisChen
 */
public interface TutorService extends Service<Tutor> {

    /**
     * 按搜索条件搜索tutor
     * @param whereCondition where
     * @param groupCondition group by
     * @param orderCondition order by
     * @param pageCondition page
     * @return Tutor列表
     */
    List<Tutor> search(String whereCondition, String groupCondition, String orderCondition, Map<String, Integer> pageCondition);

    public List<Tutor> findTutors(Integer subjectId, Integer pageNum, Integer pageSize);

    public List<TutorRateInfo> findTopTutors(Integer subjectId, Integer pageNum, Integer pageSize);

    public List<TutorViewHistory> findViewHistoryByTutorId(Integer tutorId, Integer pageNum, Integer pageSize);

    public List<OnlineEvent> findOnlineEventByUserId(Integer userId);
}
