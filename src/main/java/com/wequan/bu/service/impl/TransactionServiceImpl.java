package com.wequan.bu.service.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.wequan.bu.controller.vo.Transaction;
import com.wequan.bu.repository.dao.AppointmentMapper;
import com.wequan.bu.repository.dao.TransactionMapper;
import com.wequan.bu.repository.dao.TutorMapper;
import com.wequan.bu.repository.model.Appointment;
import com.wequan.bu.repository.model.Tutor;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.AppointmentService;
import com.wequan.bu.service.StripeService;
import com.wequan.bu.service.TransactionService;
import com.wequan.bu.util.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
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

    @Autowired
    private StripeService stripeService;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(transactionMapper);
    }

    @Override
    public void saveAppointmentTransaction(PaymentIntent paymentIntent) {
        Map<String, String> metadata = paymentIntent.getMetadata();
       // Integer appointmentId = Integer.parseInt(metadata.get("appointment_id"));
        Integer appointmentId = 1;

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

    @Override
    public void delete(PaymentIntent paymentIntent) {
        transactionMapper.deleteByThirdPartyTransactionId(paymentIntent.getId());
    }

    @Override
    public void deleteTransaction(Integer id, String transactionId) throws StripeException {
        Transaction transaction = transactionMapper.selectByPrimaryKey(id);

        if(TransactionStatus.REQUIRES_PAYMENT_METHOD.getValue() == transaction.getStatus()){
            transactionMapper.deleteByPrimaryKey(transactionId);
            appointmentMapper.deleteByTransactionId(transactionId);
        }else if(TransactionStatus.SUCCEEDED.getValue() == transaction.getStatus()){
            //refund part of fee
            stripeService.createRefund(transactionId);
        }
    }

    @Override
    public Transaction findById(String id) {
        return transactionMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateStatus(String paymentIntentId, TransactionStatus status) {
        Transaction transaction = new Transaction();
        transaction.setStatus(status.getValue());
        transaction.setThirdPartyTransactionId(paymentIntentId);
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
       // Map<String, String> metadata = paymentIntent.getMetadata();
        //local test data
        Map<String, String> metadata = new HashMap<>();
        metadata.put("appointment_id", "1");
        metadata.put("type", "0");

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
