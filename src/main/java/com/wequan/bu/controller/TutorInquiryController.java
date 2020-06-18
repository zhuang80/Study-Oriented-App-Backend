package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.TutorInquiryVo;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.TutorInquiry;
import com.wequan.bu.service.TutorInquiryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ChrisChen
 */
@RestController
@Api(tags = "Tutor Inquiry")
public class TutorInquiryController {

    private static final Logger log = LoggerFactory.getLogger(TutorInquiryController.class);

    @Autowired
    private TutorInquiryService tutorInquiryService;

    @Autowired
    private MessageHandler messageHandler;

    @GetMapping("/tutor_inquiries")
    @ApiOperation(value = "a list of tutor inquiry", notes = "返回Tutor inquiry列表")
    public Result<List<TutorInquiry>> getTutorInquiries(@RequestParam(value = "subjectId", required = false) Integer subjectId,
                                                        @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<TutorInquiry> tutorInquiryList = tutorInquiryService.findBySubject(subjectId);
        return ResultGenerator.success(tutorInquiryList);
    }

    @GetMapping("/tutor_inquiry/{id}")
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
    @ApiOperation(value = "Make tutor inquiry", notes = "返回添加tutor inquiry成功与否")
    public Result addTutorInquiry(@RequestBody TutorInquiryVo tutorInquiry) {
        tutorInquiryService.save(tutorInquiry);
        return ResultGenerator.success();
    }

}
