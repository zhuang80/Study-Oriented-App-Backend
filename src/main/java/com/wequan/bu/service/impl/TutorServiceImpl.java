package com.wequan.bu.service.impl;

import com.github.pagehelper.PageHelper;
import com.wequan.bu.controller.vo.TutorApplicationSubjectTopic;
import com.wequan.bu.repository.dao.OnlineEventMapper;
import com.wequan.bu.repository.dao.TutorMapper;
import com.wequan.bu.repository.dao.TutorViewHistoryMapper;
import com.wequan.bu.repository.model.*;
import com.wequan.bu.repository.model.extend.TutorBriefInfo;
import com.wequan.bu.repository.model.extend.TutorRateInfo;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.TutorApplicationSubjectTopicService;
import com.wequan.bu.service.TutorService;
import com.wequan.bu.service.TutorSubjectService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ChrisChen
 */
@Service
public class TutorServiceImpl extends AbstractService<Tutor> implements TutorService {

    private static final Logger log = LoggerFactory.getLogger(TutorServiceImpl.class);

    @Autowired
    private TutorMapper tutorMapper;
    @Autowired
    private OnlineEventMapper onlineEventMapper;
    @Autowired
    private TutorViewHistoryMapper tutorViewHistoryMapper;

    @Autowired
    private TutorApplicationSubjectTopicService subjectTopicService;

    @Autowired
    private TutorSubjectService tutorSubjectService;

    @PostConstruct
    public void postConstruct() {
        this.setMapper(tutorMapper);
    }

    @Override
    public List<TutorBriefInfo> search(String whereCondition, String orderCondition, Map<String, Integer> pageCondition) {
        List<TutorBriefInfo> tutors = null;
        tutors = tutorMapper.selectByConditions(whereCondition, orderCondition,
                new RowBounds(pageCondition.get("pageNo"), pageCondition.get("pageSize")));
        return tutors;
    }

    @Override
    public List<Tutor> findTutors(Integer subjectId, Integer pageNum, Integer pageSize) {
        if(pageNum == null || pageNum <= 0 ) {
            pageNum = 1;
        }
        if(pageSize == null || pageSize <= 0){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        return tutorMapper.selectTutors(subjectId);
    }

    @Override
    public List<TutorRateInfo> findTopTutors(Integer subjectId, Integer pageNum, Integer pageSize) {
        if(pageNum == null || pageNum <= 0 ) {
            pageNum = 1;
        }
        if(pageSize == null || pageSize <= 0){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        return tutorMapper.selectTopTutors(subjectId);
    }

    @Override
    public List<TutorViewHistory> findViewHistoryByTutorId(Integer tutorId, Integer pageNum, Integer pageSize) {
        if(pageNum == null || pageNum <= 0 ) {
            pageNum = 1;
        }
        if(pageSize == null || pageSize <= 0){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        return tutorViewHistoryMapper.selectByTutorId(tutorId);
    }

    @Override
    public List<OnlineEvent> findOnlineEventByUserId(Integer userId) {
        return onlineEventMapper.selectByUserId(userId);
    }

    @Override
    public void approveTutorApplication(TutorApplication tutorApplication) {
        Tutor tutor = setTutorProfile(tutorApplication);
        tutorMapper.insertSelective(tutor);
        saveTutorSubject(tutor, tutorApplication);
    }

    @Override
    public void updateAvailability(Integer tutorId, Short action) {
        Tutor tutor = new Tutor();
        tutor.setId(tutorId);
        tutor.setTutorAvailable(action == 1 ? true:false);
        tutorMapper.updateByPrimaryKeySelective(tutor);
    }

    @Override
    public void logTutorViewHistory(Tutor tutor, Integer userId) {
        /** check whether the user who view the tutor profile is the owner of the profile
         * only log view history when the user is not the owner of the profile
         */
        if(!(userId == null || tutor.getUser().getId().equals(userId))){
            TutorViewHistory viewHistory = new TutorViewHistory();
            viewHistory.setUserId(userId);
            viewHistory.setTutorId(tutor.getId());
            viewHistory.setViewTime(LocalDateTime.now());
            tutorViewHistoryMapper.insertSelective(viewHistory);
        }
    }

    private Tutor setTutorProfile(TutorApplication tutorApplication){
        Tutor tutor = new Tutor();
        tutor.setUserId(tutorApplication.getUserId());
        tutor.setBriefIntroduction(tutorApplication.getBriefIntroduction());
        tutor.setCreateTime(LocalDateTime.now());
        tutor.setStatus((short) 1);
        tutor.setLatePolicyId(tutorApplication.getLatePolicyId());
        tutor.setCancellationPolicyId(tutorApplication.getCancellationPolicyId());
        tutor.setTutorAvailable(true);
        tutor.setTutorApplicationId(tutorApplication.getId());
        tutor.setCurrentCity(tutorApplication.getCurrentCity());
        tutor.setCurrentState(tutorApplication.getCurrentState());
        tutor.setTeachMethod(tutorApplication.getTeachMethod());
        tutor.setHourlyRate(tutorApplication.getHourlyRate());
        return tutor;
    }

    private void saveTutorSubject(Tutor tutor, TutorApplication tutorApplication){
        List<TutorApplicationSubjectTopic> subjectTopics = subjectTopicService.findByIds(tutorApplication.getSubjectTopicsIds());
        List<TutorSubject> tutorSubjectList = new ArrayList<>();
        for(TutorApplicationSubjectTopic subjectTopic : subjectTopics){
            TutorSubject tutorSubject = new TutorSubject();
            tutorSubject.setTutorId(tutor.getId());
            tutorSubject.setSubjectId(subjectTopic.getSubjectId());
            tutorSubject.setCreateTime(LocalDateTime.now());
            tutorSubjectList.add(tutorSubject);
        }
        tutorSubjectService.save(tutorSubjectList);
    }

}
