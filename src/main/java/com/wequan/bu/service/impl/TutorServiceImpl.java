package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.TutorMapper;
import com.wequan.bu.repository.model.Tutor;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.TutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author ChrisChen
 */
@Service
public class TutorServiceImpl extends AbstractService<Tutor> implements TutorService {

    private static final Logger logger = LoggerFactory.getLogger(TutorServiceImpl.class);

    @Autowired
    private TutorMapper tutorMapper;

    @PostConstruct
    public void postConstruct() {
        this.setMapper(tutorMapper);
    }

}
