package com.wequan.bu.service;

import com.stripe.model.PaymentIntent;
import com.wequan.bu.controller.vo.Transaction;

/**
 * @author Zhaochao Huang
 */
public interface TransactionService extends Service<Transaction> {
    public void saveAppointmentTransaction(PaymentIntent paymentIntent);
}
