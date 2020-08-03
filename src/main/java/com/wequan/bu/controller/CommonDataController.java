package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.*;
import com.wequan.bu.service.CommonDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ChrisChen
 */
@RestController
@RequestMapping("/data")
@Api(tags = "Common Data")
public class CommonDataController {

    private static final Logger log = LoggerFactory.getLogger(CommonDataController.class);

    @Autowired
    private CommonDataService commonDataService;

    @GetMapping("/school")
    @ApiOperation(value = "a list of school data", notes = "返回School基本信息")
    public Result<List<School>> getSchoolData() {
        List<School> schoolData = commonDataService.getSchoolData();
        return ResultGenerator.success(schoolData);
    }

    @GetMapping("/tag")
    @ApiOperation(value = "a list of tag data", notes = "返回Tag基本信息")
    public Result<List<Tag>> getTagData() {
        List<Tag> tagData = commonDataService.getTagData();
        return ResultGenerator.success(tagData);
    }

    @GetMapping("/subject")
    @ApiOperation(value = "a list of subject data", notes = "返回Subject基本信息")
    public Result<List<Subject>> getSubjectData() {
        List<Subject> subjectData = commonDataService.getSubjectData();
        return ResultGenerator.success(subjectData);
    }

    @GetMapping("/topic")
    @ApiOperation(value = "a list of topic data", notes = "返回Topic基本信息")
    public Result<List<Topic>> getTopicData() {
        List<Topic> topicData = commonDataService.getTopicData();
        return ResultGenerator.success(topicData);
    }

    @GetMapping("/degree")
    @ApiOperation(value = "a list of degree data", notes = "返回degree基本信息")
    public Result<List<Degree>> getDegreeData(){
        List<Degree> degreeData = commonDataService.getDegreeData();
        return ResultGenerator.success(degreeData);
    }

    @GetMapping("/cancellation_policy")
    @ApiOperation(value = "a list of cancellation policy", notes = "返回cancellation policy")
    public Result<List<CancellationPolicy>> getCancellationPolicy(){
        List<CancellationPolicy> data = commonDataService.getCancellationPolicyData();
        return ResultGenerator.success(data);
    }

    @GetMapping("/late_policy")
    @ApiOperation(value = "a list of late policy", notes = "返回late policy")
    public Result<List<LatePolicy>> getLatePolicy(){
        List<LatePolicy> data = commonDataService.getLatePolicyData();
        return ResultGenerator.success(data);
    }



}
