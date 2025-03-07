package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.Condition;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.Thread;
import com.wequan.bu.repository.model.*;
import com.wequan.bu.repository.model.extend.SubjectBriefInfo;
import com.wequan.bu.repository.model.extend.TutorBriefInfo;
import com.wequan.bu.repository.model.extend.TutorInquiryBriefInfo;
import com.wequan.bu.service.DiscussionGroupService;
import com.wequan.bu.service.ThreadService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private DiscussionGroupService discussionGroupService;
    @Autowired
    private ThreadService threadService;

    @PostMapping("/tutor")
    @ApiOperation(value = "Search tutor with condition", notes = "返回Tutor列表, 根据subject分组，评分排序")
    @ApiModelProperty(value="condition", notes = "筛选条件json串")
    public Result<Map<String, List<TutorBriefInfo>>> searchTutor(@RequestBody Condition condition) {
        final Map<String, List<TutorBriefInfo>> result = new HashMap<>();
        if (condition != null && condition.selfCheck()) {
            String whereCondition = condition.getWhereCondition();
            String orderCondition = condition.getOrderCondition();
            Map<String, Integer> pageCondition = condition.getPageCondition();
            List<TutorBriefInfo> tutors = tutorService.search(whereCondition, orderCondition, pageCondition);
            //根据subject分组，评分排序
            tutors.forEach(e -> {
                List<SubjectBriefInfo> subjectList = e.getSubjectList();
                for (SubjectBriefInfo s : subjectList) {
                    String name = s.getName();
                    if (!result.containsKey(name)) {
                        List<TutorBriefInfo> tutorBriefInfoList = new ArrayList<>();
                        tutorBriefInfoList.add(e);
                        result.put(name, tutorBriefInfoList);
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
    public Result<Map<String, List<TutorInquiryBriefInfo>>> searchTutorInquiry(@RequestBody Condition condition) {
        final Map<String, List<TutorInquiryBriefInfo>> result = new HashMap<>();
        if (condition != null && condition.selfCheck()) {
            String whereCondition = condition.getWhereCondition();
            String orderCondition = condition.getOrderCondition();
            Map<String, Integer> pageCondition = condition.getPageCondition();
            List<TutorInquiryBriefInfo> inquiryList = tutorInquiryService.search(whereCondition, orderCondition, pageCondition);
            //根据subject分组，按时间倒序
            inquiryList.forEach(e -> {
                SubjectBriefInfo subject = e.getSubject();
                String name = subject.getName();
                if (!result.containsKey(name)) {
                    List<TutorInquiryBriefInfo> tutorInquiryList = new ArrayList<>();
                    tutorInquiryList.add(e);
                    result.put(name, tutorInquiryList);
                } else {
                    result.get(name).add(e);
                }
            });
            return ResultGenerator.success(result);
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
    @ApiOperation(value = "Search discussion group", notes = "返回Discussion group列表，按创建时间倒序")
    @ApiModelProperty(value="condition", notes = "筛选条件json串")
    public Result<List<DiscussionGroup>> searchDiscussionGroup(@RequestBody Condition condition) {
        List<DiscussionGroup> result = null;
        if (condition != null && condition.selfCheck()) {
            String whereCondition = condition.getWhereCondition();
            String orderCondition = condition.getOrderCondition();
            Map<String, Integer> pageCondition = condition.getPageCondition();
            result = discussionGroupService.search(whereCondition, orderCondition, pageCondition);
            return ResultGenerator.success(result);
        } else {
            return ResultGenerator.fail("Invalid parameters");
        }
    }

    @PostMapping("/thread")
    @ApiOperation(value = "Search thread", notes = "返回thread列表，按点赞数、创建时间倒序")
    @ApiModelProperty(value="condition", notes = "筛选条件json串")
    public Result<List<Thread>> searchThread(@RequestBody Condition condition) {
        List<Thread> result = null;
        if (condition != null && condition.selfCheck()) {
            String whereCondition = condition.getWhereCondition();
            String orderCondition = condition.getOrderCondition();
            Map<String, Integer> pageCondition = condition.getPageCondition();
            result = threadService.search(whereCondition, orderCondition, pageCondition);
            return ResultGenerator.success(result);
        } else {
            return ResultGenerator.fail("Invalid parameters");
        }
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
