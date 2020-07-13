package com.wequan.bu.service;

import com.wequan.bu.repository.model.TutorSubject;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
public interface TutorSubjectService extends Service<TutorSubject> {
    void insertOrDoNothing(List<TutorSubject> tutorSubjectList);
}
