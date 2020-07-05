package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.Appointment;
import com.wequan.bu.service.AppointmentService;
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
@Api(tags = "Appointment")
public class AppointmentController {

    private static final Logger log = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/appointments")
    @ApiOperation(value = "Available appointment", notes = "返回appointment列表，按临近时间倒序")
    public Result<List<Appointment>> getAvailableAppointments(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return ResultGenerator.success(appointmentService.findAll(pageNum, pageSize));
    }

    @GetMapping("/appointment/{id}")
    @ApiOperation(value = "Appointment detail", notes = "Appointment详情")
    public Result<Appointment> getAppointment(@PathVariable("id") Integer id) {
        Appointment result = appointmentService.findById(id);
        return ResultGenerator.success(result);
    }

    @PostMapping("/appointment")
    @ApiOperation(value = "Create an appointment by tutor", notes = "返回创建appointment成功与否")
    public Result addAppointment(@RequestBody Appointment appointment) {
        appointmentService.makeAppointment(appointment);
        return ResultGenerator.success();
    }

    @PutMapping("tutor/{id}/appointment/{appointment_id}")
    @ApiOperation(value= "update an appointment by tutor", notes = "如果订单还未付款，tutor可以改动appointment，若订单已经付款，tutor将不能再改动appointment.改动appointment的金额后，会删除原订单，生成一个新的订单")
    public Result updateAppointment(@RequestBody Appointment appointment,
                                    @PathVariable("id") Integer tutorId,
                                    @PathVariable("appointment_id") Integer appointmentId) {
        try{
            appointmentService.updateAppointment(appointment, tutorId, appointmentId);
        }catch (Exception e){
            return ResultGenerator.fail(e.getMessage());
        }
        return ResultGenerator.success();
    }
}
