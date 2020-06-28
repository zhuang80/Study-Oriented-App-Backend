package com.wequan.bu.service.impl;

import com.stripe.model.PaymentIntent;
import com.wequan.bu.controller.vo.Transaction;
import com.wequan.bu.repository.dao.AppointmentMapper;
import com.wequan.bu.repository.dao.TransactionMapper;
import com.wequan.bu.repository.dao.TutorMapper;
import com.wequan.bu.repository.model.Appointment;
import com.wequan.bu.repository.model.Tutor;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.TransactionService;
import com.wequan.bu.util.TransactionType;
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
    private TutorMapper tutorMapper;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(transactionMapper);
    }

    @Override
    public void saveAppointmentTransaction(PaymentIntent paymentIntent) {
        Map<String, String> metadata = paymentIntent.getMetadata();

       Integer appointmentId = Integer.parseInt(metadata.get("appointment_id"));
       // Integer appointmentId = 0;
        Appointment appointment = appointmentMapper.selectByPrimaryKey(appointmentId);
        Tutor tutor = tutorMapper.selectByPrimaryKey(appointment.getTutorId());

        Transaction transaction = new Transaction();
        transaction.setId(String.valueOf(UUID.randomUUID()));
        transaction.setType((short) TransactionType.APPOINTMENT.getValue());
        transaction.setPayFrom(appointment.getUserId());
        transaction.setPayTo(tutor.getUser().getId());
        transaction.setPayAmount(appointment.getFee());
        transaction.setPaymentMethod((short) 0);
        transaction.setThirdPartyTransactionId(paymentIntent.getId());
        LocalDateTime createdTime = convertTimestampToLocalDateTime(paymentIntent.getCreated());

        transaction.setCreateTime(createdTime);
        short status = getStatus(paymentIntent.getStatus());
        transaction.setStatus(status);
        transactionMapper.insertSelective(transaction);
    }

    private LocalDateTime convertTimestampToLocalDateTime(Long timestamp){
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp),
                TimeZone.getDefault().toZoneId());
    }

    private short getStatus(String status){
        if(status.equals("succeeded")) {
            return 1;
        }
        return -1;
    }
}
