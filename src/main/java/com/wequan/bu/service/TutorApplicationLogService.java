package com.wequan.bu.service;

import com.wequan.bu.repository.model.TutorApplication;
import com.wequan.bu.repository.model.TutorApplicationLog;
import com.wequan.bu.util.TutorApplicationStatus;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
public interface TutorApplicationLogService extends Service<TutorApplicationLog> {
    public void addTutorApplicationLog(TutorApplication application, TutorApplicationStatus status, String comment);

    public List<TutorApplicationLog> findByUserId(Integer userId);
}
