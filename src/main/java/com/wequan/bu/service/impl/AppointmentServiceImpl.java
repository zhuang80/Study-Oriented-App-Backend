package com.wequan.bu.service.impl;

import com.wequan.bu.controller.vo.Appointment;
import com.wequan.bu.repository.dao.AppointmentMapper;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Service
public class AppointmentServiceImpl extends AbstractService<Appointment> implements AppointmentService {
    @Autowired
    private AppointmentMapper appointmentMapper;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(appointmentMapper);
    }

    @Override
    public List<Appointment> findByTutorId(Integer tutorId) {
        return appointmentMapper.selectByTutorId(tutorId);
    }
}
