package com.wequan.bu.controller;

import com.wequan.bu.repository.model.School;
import com.wequan.bu.service.SchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@RestController
@RequestMapping("/universities")
@Api(tags = "School")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    @GetMapping("/")
    @ApiOperation(value = "findAll", notes = "return a list of schools")
    public List<School> findAll(){
        return schoolService.findAll();
    }
}
