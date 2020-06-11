package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.TutorInquiry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ChrisChen
 */
@RestController
@Api(tags = "Tutor Inquiry")
public class TutorInquiryController {

    private static final Logger log = LoggerFactory.getLogger(TutorInquiryController.class);

    @GetMapping("/tutor_inquiries")
    @ApiOperation(value = "See more tutor inquiry", notes = "返回Tutor inquiry列表")
    public List<TutorInquiry> getTutorInquiries(@RequestParam(value = "subject", required = false) String subject) {
        return null;
    }

    @GetMapping("/tutor_inquiry/{id}")
    @ApiOperation(value = "Tutor inquiry page/detail", notes = "返回Tutor inquiry详情")
    public TutorInquiry getTutorInquiry(@PathVariable("id") int id) {
        return null;
    }

    @PostMapping("/tutor_inquiry")
    @ApiOperation(value = "Make tutor inquiry", notes = "返回添加tutor inquiry信息")
    public String addTutorInquiry(@RequestBody TutorInquiry tutorInquiry) {
        return null;
    }

    @GetMapping("/tutor_inquiries/latest")
    @ApiOperation(value = "Latest tutor inquires", notes = "返回最新tutor inquiries")
    public List<TutorInquiry> getLatestTutorInquiries() {
        return null;
    }
}
