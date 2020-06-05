package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.ProfessorMapper;
import com.wequan.bu.repository.model.Professor;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Service
public class ProfessorServiceImpl extends AbstractService<Professor> implements ProfessorService {

    @Autowired
    private ProfessorMapper professorMapper;

    @PostConstruct
    public void postConstruct() { this.setMapper(professorMapper);}

    @Override
    public List<Professor> findAllWithRateByName(Integer limit, String name) {
        return professorMapper.selectAllWithRateByName(limit, name);
    }

    @Override
    public Boolean checkCourseForProfessor(Integer id, Integer c_id) {
        List<Integer> courseIds = professorMapper.getCourseIds(id);
        for(Integer courseId : courseIds){
            //System.out.println(courseId);
            if(courseId.equals(c_id)){
                return true;
            }
        }
        return false;
    }
}
