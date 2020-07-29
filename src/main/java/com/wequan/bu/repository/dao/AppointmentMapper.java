package com.wequan.bu.repository.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.wequan.bu.repository.model.Appointment;
import com.wequan.bu.repository.model.extend.AppointmentBriefInfo;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface AppointmentMapper extends GeneralMapper<Appointment> {

    public List<Appointment> selectByTutorId(@Param("tutor_id") Integer tutorId, @Param("status") Short status);

    /**
     * 根据用户id获取AppointmentBriefInfo列表
     *
     * @param userId 用户id
     * @return AppointmentBriefInfo列表
     */
    List<AppointmentBriefInfo> selectByUserId(Integer userId, RowBounds rowBounds, @Param("status") Short status);

    public void deleteByTransactionId(@Param("transaction_id") String transactionId);

    public void updateByTransactionIdSelective(Appointment appointment);

    public Appointment selectByTransactionId(@Param("transaction_id") String transactionId);

}
