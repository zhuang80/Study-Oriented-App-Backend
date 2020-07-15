package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.CourseViewHistoryMapper;
import com.wequan.bu.repository.model.CourseViewHistory;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.CourseViewHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

/**
 * @author Zhaochao Huang
 */
@Service
public class CourseViewHistoryServiceImpl extends AbstractService<CourseViewHistory> implements CourseViewHistoryService {
    @Autowired
    private CourseViewHistoryMapper courseViewHistoryMapper;

    @PostConstruct
    public void postConstruct(){
       this.setMapper(courseViewHistoryMapper);
    }

    @Override
    public void recordHistory(Integer courseId, Integer userId) {
        CourseViewHistory courseViewHistory = new CourseViewHistory();
        courseViewHistory.setCourseId(courseId);
        courseViewHistory.setUserId(userId);
        courseViewHistory.setViewTime(LocalDateTime.now());
        courseViewHistoryMapper.insertSelective(courseViewHistory);
    }
}
