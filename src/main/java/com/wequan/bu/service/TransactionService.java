package com.wequan.bu.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.wequan.bu.controller.vo.Transaction;
import com.wequan.bu.util.TransactionStatus;

/**
 * @author Zhaochao Huang
 */
public interface TransactionService extends Service<Transaction> {
    public void saveAppointmentTransaction(PaymentIntent paymentIntent);

    public void update(PaymentIntent paymentIntent);

    public void delete(PaymentIntent paymentIntent);

    public void deleteTransaction(Integer id, String transactionId) throws StripeException;

    public Transaction findById(String id);

    public void updateStatus(String paymentIntentId, TransactionStatus status);
}
