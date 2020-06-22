package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.Appointment;
import com.wequan.bu.controller.vo.OnlineEvent;
import com.wequan.bu.controller.vo.TutorReview;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.Tutor;
import com.wequan.bu.repository.model.extend.TutorRateInfo;
import com.wequan.bu.repository.model.TutorViewHistory;
import com.wequan.bu.service.TutorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
@Api(tags = "Tutor")
public class TutorController {

    private static final Logger log = LoggerFactory.getLogger(TutorController.class);

    @Autowired
    private TutorService tutorService;
    @Autowired
    private MessageHandler messageHandler;


    @GetMapping("/tutor/{id}")
    @ApiOperation(value = "Get tutor info", notes = "返回Tutor详情")
    public Result<Tutor> getTutor(@PathVariable("id") Integer tutorId) {
        if( tutorId < 0 ){
            String message = messageHandler.getFailResponseMessage("40008");
            return ResultGenerator.fail(message);
        }
        Tutor tutor = tutorService.findById(tutorId);
        return ResultGenerator.success(tutor);
    }

    @PutMapping("/tutor/{id}")
    @ApiOperation(value = "modify tutor basic info", notes = "修改tutor基本信息")
    public Result modifyTutorBasicInfo(@RequestBody Tutor tutor, @PathVariable Integer id) {
        tutor.setId(id);
        tutorService.update(tutor);
        return ResultGenerator.success();
    }

    @GetMapping("/tutors")
    @ApiOperation(value = "Available tutors", notes = "返回Tutor列表，按评分/加入时间倒序")
    public Result<List<Tutor>> getTutors(@RequestParam(value = "subjectId", required = false) Integer subjectId,
                                         @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Tutor> tutors = tutorService.findTutors(subjectId, pageNum, pageSize);
        return ResultGenerator.success(tutors);
    }

    @GetMapping("/tutors/popular")
    @ApiOperation(value = "Popular tutors", notes = "返回Tutor列表，按评分和被查看次数排序")
    public Result<List<TutorRateInfo>> getPopularTutors(@RequestParam(value = "subjectId", required = false) Integer subjectId,
                                                @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if(subjectId != null && subjectId < 0 ){
            String message = messageHandler.getFailResponseMessage("40008");
            return ResultGenerator.fail(message);
        }
        List<TutorRateInfo> result = tutorService.findTopTutors(subjectId, pageNum, pageSize);
        return ResultGenerator.success(result);
    }

    @PostMapping("/tutor/{id}/review")
    @ApiOperation(value = "Review tutor", notes = "返回评价tutor成功与否")
    public Result reviewTutor(@PathVariable("id") Integer tutorId,
                              @RequestBody TutorReview tutorReview) {

        return ResultGenerator.success();
    }

    @GetMapping("/tutor/{id}/appointments")
    @ApiOperation(value = "a list of tutor’s appointment", notes = "返回Tutor与用户的appointment列表")
    public Result<List<Appointment>> getAppointments(@PathVariable("id") Integer tutorId) {
        List<Appointment> result = null;
        return ResultGenerator.success(result);
    }

    @GetMapping("/tutor/{id}/public_class")
    @ApiOperation(value = "a list of tutor’s public class", notes = "返回Tutor创建的public class列表")
    public Result<List<OnlineEvent>> getOnlineEvents(@PathVariable("id") Integer userId) {
        List<OnlineEvent> result = tutorService.findOnlineEventByUserId(userId);
        return ResultGenerator.success(result);
    }

    @GetMapping("/tutor/{id}/incoming_events")
    @ApiOperation(value = "incoming events for tutor", notes = "返回Tutor邻近的事件")
    public Result<Map<String, List<Object>>> getIncomingEvents(@PathVariable("id") Integer tutorId) {
        Map<String, List<Object>> result = null;
        return ResultGenerator.success(result);
    }

    @PutMapping("/tutor/{id}/availability")
    @ApiOperation(value = "tutor change availability", notes = "返回Tutor改变能否辅导成功与否")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "action", value = "1 -> open; 0 -> close")
    })
    public Result changeAvailability(@PathVariable("id") Integer tutorId,
                                     @RequestParam("action") Integer action) {

        return ResultGenerator.success();
    }

    @GetMapping("/tutor/{id}/viewed_history")
    @ApiOperation(value = "tutor viewed history", notes = "返回Tutor被查看历史，便于tutor分析用户")
    public Result<List<TutorViewHistory>> changeAvailability(@PathVariable("id") Integer tutorId,
                                                       @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if( tutorId < 0 ){
            String message = messageHandler.getFailResponseMessage("40008");
            return ResultGenerator.fail(message);
        }
        List<TutorViewHistory> tutorViewHistories = tutorService.findViewHistoryByTutorId(tutorId, pageNum, pageSize);
        return ResultGenerator.success(tutorViewHistories);
    }


}
