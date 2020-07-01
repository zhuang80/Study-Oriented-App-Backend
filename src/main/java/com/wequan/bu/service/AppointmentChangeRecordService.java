package com.wequan.bu.service;

import com.wequan.bu.repository.model.AppointmentChangeRecord;

public interface AppointmentChangeRecordService extends Service<AppointmentChangeRecord> {

    public void addRecordByTutor(String transactionId, Integer tutorId);

    public void addRecordByUser(String transactionId, Integer userId, Integer refundAmount);
}
