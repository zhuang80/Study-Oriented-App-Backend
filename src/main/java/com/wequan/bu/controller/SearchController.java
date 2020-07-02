package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.Condition;
import com.wequan.bu.controller.vo.TutorInquiryVo;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.json.JSON;
import com.wequan.bu.repository.model.*;
import com.wequan.bu.repository.model.extend.TutorRateInfo;
import com.wequan.bu.service.TutorInquiryService;
import com.wequan.bu.service.TutorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * @author ChrisChen
 */
@RestController
@RequestMapping("/search")
@Api(tags = "Search")
public class SearchController {

    private static final Logger log = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private TutorService tutorService;
    @Autowired
    private TutorInquiryService tutorInquiryService;

    @PostMapping("/tutor")
    @ApiOperation(value = "Search tutor with condition", notes = "返回Tutor列表, 根据subject分组，评分排序")
    @ApiModelProperty(value="condition", notes = "筛选条件json串")
    @JSON(type = User.class, include = {"userName", "schoolId", "firstName", "lastName", "avatar", "avatarUrlInProvider"})
    @JSON(type = Subject.class, include = {"id", "name"})
    public Result<Map<String, List<TutorRateInfo>>> searchTutor(@RequestBody Condition condition) {
        final Map<String, List<TutorRateInfo>> result = new HashMap<>();
        if (condition != null && condition.selfCheck()) {
            String whereCondition = condition.getWhereCondition();
            Map<String, Integer> pageCondition = condition.getPageCondition();
            List<TutorRateInfo> tutors = tutorService.search(whereCondition, null, pageCondition);
            //根据subject分组，评分排序
            tutors.stream().sorted(Comparator.comparing(TutorRateInfo::getScore).reversed().thenComparing(Tutor::getId)).forEach(e -> {
                List<Subject> subjectList = e.getSubjectList();
                for (Subject s : subjectList) {
                    String name = s.getName();
                    if (!result.containsKey(name)) {
                        List<TutorRateInfo> tutorRateInfoList = new ArrayList<>();
                        tutorRateInfoList.add(e);
                        result.put(name, tutorRateInfoList);
                    } else {
                        result.get(name).add(e);
                    }
                }
            });
            return  ResultGenerator.success(result);
        } else {
            return ResultGenerator.fail("Invalid parameters");
        }
    }

    @PostMapping("/tutor_inquiry")
    @ApiOperation(value = "Search tutor inquiry with condition", notes = "返回Tutor inquires列表，根据subject分组，按时间倒序")
    @ApiModelProperty(value="condition", notes = "筛选条件json串")
    public Result<Map<String, List<TutorInquiryVo>>> searchTutorInquiry(@RequestBody Condition condition) {
        final Map<String, List<TutorInquiryVo>> result = new HashMap<>();
        if (condition != null && condition.selfCheck()) {
            String whereCondition = condition.getWhereCondition();
            Map<String, Integer> pageCondition = condition.getPageCondition();
            List<TutorInquiryVo> inquiryList = tutorInquiryService.search(whereCondition, null, pageCondition);

            return  ResultGenerator.success(result);
        } else {
            return ResultGenerator.fail("Invalid parameters");
        }
    }

    @PostMapping("/online_event")
    @ApiOperation(value = "Search online event", notes = "返回Online event列表，按临近时间倒序")
    @ApiModelProperty(value="condition", notes = "筛选条件json串")
    @ApiIgnore
    public Result<List<OnlineEvent>> searchOnlineEvent(@RequestBody Condition condition) {
        return null;
    }

    @PostMapping("/discussion_group")
    @ApiOperation(value = "Search discussion group", notes = "返回Discussion group列表，按临近时间倒序")
    @ApiModelProperty(value="condition", notes = "筛选条件json串")
    public Result<List<DiscussionGroup>> searchDiscussionGroup(@RequestBody Condition condition) {
        return null;
    }

    @PostMapping("/professor")
    @ApiOperation(value = "Search professor", notes = "返回Professor列表，按评分倒序")
    @ApiModelProperty(value="condition", notes = "筛选条件json串")
    @ApiIgnore
    public Result<List<Professor>> searchProfessor(@RequestBody Condition condition) {
        return null;
    }

    @PostMapping("/course")
    @ApiOperation(value = "Search course", notes = "返回Course列表")
    @ApiModelProperty(value="condition", notes = "筛选条件json串")
    @ApiIgnore
    public Result<List<Course>> searchCourse(@RequestBody Condition condition) {
        return null;
    }

}
