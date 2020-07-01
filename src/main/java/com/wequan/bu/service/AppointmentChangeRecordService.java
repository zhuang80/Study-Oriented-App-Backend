package com.wequan.bu.service;

import com.wequan.bu.repository.model.AppointmentChangeRecord;

import java.util.List;

public interface AppointmentChangeRecordService extends Service<AppointmentChangeRecord> {

    public void addRecordByTutor(String transactionId, Integer tutorId);

    public void addRecordByUser(String transactionId, Integer userId, Integer refundAmount);

    public List<AppointmentChangeRecord> findPendingRefundApplication();

    public void approve(Integer id, String comment);

    public void reject(Integer id, String comment);
}
