package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.RefundApplication;
import com.wequan.bu.controller.vo.TutorReview;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.Appointment;
import com.wequan.bu.repository.model.Tutor;
import com.wequan.bu.security.CurrentUser;
import com.wequan.bu.service.AppointmentService;
import com.wequan.bu.service.TransactionService;
import com.wequan.bu.service.TutorReviewService;
import com.wequan.bu.service.TutorService;
import com.wequan.bu.util.AppointmentStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Zhaochao Huang
 */
@RestController
@Api(tags = "Appointment")
public class AppointmentController {

    private static final Logger log = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private TransactionService transactionService;


    @Autowired
    private TutorReviewService tutorReviewService;

    @Autowired
    private TutorService tutorService;

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

    @PutMapping("/tutor/{id}/appointment/{appointment_id}")
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

    @PostMapping("/user/{id}/appointment/{appointment_id}/cancel")
    @ApiOperation(value = "cancel appointment", notes = "用户在付款前，可以任意取消订单，若付款后，在辅导开始前，取消订单，返还部分金额, 如果离辅导开始后，需要提交退款申请")
    public Result cancelAppointment(@CurrentUser Integer currentUserId,
                                    @PathVariable("appointment_id") Integer appointmentId) {
        Appointment appointment = appointmentService.findById(appointmentId);
        if(appointment == null) {
            return ResultGenerator.fail("No Such Appointment.");
        }
        if(!appointment.getUserId().equals(currentUserId)) {
            return ResultGenerator.fail("You are not authorized to do that.");
        }
        try {
            if(appointment.getStatus() == AppointmentStatus.DEFAULT.getValue()
                    || appointment.getStatus() == AppointmentStatus.PAID.getValue()) {
                transactionService.cancelTransactionByUser(appointment.getTransactionId());
                return ResultGenerator.success();
            }
            if(appointment.getStatus() == AppointmentStatus.IN_PROGRESS.getValue()) {
                return ResultGenerator.fail("Please apply for refund request.");
            }
            return ResultGenerator.fail("The operation is not supported.");
        }catch (Exception e) {
            return ResultGenerator.fail(e.getMessage());
        }
    }

    @PostMapping("/user/{id}/appointment/{appointment_id}/refund_apply")
    @ApiOperation(value = "apply refund appointment", notes = "用户在付款前，可以任意取消订单，若付款后，在辅导开始前，取消订单，返还部分金额, 如果离辅导开始后，需要提交退款申请")
    public Result refundAppointment(@CurrentUser Integer currentUserId,
                                    @PathVariable("appointment_id") Integer appointmentId,
                                    @RequestBody RefundApplication refundApplication) {
        Appointment appointment = appointmentService.findById(appointmentId);
        if(appointment == null) {
            return ResultGenerator.fail("No Such Appointment.");
        }
        if(!appointment.getUserId().equals(currentUserId)) {
            return ResultGenerator.fail("You are not authorized to do that.");
        }
        try {
            if(appointment.getStatus() == AppointmentStatus.IN_PROGRESS.getValue()) {
                transactionService.refundApply(refundApplication);
                return ResultGenerator.success();
            }
            return ResultGenerator.fail("The operation is not supported.");

        }catch (Exception e){
            return ResultGenerator.fail(e.getMessage());
        }
    }

    @PostMapping("/tutor/{id}/appointment/{appointment_id}/cancel")
    @ApiOperation(value = "cancel transaction", notes = "在用户付款前，tutor可以任意取消订单，如果付款后，tutor取消订单，需要退还全款")
    public Result cancelAppointmentByTutor(@PathVariable("id") Integer id,
                                           @CurrentUser Integer currentUserId,
                                           @PathVariable("appointment_id") Integer appointmentId){
        Appointment appointment = appointmentService.findById(appointmentId);
        if(appointment == null) {
            return ResultGenerator.fail("No Such Appointment.");
        }
        Tutor tutor = tutorService.findByUserId(currentUserId);

        log.info("The uer id: "+ currentUserId + "   " + tutor.getUserId());
        if(!appointment.getTutorId().equals(tutor.getId())) {
            return ResultGenerator.fail("You are not authorized to do that.");
        }
        try {
            if(appointment.getStatus() == AppointmentStatus.DEFAULT.getValue()
                    || appointment.getStatus() == AppointmentStatus.PAID.getValue()
                    || appointment.getStatus() == AppointmentStatus.IN_PROGRESS.getValue()){
                transactionService.cancelTransactionByTutor(tutor.getId(), appointment.getTransactionId());
                return ResultGenerator.success();
            }
            return ResultGenerator.fail("The operation is not supported");

        }catch (Exception e){
            return ResultGenerator.fail(e.getMessage());
        }

    }

    @PutMapping("/appointment/{appointment_id}/confirm")
    @ApiOperation(value="user confirm after appointment is finished", notes = "用户确认appointment完成")
    public Result confirmAppointment(@CurrentUser Integer currentUserId,
                                     @PathVariable("appointment_id") Integer appointmentId) {
        Appointment appointment = appointmentService.findById(appointmentId);
        if(appointment == null) {
            return ResultGenerator.fail("No Such Appointment.");
        }
        if(!appointment.getUserId().equals(currentUserId)) {
            return ResultGenerator.fail("You are not authorized to do that.");
        }
        appointmentService.updateStatus(appointmentId, AppointmentStatus.COMPLETED.getValue());
        return ResultGenerator.success();
    }

    @PostMapping("/appointment/{appointment_id}/review")
    @ApiOperation(value="review the tutor who gives you the tutorial", notes="用户评价tutor")
    public Result reviewAppointment(@CurrentUser Integer currentUserId,
                                    @PathVariable("appointment_id") Integer appointmentId,
                                    @RequestBody TutorReview tutorReview) {
        Appointment appointment = appointmentService.findById(appointmentId);
        if(appointment == null) {
            return ResultGenerator.fail("No Such Appointment.");
        }
        if(!appointment.getUserId().equals(currentUserId)) {
            return ResultGenerator.fail("You are not authorized to do that.");
        }
        if(appointment.getStatus() == AppointmentStatus.COMPLETED.getValue()) {
            appointmentService.updateStatus(appointmentId, AppointmentStatus.REVIEWED.getValue());
            tutorReview.setCreateTime(LocalDateTime.now());
            tutorReviewService.save(tutorReview);
            return ResultGenerator.success();
        }else {
            return ResultGenerator.fail("The operation is not supported.");
        }
    }
}
