package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.CourseSubject;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.Department;
import com.wequan.bu.service.SchoolService;
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
@RequestMapping("/study_space")
@Api(tags = "Basic Study Space")
public class StudySpaceController {

    private static final Logger log = LoggerFactory.getLogger(StudySpaceController.class);

    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private SchoolService schoolService;

    @GetMapping("/subjects")
    @ApiOperation(value = "a list of subjects", notes = "根据school id返回学科列表")
    public Result<List<CourseSubject>> getSubjectsBySchool(@RequestParam("schoolId") Integer schoolId) {
        Result<List<CourseSubject>> result = null;

        return result;
    }

    @PutMapping("/subject")
    @ApiOperation(value = "change subject", notes = "用于保存当前用户选择的subject，以便页面恢复上次浏览状态")
    public Result changeSubject(@RequestParam("schoolId") Integer schoolId,
                                @RequestParam("subjectId") Integer subjectId,
                                @RequestParam("userId") String userId) {
        Result result = ResultGenerator.success();

        return result;
    }

    @GetMapping("/subject")
    @ApiOperation(value = "get user's selected subject", notes = "获取用户上次选择的subject")
    public Result<Integer> getSubject(@RequestParam("userId") String userId) {
        Result<Integer> result = null;

        return result;
    }

    @GetMapping("/departments")
    @ApiOperation(value = "a list of departments", notes = "根据school id获取departments")
    public Result<List<Department>> getDepartments(@RequestParam("schoolId") Integer schoolId) {
        List<Department> result = null;
        if(schoolId < 0){
            log.info(messageHandler.getFailResponseMessage("40008"));
            return null;
        }
        result = schoolService.findDepartmentsBySchoolId(schoolId);
        return ResultGenerator.success(result);
    }
}
