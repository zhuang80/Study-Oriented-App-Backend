package com.wequan.bu.service;

import com.wequan.bu.repository.model.Appointment;
import com.wequan.bu.repository.model.extend.AppointmentBriefInfo;

import java.util.List;

/**
 * @author ChrisChen
 */
public interface AppointmentService extends Service<Appointment> {

    /**
     * 根据用户id获取AppointmentBriefInfo列表
     * @param userId 用户id
     * @return AppointmentBriefInfo列表
     */
    List<AppointmentBriefInfo> getUserAppointments(Integer userId, Integer pageNum, Integer pageSize);
}
