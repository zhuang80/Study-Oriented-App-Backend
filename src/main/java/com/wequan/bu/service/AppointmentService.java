package com.wequan.bu.service;

import com.wequan.bu.repository.model.Appointment;
import com.wequan.bu.repository.model.extend.AppointmentBriefInfo;
import com.wequan.bu.util.AppointmentStatus;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
public interface AppointmentService extends Service<Appointment> {

    public List<Appointment> findByTutorId(Integer tutorId);

    /**
     * 根据用户id获取AppointmentBriefInfo列表
     * @param userId 用户id
     * @return AppointmentBriefInfo列表
     */
    List<AppointmentBriefInfo> getUserAppointments(Integer userId, Integer pageNum, Integer pageSize);

    public void makeAppointment(Appointment appointment);

    public void updateTransactionIdByPrimaryKey(Integer appointmentId, String transactionId);

    public void updateAppointmentAndGenerateNewTransaction(Appointment appointment) throws Exception;

    public void updateStatus(String paymentIntentId, AppointmentStatus status);

    public Appointment findByTransactionId(String transactionId);

    public List<Appointment> findAll(Integer pageNum, Integer pageSize);
}
