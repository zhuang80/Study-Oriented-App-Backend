package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.SchoolMapper;
import com.wequan.bu.repository.model.School;
import com.wequan.bu.service.SchoolService;
import com.wequan.bu.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Zhaochao Huang
 */
@Service
public class SchoolServiceImpl extends AbstractService<School> implements SchoolService {
    @Autowired
    private SchoolMapper schoolMapper;

    @PostConstruct
    public void postConstructor() {
        this.setMapper(schoolMapper);
    }

}
