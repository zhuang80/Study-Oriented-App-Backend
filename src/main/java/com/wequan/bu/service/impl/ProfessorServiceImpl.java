package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.ProfessorMapper;
import com.wequan.bu.repository.model.Professor;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Zhaochao Huang
 */
@Service
public class ProfessorServiceImpl extends AbstractService<Professor> implements ProfessorService {

    @Autowired
    private ProfessorMapper professorMapper;

    @PostConstruct
    public void postConstruct() { this.setMapper(professorMapper);}
}
