package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.Condition;
import com.wequan.bu.controller.vo.DiscussionGroup;
import com.wequan.bu.controller.vo.OnlineEvent;
import com.wequan.bu.controller.vo.TutorInquiryVo;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.Course;
import com.wequan.bu.repository.model.Professor;
import com.wequan.bu.repository.model.Tutor;
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

    @PostMapping("/tutor")
    @ApiOperation(value = "Search tutor with condition", notes = "返回Tutor列表, 根据subject分组，评分排序")
    @ApiModelProperty(value="condition", notes = "筛选条件json串")
    public Result<List<Tutor>> searchTutor(@RequestBody Condition condition) {
        Result<List<Tutor>> result = null;
        if (condition != null && condition.selfCheck()) {
            String whereCondition = condition.getWhereCondition();
            String groupCondition = condition.getGroupCondition();
            String orderCondition = condition.getOrderCondition();
            Map<String, Integer> pageCondition = condition.getPageCondition();
            List<Tutor> tutors = tutorService.search(whereCondition, groupCondition, orderCondition, pageCondition);
            result = ResultGenerator.success(tutors);
        } else {
            ResultGenerator.fail("Invalid parameters");
        }
        return result;
    }

    @PostMapping("/tutor_inquiry")
    @ApiOperation(value = "Search tutor inquiry with condition", notes = "返回Tutor inquires列表，根据subject分组，按时间倒序")
    @ApiModelProperty(value="condition", notes = "筛选条件json串")
    public Result<List<TutorInquiryVo>> searchTutorInquiry(@RequestBody Condition condition) {

        return null;
    }

    @PostMapping("/online_event")
    @ApiOperation(value = "Search online event", notes = "返回Online event列表，按临近时间倒序")
    @ApiModelProperty(value="condition", notes = "筛选条件json串")
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
    public Result<List<Professor>> searchProfessor(@RequestBody Condition condition) {
        return null;
    }

    @PostMapping("/course")
    @ApiOperation(value = "Search course", notes = "返回Course列表")
    @ApiModelProperty(value="condition", notes = "筛选条件json串")
    public Result<List<Course>> searchCourse(@RequestBody Condition condition) {
        return null;
    }

}
