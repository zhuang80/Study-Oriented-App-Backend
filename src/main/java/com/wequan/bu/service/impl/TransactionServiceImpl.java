package com.wequan.bu.service.impl;

import com.github.pagehelper.PageHelper;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.wequan.bu.controller.vo.RefundApplication;
import com.wequan.bu.controller.vo.Transaction;
import com.wequan.bu.repository.dao.AppointmentMapper;
import com.wequan.bu.repository.dao.TransactionMapper;
import com.wequan.bu.repository.dao.TutorMapper;
import com.wequan.bu.repository.model.*;
import com.wequan.bu.service.*;
import com.wequan.bu.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${APPLICATION_FEE_RATE}")
    private Double applicationFeeRate;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private TutorMapper tutorMapper;

    @Autowired
    private TutorService tutorService;

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
    public void savePublicClassTransaction(PaymentIntent paymentIntent){
        Map<String, String> metadata = paymentIntent.getMetadata();
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

    /**
     * create and save transaction after payment_intent.created webhook is triggered
     * @param paymentIntent
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTransaction(PaymentIntent paymentIntent){
        Map<String, String> metadata = paymentIntent.getMetadata();
        Short type = Short.parseShort(metadata.get("type"));

        if(TransactionType.PUBLIC_CLASS.getValue() == type){
           savePublicClassTransaction(paymentIntent);
        }

        if(TransactionType.APPOINTMENT.getValue() == type){
            saveAppointmentTransaction(paymentIntent);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByPaymentIntent(PaymentIntent paymentIntent) {
        Transaction transaction = new Transaction();
        short status = getStatus(paymentIntent.getStatus());
        String transferId= paymentIntent.getCharges()
                .getData()
                .get(0)
                .getTransfer();

        transaction.setStatus(status);
        transaction.setThirdPartyTransactionId(paymentIntent.getId());
        transaction.setPayAmount((int)(long) paymentIntent.getAmount());
        transaction.setTransferId(transferId);
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
        Short type = transaction.getType();

        if(type == TransactionType.APPOINTMENT.getValue()){
            cancelAppointmentTypeTransactionByUser(transaction);
        }

        if(type == TransactionType.PUBLIC_CLASS.getValue()){
            cancelPublicClassTypeTransactionByUser(transaction);
        }
    }

    public void cancelAppointmentTypeTransactionByUser(Transaction transaction) throws Exception {
        Integer refundAmount = 0;
        if(TransactionStatus.REQUIRES_PAYMENT_METHOD.getValue() == transaction.getStatus()){
            stripeService.cancelPaymentIntent(transaction.getThirdPartyTransactionId());
        }else if(TransactionStatus.SUCCEEDED.getValue() == transaction.getStatus()){
            Appointment appointment = appointmentService.findByTransactionId(transaction.getId());

            //check if the start time of appointment is after current time
            if(appointment.getStartTime().isAfter(LocalDateTime.now())){
                //refund part of fee
                refundAmount = calculateRefundAmount(transaction.getId());

                stripeService.createRefund(transaction.getId(), refundAmount);
            }else {
                throw new Exception("Please apply for refund request.");
            }
        }

        appointmentChangeRecordService.addRecordByUser(transaction.getId(), transaction.getPayFrom(), refundAmount);
    }

    public void cancelPublicClassTypeTransactionByUser(Transaction transaction) throws Exception {
        Integer refundAmount = 0;
        if(TransactionStatus.REQUIRES_PAYMENT_METHOD.getValue() == transaction.getStatus()){
            stripeService.cancelPaymentIntent(transaction.getThirdPartyTransactionId());
        }else if(TransactionStatus.SUCCEEDED.getValue() == transaction.getStatus()){
            OnlineEvent onlineEvent = onlineEventService.findByTransactionId(transaction.getId());

            //check if the start time of appointment is after current time
            if(onlineEvent.getStartTime().isAfter(LocalDateTime.now())){
                //refund part of fee
                refundAmount = calculateRefundAmount(transaction.getId());

                stripeService.createSeparateRefund(transaction.getId(), refundAmount);
                stripeService.reverseTransfer(transaction.getId(), refundAmount);
            }else {
                throw new Exception("Can't cancel public class after it starts.");
            }

        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelTransactionByTutor(Integer tutorId, String transactionId) throws Exception {
        Transaction transaction = transactionMapper.selectByPrimaryKey(transactionId);
        if(transaction == null) {
            throw new Exception("No such transaction.");
        }

        Short type = transaction.getType();
        Short status = transaction.getStatus();

        if(type == TransactionType.APPOINTMENT.getValue()){
            if(TransactionStatus.REQUIRES_PAYMENT_METHOD.getValue() == status){
                stripeService.cancelPaymentIntent(transaction.getThirdPartyTransactionId());
            }else if(TransactionStatus.SUCCEEDED.getValue() == status){
                Integer amount = transaction.getPayAmount() - (int) Math.round(transaction.getPayAmount() * applicationFeeRate);
                stripeService.createRefund(transactionId, amount);
            }

            appointmentChangeRecordService.addRecordByTutor(transactionId, tutorId);
        }

        if(type == TransactionType.PUBLIC_CLASS.getValue()){
            if(TransactionStatus.REQUIRES_PAYMENT_METHOD.getValue() == status){
                stripeService.cancelPaymentIntent(transaction.getThirdPartyTransactionId());
            }else if(TransactionStatus.SUCCEEDED.getValue() == status){
                Integer amount = transaction.getPayAmount() - (int) Math.round(transaction.getPayAmount() * applicationFeeRate);
                stripeService.createSeparateRefund(transactionId, amount);
                stripeService.reverseTransfer(transactionId, amount);
            }
        }

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
    public void addRefundTransactionRecord(Charge charge) {
        Map<String, String> metadata = charge.getMetadata();

        Refund refund = charge.getRefunds().getData().get(0);
        Map<String, String> refundMetadata = refund.getMetadata();
        String transactionId = refundMetadata.get("transaction_id");

        Transaction trans = transactionMapper.selectByPrimaryKey(transactionId);

        Transaction transaction = new Transaction();
        transaction.setId(String.valueOf(UUID.randomUUID()));
        transaction.setType(Short.parseShort(metadata.get("type")));
        transaction.setPayFrom(trans.getPayTo());
        transaction.setPayTo(trans.getPayFrom());
        transaction.setPayAmount((int)(long) charge.getAmountRefunded());
        transaction.setPaymentMethod((short) PaymentMethod.CARD.getValue());
        transaction.setCreateTime(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.SUCCEEDED.getValue());
        transaction.setToTransactionId(transactionId);

        transactionMapper.insertSelective(transaction);
    }


    @Override
    public List<Transaction> findByUserId(Integer userId, Short status, Integer pageNum, Integer pageSize) {
        if(pageNum == null || pageNum <= 0 ) {
            pageNum = 1;
        }
        if(pageSize == null || pageSize <= 0){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        return transactionMapper.selectByUserId(userId, status);
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

    @Override
    public Integer findTotalTransactionAmountByOnlineEventId(Integer id) {
        return transactionMapper.selectTotalTransactionAmountByOnlineEventId(id, TransactionStatus.SUCCEEDED.getValue());
    }

    @Override
    public Integer findTransactionAmountByOnlineEventIdAndUserId(Integer onlineEventId, Integer userId) {
        Transaction transaction = transactionMapper.selectByOnlineEventIdAndUserId(onlineEventId, userId);

        if(transaction != null){
            Short status = transaction.getStatus();
            Integer amount = transaction.getPayAmount();
            Integer applicationFeeAmount = transaction.getApplicationFeeAmount();

            if(status == TransactionStatus.SUCCEEDED.getValue()){
                return amount - applicationFeeAmount;
            }
            if(status == TransactionStatus.REFUNDED.getValue()){
                Transaction refund = transactionMapper.selectRefundTransactionByToTransactionId(transaction.getId());
                return amount - applicationFeeAmount - refund.getPayAmount();
            }
        }
        return -1;
    }

    @Override
    public Transaction findByOnlineEventIdAndUserId(Integer publicClassId, Integer userId) {
        return transactionMapper.selectByOnlineEventIdAndUserId(publicClassId, userId);
    }

    @Override
    public Transaction findByThirdPartyTransactionId(String paymentIntentId) {
        return transactionMapper.selectByThirdPartyTransactionId(paymentIntentId);
    }

    @Override
    public void updateTransferIdByThirdPartyTransactionId(String paymentIntentId, String transferId) {
        Transaction transaction = new Transaction();
        transaction.setTransferId(transferId);
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
     * generate the transaction after payment_intent.created webhook is triggered
     * @param paymentIntent
     * @return
     */
    private Transaction generateTransaction(PaymentIntent paymentIntent){
        //fetch meta data
        Map<String, String> metadata = paymentIntent.getMetadata();
        short type = Short.parseShort(metadata.get("type"));
        Integer from = Integer.parseInt(metadata.get("from"));
        Integer to = Integer.parseInt(metadata.get("to"));

        Integer amount = (int) (long) paymentIntent.getAmount();
        Integer applicationFeeAmount = calculateApplicationFeeAmount(amount);
        LocalDateTime createdTime = convertTimestampToLocalDateTime(paymentIntent.getCreated());
        short status = getStatus(paymentIntent.getStatus());

        //set up transaction fields
        Transaction transaction = new Transaction();
        transaction.setId(String.valueOf(UUID.randomUUID()));
        transaction.setType(type);
        transaction.setPayFrom(from);
        transaction.setPayTo(to);
        transaction.setPayAmount(amount);
        transaction.setApplicationFeeAmount(applicationFeeAmount);
        transaction.setPaymentMethod((short) PaymentMethod.CARD.getValue());
        transaction.setThirdPartyTransactionId(paymentIntent.getId());
        transaction.setCreateTime(createdTime);
        transaction.setStatus(status);

        return transaction;
    }

    /**
     * calculate refund amount, refund amount = (total charge - application fee) * refund ratio
     * @param transactionId
     * @return
     */
    private Integer calculateRefundAmount(String transactionId){
        Transaction transaction = transactionMapper.selectByPrimaryKey(transactionId);
        Tutor tutor = tutorService.findByUserId(transaction.getPayTo());
        CancellationPolicy cancellationPolicy = cancellationPolicyService.findByTutorId(tutor.getId());

        Integer amount = transaction.getPayAmount() - transaction.getApplicationFeeAmount();
        amount = Math.round(cancellationPolicy.getRefundRatio() * amount);
        return amount;
    }

    private Integer calculateApplicationFeeAmount(Integer amount){
        return (int) Math.round(amount * applicationFeeRate);
    }

}
