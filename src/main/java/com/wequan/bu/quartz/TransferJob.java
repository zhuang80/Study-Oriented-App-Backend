package com.wequan.bu.quartz;

import com.stripe.exception.StripeException;
import com.wequan.bu.service.StripeService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Zhaochao Huang
 */
@Component
public class TransferJob extends QuartzJobBean {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private StripeService stripeService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Trigger trigger = jobExecutionContext.getTrigger();
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();

        Integer id = jobDataMap.getInt("id");

        try {
            stripeService.createSeparateTransfer(id);
        }catch (StripeException e){
            e.printStackTrace();
        }

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
