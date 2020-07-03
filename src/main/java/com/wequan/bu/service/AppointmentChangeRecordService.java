package com.wequan.bu.service;

import com.stripe.exception.StripeException;
import com.wequan.bu.repository.model.AppointmentChangeRecord;

import java.util.List;

public interface AppointmentChangeRecordService extends Service<AppointmentChangeRecord> {

    public void addRecordByTutor(String transactionId, Integer tutorId);

    public void addRecordByUser(String transactionId, Integer userId, Integer refundAmount);

    public List<AppointmentChangeRecord> findPendingRefundApplication();

    public void approve(Integer id, String comment) throws StripeException;

    public void reject(Integer id, String comment);
}
