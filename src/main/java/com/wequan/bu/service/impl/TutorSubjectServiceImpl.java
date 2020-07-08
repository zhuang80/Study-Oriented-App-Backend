package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.TutorSubjectMapper;
import com.wequan.bu.repository.model.TutorSubject;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.TutorSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Zhaochao Huang
 */
@Service
public class TutorSubjectServiceImpl extends AbstractService<TutorSubject> implements TutorSubjectService {

    @Autowired
    private TutorSubjectMapper tutorSubjectMapper;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(tutorSubjectMapper);
    }
}
