package com.wequan.bu.service;

import com.wequan.bu.repository.model.Course;
import com.wequan.bu.repository.model.Tutor;

import java.util.List;

/**
 * @author ChrisChen
 */
public interface TutorService extends Service<Tutor> {
    public List<Tutor> findTutors(Integer subjectId);
}
