package com.wequan.bu.quartz;

import com.wequan.bu.service.OnlineEventService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Zhaochao Huang
 */
@Component
public class UpdateStatusJob extends QuartzJobBean {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private OnlineEventService onlineEventService;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Trigger trigger = jobExecutionContext.getTrigger();
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();

        Short status = Short.parseShort(jobDataMap.getString("status"));
        String guid = jobDataMap.getString("guid");
        Integer id = jobDataMap.getInt("id");

        onlineEventService.updateStatus(id, status);
        System.out.println("Update Status Job is triggered.-----------"+ LocalDateTime.now().toString());
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
