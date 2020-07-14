package com.wequan.bu.service.impl;

import com.wequan.bu.controller.vo.ProfessorVo;
import com.wequan.bu.repository.dao.DepartmentMapper;
import com.wequan.bu.repository.dao.ProfessorMapper;
import com.wequan.bu.repository.dao.UserMapper;
import com.wequan.bu.repository.model.Department;
import com.wequan.bu.repository.model.Professor;
import com.wequan.bu.repository.model.User;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zhaochao Huang
 */
@Service
public class ProfessorServiceImpl extends AbstractService<Professor> implements ProfessorService {

    @Autowired
    private ProfessorMapper professorMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @PostConstruct
    public void postConstruct() { this.setMapper(professorMapper);}

    @Override
    public List<Professor> findAllWithRateByName(Integer limit, String name, Integer pageNum, Integer pageSize) {
        if(pageNum == null || pageNum < 0){
            pageNum = 1;
        }
        if(pageSize == null || pageSize < 0){
            pageSize = 10;
        }
        List<Professor> professorList = professorMapper.selectAllWithRateByName(limit, name);
        professorList = professorList.stream()
                                    .skip((pageNum-1) * pageSize)
                                    .limit(pageSize)
                                    .collect(Collectors.toList());
        return professorList;
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

    @Override
    public List<Professor> findProfessorsByCourseId(Integer id) {
        return professorMapper.selectByCourseId(id);
    }

    @Override
    public void save(ProfessorVo professor)throws Exception{
        User user = userMapper.selectByPrimaryKey(professor.getCreateBy());
        if(user == null) {
            throw new Exception("40010");
        }
        Department department = departmentMapper.selectByPrimaryKey(professor.getDepartmentId());
        if(!department.getSchoolId().equals(professor.getSchoolId())){
            throw new Exception("40010");
        }
        professor.setCreateTime(LocalDateTime.now());
        professorMapper.insert(professor);
    }

    @Override
    public void updateOverallScore(Integer id) {
        professorMapper.updateOverallScoreByPrimaryKey(id);
    }
}
