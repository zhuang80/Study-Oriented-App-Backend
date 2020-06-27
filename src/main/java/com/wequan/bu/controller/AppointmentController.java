package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.Appointment;
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
@Api(tags = "Appointment")
public class AppointmentController {

    private static final Logger log = LoggerFactory.getLogger(AppointmentController.class);

    @GetMapping("/appointments")
    @ApiOperation(value = "Available appointment", notes = "返回appointment列表，按临近时间倒序")
    public Result<List<Appointment>> getAvailableAppointments(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return null;
    }

    @GetMapping("/appointment/{id}")
    @ApiOperation(value = "Appointment detail", notes = "Appointment详情")
    public Result<Appointment> getAppointment(@PathVariable("id") Integer id) {
        Appointment result = null;
        return ResultGenerator.success(result);
    }

    @PostMapping("/appointment")
    @ApiOperation(value = "Create an appointment by tutor", notes = "返回创建appointment成功与否")
    public Result addAppointment(@RequestBody Appointment appointment) {
        return ResultGenerator.success();
    }

}
