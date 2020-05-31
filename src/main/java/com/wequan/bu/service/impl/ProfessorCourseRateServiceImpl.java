package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.ProfessorCourseRateMapper;
import com.wequan.bu.repository.model.ProfessorCourseRate;
import com.wequan.bu.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Zhaochao Huang
 */
@Service
public class ProfessorCourseRateServiceImpl extends AbstractService<ProfessorCourseRate> {

    @Autowired
    private ProfessorCourseRateMapper professorCourseRateMapper;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(professorCourseRateMapper);
    }
}
