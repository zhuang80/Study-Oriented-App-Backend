package com.wequan.bu.repository.dao;

import com.wequan.bu.controller.vo.Appointment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface AppointmentMapper extends GeneralMapper<Appointment> {
    public List<Appointment> selectByTutorId(@Param("tutor_id") Integer tutorId);
}
