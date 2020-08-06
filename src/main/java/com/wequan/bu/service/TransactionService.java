package com.wequan.bu.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.wequan.bu.controller.vo.RefundApplication;
import com.wequan.bu.controller.vo.Transaction;
import com.wequan.bu.util.TransactionStatus;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
public interface TransactionService extends Service<Transaction> {
    public void saveAppointmentTransaction(PaymentIntent paymentIntent);

    public void savePublicClassTransaction(PaymentIntent paymentIntent);

    public void saveTransaction(PaymentIntent paymentIntent);

    public void updateByPaymentIntent(PaymentIntent paymentIntent);

    public void delete(PaymentIntent paymentIntent);

    public void cancelTransactionByUser(Integer userId, String transactionId) throws Exception;

    public void cancelTransactionByTutor(Integer tutorId, String transactionId) throws  Exception;

    public Transaction findById(String id);

    public void updateStatus(String paymentIntentId, TransactionStatus status);

    public void addRefundTransactionRecord(Charge charge);

    public List<Transaction> findByUserId(Integer userId, Short status, Integer pageNum, Integer pageSize);

    public List<Transaction> findByTutorId(Integer tutorId, Short status, Integer pageNum, Integer pageSize) throws Exception;

    public void refundApply(RefundApplication refundApplication);

    public List<Transaction> findAll(Integer pageNum, Integer pageSize);

    public Integer findTotalTransactionAmountByOnlineEventId(Integer id);

    public Integer findTransactionAmountByOnlineEventIdAndUserId(Integer onlineEventId, Integer userId);

    public Transaction findByOnlineEventIdAndUserId(Integer publicClassId, Integer userId);

    public Transaction findByThirdPartyTransactionId(String paymentIntentId);

    public void updateTransferIdByThirdPartyTransactionId(String paymentIntentId, String transferId);

    public Transaction findTransactionByToTransactionId(String id);

    public Transaction createStudyPointTransaction(Integer userId, Integer amount) throws StripeException;

}
