package com.wequan.bu.service.impl;

import com.wequan.bu.controller.vo.TutorApplicationSubjectTopic;
import com.wequan.bu.repository.dao.TutorApplicationSubjectTopicMapper;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.TutorApplicationSubjectTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Zhaochao Huang
 */
@Service
public class TutorApplicationSubjectTopicServiceImpl extends AbstractService<TutorApplicationSubjectTopic> implements TutorApplicationSubjectTopicService {
    @Autowired
    private TutorApplicationSubjectTopicMapper tutorApplicationSubjectTopicMapper;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(tutorApplicationSubjectTopicMapper);
    }
}
