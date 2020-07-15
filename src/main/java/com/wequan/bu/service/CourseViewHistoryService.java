package com.wequan.bu.service;

import com.wequan.bu.repository.model.CourseViewHistory;

/**
 * @author Zhaochao Huang
 */
public interface CourseViewHistoryService extends Service<CourseViewHistory> {

    void recordHistory(Integer courseId, Integer userId);
}
