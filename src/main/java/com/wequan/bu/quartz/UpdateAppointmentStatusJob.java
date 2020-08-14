package com.wequan.bu.quartz;

import com.wequan.bu.service.AppointmentService;
import com.wequan.bu.service.OnlineEventService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

public class UpdateAppointmentStatusJob extends QuartzJobBean {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private AppointmentService appointmentService;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Trigger trigger = jobExecutionContext.getTrigger();
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();

        Short status = Short.parseShort(jobDataMap.getString("status"));
        Integer id = jobDataMap.getInt("id");

        appointmentService.updateStatus(id, status);
        System.out.println("Update Appointment Status Job is triggered.-----------"+ LocalDateTime.now().toString());
        System.out.println(trigger.getKey().toString());
        System.out.println(jobDetail.getKey().toString());
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

