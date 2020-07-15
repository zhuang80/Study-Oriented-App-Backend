package com.wequan.bu.service.impl;

import com.github.pagehelper.PageHelper;
import com.wequan.bu.repository.dao.ProfessorCourseRateMapper;
import com.wequan.bu.repository.model.ProfessorCourseRate;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.ProfessorCourseRateService;
import com.wequan.bu.service.ProfessorService;
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

    @Autowired
    private ProfessorService professorService;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(professorCourseRateMapper);
    }

    /**
     *get a list of certain professor reviews for a certain course
     * @param p_id the professor id
     * @param c_id the course id
     * @param pageNum the number of page
     * @param pageSize the size of each page
     * @return a list of reviews
     */
    @Override
    public List<ProfessorCourseRate> findAllByProfessorIdAndCourseId(Integer p_id, Integer c_id, Integer pageNum, Integer pageSize) {
        if(pageNum == null || pageNum < 0) {
            pageNum = 1;
        }
        if(pageSize == null || pageSize < 0){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        return professorCourseRateMapper.selectAllByProfessorIdAndCourseId(p_id, c_id);
    }

    @Override
    public void save(ProfessorCourseRate professorCourseRate){
        professorCourseRateMapper.insertSelective(professorCourseRate);
        professorService.updateOverallScore(professorCourseRate.getProfessorId());
    }
}
