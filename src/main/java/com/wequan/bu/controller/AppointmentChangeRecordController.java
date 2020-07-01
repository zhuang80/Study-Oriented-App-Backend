package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.AppointmentChangeRecord;
import com.wequan.bu.service.AppointmentChangeRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@RestController
@Api(tags = "Appointment Change Record")
public class AppointmentChangeRecordController {

    @Autowired
    private AppointmentChangeRecordService appointmentChangeRecordService;

    @GetMapping("/pending_refund_application")
    @ApiOperation(value = "get pending refund application", notes = "返回未处理的退款申请")
    public Result<List<AppointmentChangeRecord>> getPendingRefundApplication(){
        return ResultGenerator.success(appointmentChangeRecordService.findPendingRefundApplication());
    }

    @PutMapping("/pending_refund_application/{id}/approve")
    @ApiOperation(value = "admin approve refund application", notes = "管理员批准退款")
    public Result approveRefundApplication(@PathVariable("id") Integer id,
                                           @RequestBody String comment){
        appointmentChangeRecordService.approve(id, comment);
        return ResultGenerator.success();
    }

    @PutMapping("/pending_refund_application/{id}/reject")
    @ApiOperation(value = "admin reject refund application", notes = "管理员拒绝退款申请")
    public Result rejectRefundApplication(@PathVariable("id") Integer id,
                                          @RequestBody String comment){
        appointmentChangeRecordService.reject(id, comment);
        return ResultGenerator.success();
    }
}
