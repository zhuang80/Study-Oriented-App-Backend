package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.CourseMapper;
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

    @PostConstruct
    public void postConstruct() { this.setMapper(courseMapper); }

    @Override
    public List<Course> findByNameOrCode(String name, String code) {
        return courseMapper.selectByNameOrCode(name, code);
    }
}
