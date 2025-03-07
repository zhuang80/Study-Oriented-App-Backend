package com.wequan.bu.service;

import com.wequan.bu.repository.model.*;
import com.wequan.bu.repository.model.extend.TutorBriefInfo;
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
     * @param orderCondition order by
     * @param pageCondition page
     * @return Tutor列表
     */
    List<TutorBriefInfo> search(String whereCondition, String orderCondition, Map<String, Integer> pageCondition);

    public List<Tutor> findTutors(Integer subjectId, Integer pageNum, Integer pageSize);

    public List<TutorRateInfo> findTopTutors(Integer subjectId, Integer pageNum, Integer pageSize);

    public List<TutorViewHistory> findViewHistoryByTutorId(Integer tutorId, Integer pageNum, Integer pageSize);

    public List<OnlineEvent> findOnlineEventByUserId(Integer userId);

    public void approveTutorApplication(TutorApplication tutorApplication);

    public void updateAvailability(Integer tutorId, Short action);

    public void logTutorViewHistory(Tutor tutor, Integer userId);

    public Tutor findByUserId(Integer userId);

    public TutorStripe findStripeAccountByTutorId(Integer tutorId);

}
