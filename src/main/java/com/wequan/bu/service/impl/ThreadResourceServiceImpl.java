package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.ThreadResourceMapper;
import com.wequan.bu.repository.model.ThreadResource;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.ThreadResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author ChrisChen
 */
@Service
public class ThreadResourceServiceImpl extends AbstractService<ThreadResource> implements ThreadResourceService {

    private static final Logger log = LoggerFactory.getLogger(ThreadResourceServiceImpl.class);

    @Autowired
    private ThreadResourceMapper threadResourceMapper;

    @PostConstruct
    public void postConstruct() {
        this.setMapper(threadResourceMapper);
    }



}
