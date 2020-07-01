package com.wequan.bu.service.impl;

import com.stripe.exception.StripeException;
import com.wequan.bu.controller.vo.Transaction;
import com.wequan.bu.repository.dao.AppointmentChangeRecordMapper;
import com.wequan.bu.repository.model.Appointment;
import com.wequan.bu.repository.model.AppointmentChangeRecord;
import com.wequan.bu.service.*;
import com.wequan.bu.util.AdminAction;
import com.wequan.bu.util.ChangeType;
import com.wequan.bu.util.TransactionStatus;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Service
public class AppointmentChangeRecordServiceImpl extends AbstractService<AppointmentChangeRecord> implements AppointmentChangeRecordService {
    @Autowired
    private AppointmentChangeRecordMapper appointmentChangeRecordMapper;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private StripeService stripeService;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(appointmentChangeRecordMapper);
    }


    @Override
    public void addRecordByTutor(String transactionId, Integer tutorId) {
        Appointment appointment = appointmentService.findByTransactionId(transactionId);
        Transaction transaction = transactionService.findById(transactionId);

        if(appointment != null && transaction != null) {
            AppointmentChangeRecord record = new AppointmentChangeRecord();

            record.setAppointmentId(appointment.getId());
            record.setUserId(transaction.getPayTo());
            record.setIsTutor(true);
            record.setTransactionId(transactionId);
            record.setCreateTime(LocalDateTime.now());
            if(transaction.getStatus().equals(TransactionStatus.REQUIRES_PAYMENT_METHOD.getValue())){
                record.setChangeType(ChangeType.BEFORE_PAYMENT.getValue());
            }else if(transaction.getStatus().equals(TransactionStatus.SUCCEEDED.getValue())){
                if(appointment.getStartTime().isAfter(LocalDateTime.now())){
                    record.setChangeType(ChangeType.BEFORE_APPOINTMENT.getValue());
                }else{
                    record.setChangeType(ChangeType.AFTER_APPOINTMENT.getValue());
                }
                record.setRefundAmount(appointment.getFee());
            }
            appointmentChangeRecordMapper.insertSelective(record);
        }
    }

    @Override
    public void addRecordByUser(String transactionId, Integer userId, Integer refundAmount) {
        Appointment appointment = appointmentService.findByTransactionId(transactionId);
        Transaction transaction = transactionService.findById(transactionId);

        if(appointment != null && transaction != null) {
            AppointmentChangeRecord record = new AppointmentChangeRecord();

            record.setAppointmentId(appointment.getId());
            record.setUserId(userId);
            record.setIsTutor(false);
            record.setTransactionId(transactionId);
            record.setCreateTime(LocalDateTime.now());
            if(transaction.getStatus().equals(TransactionStatus.REQUIRES_PAYMENT_METHOD.getValue())){
                record.setChangeType(ChangeType.BEFORE_PAYMENT.getValue());
            }else if(transaction.getStatus().equals(TransactionStatus.SUCCEEDED.getValue())){
                record.setChangeType(ChangeType.BEFORE_APPOINTMENT.getValue());
                record.setRefundAmount(refundAmount);
            }
            appointmentChangeRecordMapper.insertSelective(record);
        }
    }

    @Override
    public List<AppointmentChangeRecord> findPendingRefundApplication() {
        return appointmentChangeRecordMapper.selectPendingRefundApplication();
    }

    @Override
    public void approve(Integer id, String comment) throws StripeException {
        AppointmentChangeRecord record = appointmentChangeRecordMapper.selectByPrimaryKey(id);
        record.setAdminAction(AdminAction.APPROVE.getValue());
        record.setAdminComment(comment);
        record.setUpdateTime(LocalDateTime.now());

        stripeService.createRefund(record.getTransactionId(), record.getRefundAmount());

        appointmentChangeRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void reject(Integer id, String comment) {
        AppointmentChangeRecord record = appointmentChangeRecordMapper.selectByPrimaryKey(id);
        record.setAdminAction(AdminAction.REJECT.getValue());
        record.setAdminComment(comment);
        record.setUpdateTime(LocalDateTime.now());

        appointmentChangeRecordMapper.updateByPrimaryKeySelective(record);
    }
}
