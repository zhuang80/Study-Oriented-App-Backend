package com.wequan.bu.repository.dao;

import com.wequan.bu.repository.model.AppointmentChangeRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface AppointmentChangeRecordMapper extends GeneralMapper<AppointmentChangeRecord> {

    public List<AppointmentChangeRecord> selectPendingRefundApplication();
}
