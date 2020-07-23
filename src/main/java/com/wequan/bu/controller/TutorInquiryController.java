package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.TutorInquiryGroup;
import com.wequan.bu.controller.vo.TutorInquiryVo;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.json.JSON;
import com.wequan.bu.repository.model.Subject;
import com.wequan.bu.repository.model.Topic;
import com.wequan.bu.repository.model.TutorInquiry;
import com.wequan.bu.repository.model.User;
import com.wequan.bu.service.TutorInquiryService;
import com.wequan.bu.util.TutorInquiryTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ChrisChen
 */
@Controller
@Api(tags = "Tutor Inquiry")
public class TutorInquiryController {

    private static final Logger log = LoggerFactory.getLogger(TutorInquiryController.class);

    @Autowired
    private TutorInquiryService tutorInquiryService;

    @Autowired
    private MessageHandler messageHandler;

    @GetMapping("/tutor_inquiries")
    @JSON(type = User.class, include = {"id", "userName", "firstName", "lastName", "schoolId", "avatarUrl", "avatarUrlInProvider"})
    @JSON(type = TutorInquiry.class, filter = "subject")
    @JSON(type = Subject.class, filter = {"createBy","createTime"})
    @JSON(type = Topic.class, include = {"id", "name", "subjectId"})
    @ApiOperation(value = "a list of tutor inquiry", notes = "返回Tutor inquiry列表")
    public Result<List<TutorInquiryGroup>> getTutorInquiries(@RequestParam(value = "subjectId", required = false) Integer subjectId,
                                                             @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if(subjectId != null && subjectId < 0 ){
            String message = messageHandler.getFailResponseMessage("40008");
            return ResultGenerator.fail(message);
        }
        List<TutorInquiry> tutorInquiryList = tutorInquiryService.findBySubject(subjectId, pageNum, pageSize);
        List<TutorInquiryGroup> tutorInquiryGroupList = TutorInquiryTool.groupTutorInquiryBySubject(tutorInquiryList);
        return ResultGenerator.success(tutorInquiryGroupList);
    }

    @GetMapping("/tutor_inquiry/{id}")
    @JSON(type = User.class, include = {"id", "userName", "firstName", "lastName", "schoolId", "avatarUrl", "avatarUrlInProvider"})
    @JSON(type = Subject.class, include = {"id", "name"})
    @JSON(type = Topic.class, include = {"id", "name", "subjectId"})
    @ApiOperation(value = "Tutor inquiry detail", notes = "返回Tutor inquiry详情")
    public Result<TutorInquiry> getTutorInquiry(@PathVariable("id") Integer id) {
        if(id < 0){
            String message = messageHandler.getFailResponseMessage("40008");
            return ResultGenerator.fail(message);
        }
        TutorInquiry tutorInquiry = tutorInquiryService.findById(id);
        return ResultGenerator.success(tutorInquiry);
    }

    @PostMapping("/tutor_inquiry")
    @ResponseBody
    @ApiOperation(value = "Make tutor inquiry", notes = "返回添加tutor inquiry成功与否")
    public Result addTutorInquiry(@RequestBody TutorInquiryVo tutorInquiry) {
        tutorInquiryService.save(tutorInquiry);
        return ResultGenerator.success();
    }
}
