package com.wequan.bu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.*;
import com.wequan.bu.service.TutorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value = "Search tutor with topics or name", notes = "返回Tutor列表, 根据subject分组，评分排序，分页")
    @ApiModelProperty(value="condition", notes = "筛选条件json串")
    public String searchTutor(@RequestBody Condition condition) {
        if (condition != null && condition.selfCheck()) {
            String whereCondition = condition.getWhereCondition();
            String groupCondition = condition.getGroupCondition();
            String orderCondition = condition.getOrderCondition();
            Map<String, Integer> pageCondition = condition.getPageCondition();
            try {
                new ObjectMapper().writeValueAsString(tutorService.search(whereCondition, groupCondition, orderCondition, pageCondition));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            // to do
        }
        return null;
    }

    @GetMapping("/tutor_inquiry")
    @ApiOperation(value = "Search tutor inquiry with topics", notes = "返回Tutor inquires列表，根据subject分组，按时间倒序")
    @ApiModelProperty(value="condition", notes = "筛选条件json串")
    public List<TutorInquiry> searchTutorInquiry(@RequestBody Condition condition) {

        return null;
    }

    @GetMapping("/online_event")
    @ApiOperation(value = "Search online event", notes = "返回Online event列表，先按临近时间倒序再按scheduled时间倒序")
    @ApiModelProperty(value="condition", notes = "筛选条件json串")
    public List<OnlineEvent> searchOnlineEvent(@RequestBody Condition condition) {
        return null;
    }

    @GetMapping("/discussion_group")
    @ApiOperation(value = "Search discussion group", notes = "返回Discussion group列表，按时间倒序")
    @ApiModelProperty(value="condition", notes = "筛选条件json串")
    public List<DiscussionGroup> searchDiscussionGroup(@RequestBody Condition condition) {
        return null;
    }

}
