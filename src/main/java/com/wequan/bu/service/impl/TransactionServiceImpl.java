package com.wequan.bu.service.impl;

import com.github.pagehelper.PageHelper;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.wequan.bu.controller.vo.RefundApplication;
import com.wequan.bu.controller.vo.Transaction;
import com.wequan.bu.repository.dao.AppointmentMapper;
import com.wequan.bu.repository.dao.TransactionMapper;
import com.wequan.bu.repository.dao.TutorMapper;
import com.wequan.bu.repository.model.*;
import com.wequan.bu.service.*;
import com.wequan.bu.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
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

    @Autowired
    private CancellationPolicyService cancellationPolicyService;

    @Autowired
    private AppointmentChangeRecordService appointmentChangeRecordService;

    @Autowired
    private OnlineEventService onlineEventService;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(transactionMapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAppointmentTransaction(PaymentIntent paymentIntent) {
        Map<String, String> metadata = paymentIntent.getMetadata();
        Integer appointmentId = Integer.parseInt(metadata.get("appointment_id"));

        Transaction transaction = generateTransaction(paymentIntent);
        transactionMapper.insertSelective(transaction);
        appointmentService.updateTransactionIdByPrimaryKey(appointmentId, transaction.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTransaction(PaymentIntent paymentIntent){
        Map<String, String> metadata = paymentIntent.getMetadata();
        Short type = Short.parseShort(metadata.get("type"));

        if(TransactionType.PUBLIC_CLASS.getValue() == type){
            //save transaction information
            Transaction transaction = generateTransaction(paymentIntent);
            transactionMapper.insertSelective(transaction);

            //save online event and transaction relationship
            OnlineEventTransaction onlineEventTransaction = new OnlineEventTransaction();
            onlineEventTransaction.setOnlineEventId(Integer.parseInt(metadata.get("online_event_id")));
            onlineEventTransaction.setTransactionId(transaction.getId());
            onlineEventTransaction.setCreateTime(LocalDateTime.now());
            onlineEventService.saveOnlineEventTransaction(onlineEventTransaction);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PaymentIntent paymentIntent) {
        Transaction transaction = new Transaction();
        short status = getStatus(paymentIntent.getStatus());
        transaction.setStatus(status);
        transaction.setThirdPartyTransactionId(paymentIntent.getId());
        transaction.setPayAmount((int)(long) paymentIntent.getAmount());
        transactionMapper.updateByThirdPartyTransactionId(transaction);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(PaymentIntent paymentIntent) {
        transactionMapper.deleteByThirdPartyTransactionId(paymentIntent.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelTransactionByUser(Integer userId, String transactionId) throws Exception {
        Transaction transaction = transactionMapper.selectByPrimaryKey(transactionId);
        Integer refundAmount = 0;

        if(transaction == null) {
            throw new Exception("no such transaction");
        }
        if(TransactionStatus.REQUIRES_PAYMENT_METHOD.getValue() == transaction.getStatus()){
            stripeService.cancelPaymentIntent(transaction.getThirdPartyTransactionId());
        }else if(TransactionStatus.SUCCEEDED.getValue() == transaction.getStatus()){
            Appointment appointment = appointmentService.findByTransactionId(transactionId);

            //check if the start time of appointment is after current time
            if(appointment.getStartTime().isAfter(LocalDateTime.now())){
                //refund part of fee
                refundAmount = calculateRefundAmount(transactionId);

                stripeService.createRefund(transactionId, refundAmount);
            }else {
                throw new Exception("Please apply for refund request.");
            }
        }


        appointmentChangeRecordService.addRecordByUser(transactionId, userId, refundAmount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelTransactionByTutor(Integer tutorId, String transactionId) throws Exception {
        Transaction transaction = transactionMapper.selectByPrimaryKey(transactionId);

        if(TransactionStatus.REQUIRES_PAYMENT_METHOD.getValue() == transaction.getStatus()){
            stripeService.cancelPaymentIntent(transaction.getThirdPartyTransactionId());
        }else if(TransactionStatus.SUCCEEDED.getValue() == transaction.getStatus()){

            Integer amount = transaction.getPayAmount();
            stripeService.createRefund(transactionId, amount);
        }

        appointmentChangeRecordService.addRecordByTutor(transactionId, tutorId);
    }

    @Override
    public Transaction findById(String id) {
        return transactionMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String paymentIntentId, TransactionStatus status) {
        Transaction transaction = new Transaction();
        transaction.setStatus(status.getValue());
        transaction.setThirdPartyTransactionId(paymentIntentId);
        transaction.setUpdateTime(LocalDateTime.now());
        transactionMapper.updateByThirdPartyTransactionId(transaction);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRefundRecord(Charge charge) {
        Map<String, String> metadata = charge.getMetadata();

        Appointment appointment = appointmentMapper.selectByPrimaryKey(Integer.parseInt(metadata.get("appointment_id")));
        Tutor tutor = tutorMapper.selectByPrimaryKey(appointment.getTutorId());

        Transaction transaction = new Transaction();
        transaction.setId(String.valueOf(UUID.randomUUID()));
        transaction.setType(Short.parseShort(metadata.get("type")));
        transaction.setPayFrom(tutor.getUser().getId());
        transaction.setPayTo(appointment.getUserId());
        transaction.setPayAmount((int)(long) charge.getAmountRefunded());
        transaction.setPaymentMethod((short) PaymentMethod.CARD.getValue());
        transaction.setThirdPartyTransactionId(charge.getTransfer());
        transaction.setCreateTime(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.SUCCEEDED.getValue());
        transaction.setToTransactionId(appointment.getTransactionId());

        transactionMapper.insertSelective(transaction);
    }

    @Override
    public List<Transaction> findByUserId(Integer userId, Integer pageNum, Integer pageSize) {
        if(pageNum == null || pageNum <= 0 ) {
            pageNum = 1;
        }
        if(pageSize == null || pageSize <= 0){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        return transactionMapper.selectByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refundApply(RefundApplication refundApplication) {
        String transactionId = refundApplication.getTransactionId();
        Appointment appointment = appointmentService.findByTransactionId(transactionId);
        AppointmentChangeRecord record = new AppointmentChangeRecord();

        record.setAppointmentId(appointment.getId());
        record.setUserId(appointment.getUserId());
        record.setIsTutor(false);
        record.setChangeType(ChangeType.AFTER_APPOINTMENT.getValue());
        record.setChangeReason(refundApplication.getRefundReason());
        record.setRefundAmount(appointment.getFee());
        record.setCreateTime(LocalDateTime.now());
        record.setAdminAction(AdminAction.PEDNING.getValue());
        record.setTransactionId(transactionId);

        appointmentChangeRecordService.save(record);
    }

    @Override
    public List<Transaction> findAll(Integer pageNum, Integer pageSize) {
        if(pageNum == null || pageNum <= 0 ) {
            pageNum = 1;
        }
        if(pageSize == null || pageSize <= 0){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        return transactionMapper.selectAll();
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
        short type = Short.parseShort(metadata.get("type"));

        Transaction transaction = null;
        if(type == TransactionType.APPOINTMENT.getValue()) {
            Integer appointmentId = Integer.parseInt(metadata.get("appointment_id"));


            Appointment appointment = appointmentMapper.selectByPrimaryKey(appointmentId);
            Tutor tutor = tutorMapper.selectByPrimaryKey(appointment.getTutorId());

            transaction = new Transaction();
            transaction.setId(String.valueOf(UUID.randomUUID()));
            transaction.setType(type);
            transaction.setPayFrom(appointment.getUserId());
            transaction.setPayTo(tutor.getUser().getId());
            transaction.setPayAmount(appointment.getFee());
            transaction.setPaymentMethod((short) PaymentMethod.CARD.getValue());
            transaction.setThirdPartyTransactionId(paymentIntent.getId());
            LocalDateTime createdTime = convertTimestampToLocalDateTime(paymentIntent.getCreated());

            transaction.setCreateTime(createdTime);
            short status = getStatus(paymentIntent.getStatus());
            transaction.setStatus(status);
        }

        if(type == TransactionType.PUBLIC_CLASS.getValue()){
            Integer onlineEventId = Integer.parseInt(metadata.get("online_event_id"));

            OnlineEvent onlineEvent = onlineEventService.findById(onlineEventId);

            transaction = new Transaction();
            transaction.setId(String.valueOf(UUID.randomUUID()));
            transaction.setType(type);
            transaction.setPayFrom(Integer.parseInt(metadata.get("from")));
            transaction.setPayTo(Integer.parseInt(metadata.get("to")));
            transaction.setPayAmount((int) (long) paymentIntent.getAmount());
            transaction.setPaymentMethod((short) PaymentMethod.CARD.getValue());
            transaction.setThirdPartyTransactionId(paymentIntent.getId());
            LocalDateTime createdTime = convertTimestampToLocalDateTime(paymentIntent.getCreated());

            transaction.setCreateTime(createdTime);
            short status = getStatus(paymentIntent.getStatus());
            transaction.setStatus(status);
        }

        return transaction;
    }

    private Integer calculateRefundAmount(String transactionId){
        Appointment appointment = appointmentService.findByTransactionId(transactionId);
        CancellationPolicy cancellationPolicy = cancellationPolicyService.findByTutorId(appointment.getTutorId());

        Float amount = cancellationPolicy.getRefundRatio() * appointment.getFee();
        return Math.round(amount);
    }

}
