package com.wequan.bu.service.impl;

import com.stripe.model.PaymentIntent;
import com.wequan.bu.controller.vo.Transaction;
import com.wequan.bu.repository.dao.AppointmentMapper;
import com.wequan.bu.repository.dao.TransactionMapper;
import com.wequan.bu.repository.dao.TutorMapper;
import com.wequan.bu.repository.model.Appointment;
import com.wequan.bu.repository.model.Tutor;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.AppointmentService;
import com.wequan.bu.service.TransactionService;
import com.wequan.bu.util.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

/**
 * @author Zhaochao Huang
 */
@Service
public class TransactionServiceImpl extends AbstractService<Transaction> implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private TutorMapper tutorMapper;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(transactionMapper);
    }

    @Override
    public void saveAppointmentTransaction(PaymentIntent paymentIntent) {
        Map<String, String> metadata = paymentIntent.getMetadata();
        Integer appointmentId = Integer.parseInt(metadata.get("appointment_id"));

        Transaction transaction = generateTransaction(paymentIntent);
        transactionMapper.insertSelective(transaction);
        appointmentService.updateTransactionIdByPrimaryKey(appointmentId, transaction.getId());
    }

    @Override
    public void update(PaymentIntent paymentIntent) {
        Transaction transaction = new Transaction();
        short status = getStatus(paymentIntent.getStatus());
        transaction.setStatus(status);
        transaction.setThirdPartyTransactionId(paymentIntent.getId());
        transactionMapper.updateByThirdPartyTransactionId(transaction);
    }

    private LocalDateTime convertTimestampToLocalDateTime(Long timestamp){
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp),
                TimeZone.getDefault().toZoneId());
    }

    private short getStatus(String status){
        if(status.equals("succeeded")) {
            return TransactionStatus.SUCCEEDED.getValue();
        }
        if(status.equals("requires_payment_method")){
            return TransactionStatus.REQUIRES_PAYMENT_METHOD.getValue();
        }
        if(status.equals("canceled")){
            return TransactionStatus.CANCELED.getValue();
        }
        if(status.equals("processing")){
            return TransactionStatus.PROCESSING.getValue();
        }
        return -1;
    }

    /**
     * generate the transaction of an appointment
     * @param paymentIntent
     * @return
     */
    private Transaction generateTransaction(PaymentIntent paymentIntent){
        //fetch meta data appointment Id and transaction type
        Map<String, String> metadata = paymentIntent.getMetadata();
        Integer appointmentId = Integer.parseInt(metadata.get("appointment_id"));
        short type = Short.parseShort(metadata.get("type"));

        Appointment appointment = appointmentMapper.selectByPrimaryKey(appointmentId);
        Tutor tutor = tutorMapper.selectByPrimaryKey(appointment.getTutorId());

        Transaction transaction = new Transaction();
        transaction.setId(String.valueOf(UUID.randomUUID()));
        transaction.setType(type);
        transaction.setPayFrom(appointment.getUserId());
        transaction.setPayTo(tutor.getUser().getId());
        transaction.setPayAmount(appointment.getFee());
        transaction.setPaymentMethod((short) 0);
        transaction.setThirdPartyTransactionId(paymentIntent.getId());
        LocalDateTime createdTime = convertTimestampToLocalDateTime(paymentIntent.getCreated());

        transaction.setCreateTime(createdTime);
        short status = getStatus(paymentIntent.getStatus());
        transaction.setStatus(status);

        return transaction;
    }
}
