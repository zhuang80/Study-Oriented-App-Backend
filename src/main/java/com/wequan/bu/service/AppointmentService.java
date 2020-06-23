package com.wequan.bu.service;

import com.wequan.bu.controller.vo.Appointment;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
public interface AppointmentService extends Service<Appointment> {
    public List<Appointment> findByTutorId(Integer tutorId);
}
