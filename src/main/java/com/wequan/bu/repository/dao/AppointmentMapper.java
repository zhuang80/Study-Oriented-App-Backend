package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.Appointment;
import com.wequan.bu.repository.model.extend.AppointmentBriefInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author ChrisChen
 */
@Mapper
public interface AppointmentMapper extends GeneralMapper<Appointment> {

    /**
     * 根据用户id获取AppointmentBriefInfo列表
     * @param userId 用户id
     * @return AppointmentBriefInfo列表
     */
    List<AppointmentBriefInfo> selectByUserId(Integer userId, RowBounds rowBounds);
}
