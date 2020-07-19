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

    public void saveTransaction(PaymentIntent paymentIntent);

    public void update(PaymentIntent paymentIntent);

    public void delete(PaymentIntent paymentIntent);

    public void cancelTransactionByUser(Integer userId, String transactionId) throws Exception;

    public void cancelTransactionByTutor(Integer tutorId, String transactionId) throws  Exception;

    public Transaction findById(String id);

    public void updateStatus(String paymentIntentId, TransactionStatus status);

    public void addRefundRecord(Charge charge);

    public List<Transaction> findByUserId(Integer userId, Integer pageNum, Integer pageSize);

    public void refundApply(RefundApplication refundApplication);

    public List<Transaction> findAll(Integer pageNum, Integer pageSize);

    public Integer findTotalTransactionAmountByDiscussionGroupId(Integer id);

}
