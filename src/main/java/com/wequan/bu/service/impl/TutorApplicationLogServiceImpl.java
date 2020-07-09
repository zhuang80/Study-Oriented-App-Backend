package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.TutorApplicationLogMapper;
import com.wequan.bu.repository.model.TutorApplication;
import com.wequan.bu.repository.model.TutorApplicationLog;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.TutorApplicationLogService;
import com.wequan.bu.util.TutorApplicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Service
public class TutorApplicationLogServiceImpl extends AbstractService<TutorApplicationLog>
        implements TutorApplicationLogService {
    @Autowired
    private TutorApplicationLogMapper tutorApplicationLogMapper;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(tutorApplicationLogMapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addTutorApplicationLog(TutorApplication application, TutorApplicationStatus status, String comment){
        TutorApplicationLog applicationLog = new TutorApplicationLog();
        applicationLog.setUserId(application.getUserId());
        applicationLog.setAction(status.getValue());
        applicationLog.setActionTime(LocalDateTime.now());
        applicationLog.setActionLog(comment);
        applicationLog.setTutorApplicationId(application.getId());
        applicationLog.setAdminId(-1);
        tutorApplicationLogMapper.insertSelective(applicationLog);
    }

    @Override
    public List<TutorApplicationLog> findByUserId(Integer userId) {
        return tutorApplicationLogMapper.selectByUserId(userId);
    }
}
