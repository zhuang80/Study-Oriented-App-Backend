package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.DegreeMapper;
import com.wequan.bu.repository.model.Degree;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.DegreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Zhaochao Huang
 */
@Service
public class DegreeServiceImpl extends AbstractService<Degree> implements DegreeService {

    @Autowired
    private DegreeMapper degreeMapper;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(degreeMapper);
    }
}
