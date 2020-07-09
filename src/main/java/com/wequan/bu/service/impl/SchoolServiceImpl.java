package com.wequan.bu.service.impl;

import com.github.pagehelper.PageHelper;
import com.wequan.bu.repository.dao.DepartmentMapper;
import com.wequan.bu.repository.dao.SchoolMapper;
import com.wequan.bu.repository.model.Department;
import com.wequan.bu.repository.model.School;
import com.wequan.bu.service.SchoolService;
import com.wequan.bu.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Service
public class SchoolServiceImpl extends AbstractService<School> implements SchoolService {
    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @PostConstruct
    public void postConstructor() {
        this.setMapper(schoolMapper);
    }

    /**
     * return a specified page of schools
     * @param pageNum the number of page, the default value is 1
     * @param pageSize the size of each page, the default value is 10
     * @return
     */
    @Override
    public List<School> findAll(Integer pageNum, Integer pageSize){
        if(pageNum == null || pageNum < 0 ) {
            pageNum = 1;
        }
        if(pageSize == null || pageSize < 0){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        return schoolMapper.selectAll();
    }

    @Override
    public List<Department> findDepartmentsBySchoolId(Integer id) {
        return departmentMapper.selectDepartmentsBySchoolId(id);
    }
}
