package com.wequan.bu.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.model.WebhookEndpoint;
import com.wequan.bu.controller.vo.Transaction;
import com.wequan.bu.repository.model.Appointment;
import com.wequan.bu.repository.model.TutorStripe;
import com.wequan.bu.util.TransactionType;

import java.util.Map;

public interface StripeService extends Service<TutorStripe> {
    public void storeConnectedId(String code, Integer tutorId) throws StripeException;

    public PaymentIntent createPaymentIntent(Integer appointmentId) throws StripeException, Exception;

    public String retrieveClientSecret(Integer appointmentId) throws StripeException, Exception;

    public void handlePaymentIntent(String sigHeader, String webhookEndpoint) throws Exception;

    public PaymentIntent cancelPaymentIntent(String paymentIntentId) throws StripeException;

    public PaymentIntent updatePaymentIntent(Integer appointmentId) throws StripeException;

    public Refund createRefund(String transactionId, Integer refundAmount) throws StripeException;

    public void handleRefund(String sigHeader, String webhookEndpoint) throws Exception;

    public String getState();

    public String getUrl(String state);

    public void revoke(Integer tutorId) throws StripeException, Exception;

    public void handleAccount(String sigHeader, String payload) throws Exception;

    public PaymentIntent createSeparatePaymentIntent(Integer amount, String guid, Map<String,String> metadata) throws StripeException;

    public PaymentIntent createSeparatePaymentIntent(Integer publicClassId, Integer userId) throws StripeException;

    public void createSeparateTransfer(String guid, Long amount, String destination) throws StripeException;

    public void createSeparateTransfer(Integer id) throws StripeException;

    public String retrieveClientSecret(String transactionId) throws Exception;
}
