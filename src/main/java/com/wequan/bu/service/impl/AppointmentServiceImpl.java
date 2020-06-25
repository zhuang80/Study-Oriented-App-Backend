package com.wequan.bu.service.impl;

import com.wequan.bu.repository.dao.AppointmentMapper;
import com.wequan.bu.repository.model.Appointment;
import com.wequan.bu.repository.model.extend.AppointmentBriefInfo;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.AppointmentService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author ChrisChen
 */
@Service
public class AppointmentServiceImpl extends AbstractService<Appointment> implements AppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @PostConstruct
    public void postConstruct() {
        this.setMapper(appointmentMapper);
    }

    @Override
    public List<AppointmentBriefInfo> getUserAppointments(Integer userId, Integer pageNum, Integer pageSize) {
        RowBounds rowBounds = new RowBounds(pageNum, pageSize);
        return appointmentMapper.selectByUserId(userId, rowBounds);
    }
}
