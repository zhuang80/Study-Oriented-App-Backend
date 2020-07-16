package com.wequan.bu.service.impl;

import com.github.pagehelper.PageHelper;
import com.wequan.bu.quartz.UpdateStatusJob;
import com.wequan.bu.repository.dao.OnlineEventMapper;
import com.wequan.bu.repository.model.OnlineEvent;
import com.wequan.bu.repository.model.Tutor;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.OnlineEventService;
import com.wequan.bu.service.TutorService;
import com.wequan.bu.util.OnlineEventStatus;
import com.wequan.bu.util.OnlineEventType;
import org.apache.ibatis.session.RowBounds;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author ChrisChen
 */
@Service
public class OnlineEventServiceImpl extends AbstractService<OnlineEvent> implements OnlineEventService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private OnlineEventMapper onlineEventMapper;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void postConstruct() {
        this.setMapper(onlineEventMapper);
    }

    @Override
    public List<OnlineEvent> getUserOnlineEvents(Integer userId, Integer pageNum, Integer pageSize, Integer... type) {
        Integer typeId = null;
        if (type != null && type.length > 0) {
            typeId = type[0];
        }
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return onlineEventMapper.selectByUserIdAndType(userId, typeId, rowBounds);
    }

    @Override
    public void saveOnlineEvent(OnlineEvent onlineEvent) throws Exception{
        if(OnlineEventType.PUBLIC_CLASS.getValue() == onlineEvent.getType()){
            Tutor tutor = tutorService.findByUserId(onlineEvent.getCreateBy());
            if(tutor == null) {
                throw new Exception("You have no right to create a public class.");
            }
        }

        onlineEvent.setCreateTime(LocalDateTime.now());
        onlineEvent.setGuid(String.valueOf(UUID.randomUUID()));
        onlineEventMapper.insertSelective(onlineEvent);

        addStatusUpdationQuartzJobAndTrigger(onlineEvent, onlineEvent.getStartTime(), OnlineEventStatus.ONGOING.getValue());
        addStatusUpdationQuartzJobAndTrigger(onlineEvent, onlineEvent.getEndTime(), OnlineEventStatus.DONE.getValue());

    }

    @Override
    public List<OnlineEvent> findAll(Integer pageNum, Integer pageSize){
        if(pageNum == null){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        return onlineEventMapper.selectAll();
    }

    @Override
    public List<OnlineEvent> findAllPublicClasses(Integer pageNum, Integer pageSize){
        if(pageNum == null){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        return onlineEventMapper.selectAllByType(OnlineEventType.PUBLIC_CLASS.getValue());
    }

    @Override
    public List<OnlineEvent> findAllActivities(Integer pageNum, Integer pageSize){
        if(pageNum == null){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        return onlineEventMapper.selectAllByType(OnlineEventType.ACTIVITY.getValue());
    }

    @Override
    public List<OnlineEvent> findAllBySchoolId(Integer id, Integer pageNum, Integer pageSize){
        if(pageNum == null){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        return onlineEventMapper.selectAllBySchoolId(id);
    }

    @Override
    public void updateName(Integer id, String name){
        OnlineEvent onlineEvent = onlineEventMapper.selectByPrimaryKey(id);
        onlineEvent.setName(name);
        onlineEvent.setUpdateTime(LocalDateTime.now());
        onlineEventMapper.updateByPrimaryKeySelective(onlineEvent);
    }

    @Override
    public void updateDescription(Integer id, String description) {
        OnlineEvent onlineEvent = onlineEventMapper.selectByPrimaryKey(id);
        onlineEvent.setUpdateTime(LocalDateTime.now());
        onlineEvent.setBriefDescription(description);
        onlineEventMapper.updateByPrimaryKeySelective(onlineEvent);
    }

    @Override
    public void updateFee(Integer id, Integer fee) {
        OnlineEvent onlineEvent = onlineEventMapper.selectByPrimaryKey(id);
        onlineEvent.setUpdateTime(LocalDateTime.now());
        onlineEvent.setFee(fee);
        onlineEventMapper.updateByPrimaryKeySelective(onlineEvent);
    }

    @Override
    public void updateMethod(Integer id, String method) {
        OnlineEvent onlineEvent = onlineEventMapper.selectByPrimaryKey(id);
        onlineEvent.setUpdateTime(LocalDateTime.now());
        onlineEvent.setMethod(method);
        onlineEventMapper.updateByPrimaryKeySelective(onlineEvent);
    }

    @Override
    public void updateStartTime(Integer id, LocalDateTime startTime) {
        OnlineEvent onlineEvent = onlineEventMapper.selectByPrimaryKey(id);
        onlineEvent.setUpdateTime(LocalDateTime.now());
        onlineEvent.setStartTime(startTime);
        onlineEventMapper.updateByPrimaryKeySelective(onlineEvent);
    }

    @Override
    public void updateEndTime(Integer id, LocalDateTime endTime) {
        OnlineEvent onlineEvent = onlineEventMapper.selectByPrimaryKey(id);
        onlineEvent.setUpdateTime(LocalDateTime.now());
        onlineEvent.setEndTime(endTime);
        onlineEventMapper.updateByPrimaryKeySelective(onlineEvent);
    }

    @Override
    public void updateVisibility(Integer id, boolean visibility) {
        OnlineEvent onlineEvent = onlineEventMapper.selectByPrimaryKey(id);
        onlineEvent.setUpdateTime(LocalDateTime.now());
        onlineEvent.setVisible(visibility);
        onlineEventMapper.updateByPrimaryKeySelective(onlineEvent);
    }

    @Override
    public void updateMethodDetail(Integer id, String methodDetail) {
        OnlineEvent onlineEvent = onlineEventMapper.selectByPrimaryKey(id);
        onlineEvent.setUpdateTime(LocalDateTime.now());
        onlineEvent.setMethodDetail(methodDetail);
        onlineEventMapper.updateByPrimaryKeySelective(onlineEvent);
    }

    @Override
    public void updateLogo(Integer id, String logo) {
        OnlineEvent onlineEvent = onlineEventMapper.selectByPrimaryKey(id);
        onlineEvent.setUpdateTime(LocalDateTime.now());
        onlineEvent.setLogo(logo);
        onlineEventMapper.updateByPrimaryKeySelective(onlineEvent);
    }

    @Override
    public void updateTag(Integer id, Short tagId) {
        OnlineEvent onlineEvent = onlineEventMapper.selectByPrimaryKey(id);
        onlineEvent.setUpdateTime(LocalDateTime.now());
        onlineEvent.setTagId(tagId);
        onlineEventMapper.updateByPrimaryKeySelective(onlineEvent);
    }

    @Override
    public void updateStatus(Integer id, Short status){
        OnlineEvent onlineEvent = onlineEventMapper.selectByPrimaryKey(id);
        onlineEvent.setUpdateTime(LocalDateTime.now());
        onlineEvent.setStatus(status);
        onlineEventMapper.updateByPrimaryKeySelective(onlineEvent);
    }

    @Override
    public void addStatusUpdationQuartzJobAndTrigger(OnlineEvent onlineEvent, LocalDateTime time, Short status) throws Exception {
        //set up a job detail
        JobDetail startJobDetail = JobBuilder.newJob(UpdateStatusJob.class)
                .withIdentity(onlineEvent.getGuid(),
                        status == OnlineEventStatus.ONGOING.getValue() ? "onlineEvent_start" : "onlineEvent_end")
                .usingJobData("time", time.toString())
                .usingJobData("status", String.valueOf(status))
                .usingJobData("guid", onlineEvent.getGuid())
                .usingJobData("id", onlineEvent.getId())
                .build();
        //set a cron expression
        String startCron = String.format("%d %d %d %d %d ? %d",
                time.getSecond(),
                time.getMinute(),
                time.getHour(),
                time.getDayOfMonth(),
                time.getMonth().getValue(),
                time.getYear());
        //set up a trigger
        CronTrigger startCronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(onlineEvent.getGuid(), status == 1? "onlineEvent_start" : "onlineEvent_end")
                .withSchedule(CronScheduleBuilder.cronSchedule(startCron))
                .build();

        //add job and trigger in scheduler
        try{
            scheduler.scheduleJob(startJobDetail, startCronTrigger);
        }catch (SchedulerException e){
            e.printStackTrace();
            throw new Exception("Fail to set up quartz job.");
        }
    }

}
