package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.repository.model.Department;
import com.wequan.bu.repository.model.School;
import com.wequan.bu.service.SchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@RestController
@RequestMapping("/study_space")
@Api(value = "对学校的操作", tags = "学校相关Rest Api")
public class SchoolController {

    private static final Logger log = LoggerFactory.getLogger(SchoolController.class);

    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private SchoolService schoolService;

    @GetMapping("/universities")
    @ApiOperation(value = "findAll", notes = "return a list of schools")
    public List<School> findAll(Integer pageNum, Integer pageSize){
        return schoolService.findAll(pageNum, pageSize);
    }

    @GetMapping("/departments")
    @ApiOperation(value = "", notes = "return a list of departments by school id")
    public List<Department> findDepartmentsBySchoolId(@RequestParam("schoolId") Integer id){
        if(id < 0){
            log.info(messageHandler.getFailResponseMessage("40008"));
            return null;
        }
        return schoolService.findDepartmentsBySchoolId(id);
    }
}
