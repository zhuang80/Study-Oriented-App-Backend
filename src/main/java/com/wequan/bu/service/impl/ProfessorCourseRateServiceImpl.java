package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.ProfessorCourseRateMapper;
import com.wequan.bu.repository.model.ProfessorCourseRate;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.ProfessorCourseRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Service
public class ProfessorCourseRateServiceImpl extends AbstractService<ProfessorCourseRate> implements ProfessorCourseRateService {

    @Autowired
    private ProfessorCourseRateMapper professorCourseRateMapper;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(professorCourseRateMapper);
    }

    @Override
    public List<ProfessorCourseRate> findAllByProfessorIdAndCourseId(Integer p_id, Integer c_id) {
        return professorCourseRateMapper.selectAllByProfessorIdAndCourseId(p_id, c_id);
    }
}
