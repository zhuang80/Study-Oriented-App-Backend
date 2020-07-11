package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.School;
import com.wequan.bu.repository.model.Subject;
import com.wequan.bu.repository.model.Tag;
import com.wequan.bu.repository.model.Topic;
import com.wequan.bu.service.CommonDataService;
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
public class CommonDataController {

    private static final Logger log = LoggerFactory.getLogger(CommonDataController.class);

    @Autowired
    private CommonDataService commonDataService;

    @GetMapping("/school")
    public Result<List<School>> getSchoolData() {
        List<School> schoolData = commonDataService.getSchoolData();
        return ResultGenerator.success(schoolData);
    }

    @GetMapping("/tag")
    public Result<List<Tag>> getTagData() {
        List<Tag> tagData = commonDataService.getTagData();
        return ResultGenerator.success(tagData);
    }

    @GetMapping("/subject")
    public Result<List<Subject>> getSubjectData() {
        List<Subject> subjectData = commonDataService.getSubjectData();
        return ResultGenerator.success(subjectData);
    }

    @GetMapping("/topic")
    public Result<List<Topic>> getTopicData() {
        List<Topic> topicData = commonDataService.getTopicData();
        return ResultGenerator.success(topicData);
    }


}
