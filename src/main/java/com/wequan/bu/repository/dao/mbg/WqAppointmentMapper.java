package com.wequan.bu.repository.dao.mbg;

import com.wequan.bu.repository.model.mbg.WqAppointment;
import java.util.List;

public interface WqAppointmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WqAppointment record);

    WqAppointment selectByPrimaryKey(Integer id);

    List<WqAppointment> selectAll();

    int updateByPrimaryKey(WqAppointment record);
}