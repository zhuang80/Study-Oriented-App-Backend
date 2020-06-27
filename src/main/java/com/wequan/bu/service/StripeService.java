package com.wequan.bu.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.WebhookEndpoint;
import com.wequan.bu.repository.model.TutorStripe;

public interface StripeService extends Service<TutorStripe> {
    public void storeConnectedId(String code, Integer tutorId);

    public PaymentIntent createPaymentIntent(Integer tutorId) throws StripeException;

    public void fulfillPurchase(String sigHeader, String webhookEndpoint);
}
