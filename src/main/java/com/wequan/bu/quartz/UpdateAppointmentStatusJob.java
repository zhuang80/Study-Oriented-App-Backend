package com.wequan.bu.quartz;

import com.wequan.bu.controller.AppointmentController;
import com.wequan.bu.repository.model.Appointment;
import com.wequan.bu.service.AppointmentService;
import com.wequan.bu.service.OnlineEventService;
import com.wequan.bu.util.AppointmentStatus;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

public class UpdateAppointmentStatusJob extends QuartzJobBean {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private AppointmentService appointmentService;

    private static final Logger log = LoggerFactory.getLogger(UpdateAppointmentStatusJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Trigger trigger = jobExecutionContext.getTrigger();
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();

        Short nextStatus = Short.parseShort(jobDataMap.getString("status"));
        Integer id = jobDataMap.getInt("id");

        Appointment appointment = appointmentService.findById(id);

        //when the appointment is starting
        if(nextStatus == AppointmentStatus.IN_PROGRESS.getValue()) {
            //update the status to IN_PROGRESS if it's paid
            if(appointment.getStatus() == AppointmentStatus.PAID.getValue()) {
                appointmentService.updateStatus(id, AppointmentStatus.IN_PROGRESS.getValue());
                log.info("The appointment is paid. Update to IN_PROGRESS");
            }
            //update the status to CANCELLED if it's not paid
            if(appointment.getStatus() == AppointmentStatus.DEFAULT.getValue()) {
                appointmentService.updateStatus(id, AppointmentStatus.CANCELLED.getValue());
                log.info("The appointment is not paid. Update to CANCELLED");
            }
        }

        log.info("Update Appointment Status Job is triggered.-----------"+ LocalDateTime.now().toString());
        log.info(trigger.getKey().toString());
        log.info(jobDetail.getKey().toString());
        //delete job
        try{
            scheduler.pauseTrigger(trigger.getKey());
            scheduler.unscheduleJob(trigger.getKey());
            scheduler.deleteJob(jobDetail.getKey());
        }catch (SchedulerException e){
            e.printStackTrace();
        }
    }
}

