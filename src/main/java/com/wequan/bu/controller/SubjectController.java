package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zhaochao Huang
 */
@RestController
@Api(value="", tags="Subject Rest API")
@RequestMapping("/study_space")
public class SubjectController {

    @Autowired
    private MessageHandler messageHandler;

    @GetMapping("/subjects")
    @ApiOperation(value = "", notes="find a list of subjects by school id")
    public void findBySchoolId(@RequestParam("schoolId") Integer school_id){
        if(school_id < 0) {
            messageHandler.getFailResponseMessage("40008");
            return;
        }
    }

    @GetMapping("/subject/restore")
    @ApiOperation(value = "", notes="restore user's last selected subject")
    public void restoreSubject(@RequestParam("userId") Integer user_id){
        if(user_id < 0) {
            messageHandler.getFailResponseMessage("40008");
            return;
        }
    }
}
