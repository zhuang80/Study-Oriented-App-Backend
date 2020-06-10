package com.wequan.bu.service.impl;

import com.wequan.bu.controller.vo.CourseVo;
import com.wequan.bu.controller.vo.ProfessorVo;
import com.wequan.bu.repository.dao.CourseMapper;
import com.wequan.bu.repository.dao.ProfessorMapper;
import com.wequan.bu.repository.model.Course;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Service
public class CourseServiceImpl extends AbstractService<Course> implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ProfessorMapper professorMapper;

    @PostConstruct
    public void postConstruct() { this.setMapper(courseMapper); }

    @Override
    public List<Course> findByNameOrCode(String name, String code) {
        return courseMapper.selectByNameOrCode(name, code);
    }

    @Override
    public Course findByIdAssociatedWithProfessor(Integer id) {
        return courseMapper.selectByIdAssociatedWithProfessor(id);
    }

    @Override
    public void save(CourseVo course) throws Exception{
        ProfessorVo professor = professorMapper.selectBaseInfoById(course.getProfessorId());
        System.out.println("find professor===================================");
        if(!professor.getDepartmentId().equals(course.getDepartmentId())){
            throw new Exception("The provided information is not matched.");
        }
        if(!professor.getSchoolId().equals(course.getSchoolId())){
            throw new Exception("40009");
        }
        System.out.println("+========================do save");
        courseMapper.save(course);
    }
}
