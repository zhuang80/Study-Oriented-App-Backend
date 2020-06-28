package com.wequan.bu.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.WebhookEndpoint;
import com.stripe.model.oauth.TokenResponse;
import com.stripe.net.OAuth;
import com.stripe.net.RequestOptions;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import com.wequan.bu.controller.vo.Transaction;
import com.wequan.bu.repository.dao.AppointmentMapper;
import com.wequan.bu.repository.dao.TransactionMapper;
import com.wequan.bu.repository.dao.TutorStripeMapper;
import com.wequan.bu.repository.model.Appointment;
import com.wequan.bu.repository.model.TutorStripe;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.StripeService;
import com.wequan.bu.service.TransactionService;
import com.wequan.bu.util.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zhaochao Huang
 */
@Service
public class StripeServiceImpl extends AbstractService<TutorStripe> implements StripeService {
    private String secretKey = "sk_test_51GvSqhEWcWYP1PyNkbOqe9ccNkeR1Fwyqra7tCvsgwY9H8pNvcSpNoqxwgirFsHfD96LRLiRI9k9Gylb3O7Qx6se009LZHlhhm";
    private String clientId = "ca_HUSn3TlzUSpqzLeK4JHl3EIh6BKjVFeM";
    private String webhookSecret = "whsec_8W0LB8PW1hKslvsdgG4zIp9WQC5q36fg";

    @Autowired
    private TutorStripeMapper tutorStripeMapper;

   @Autowired
   private TransactionService transactionService;

   @Autowired
   private AppointmentMapper appointmentMapper;

    @PostConstruct
    public void postConstruct(){
        Stripe.apiKey = secretKey;
        this.setMapper(tutorStripeMapper);
    }

    @Override
    public void storeConnectedId(String code, Integer tutorId) {
        Map<String, Object> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("code", code);
        try{
            TokenResponse stripeResponse = OAuth.token(params, null);
            String connectedAccountId = stripeResponse.getStripeUserId();
            TutorStripe tutorStripe = new TutorStripe();
            tutorStripe.setTutorId(tutorId);
            tutorStripe.setStripeAccount(connectedAccountId);
            tutorStripe.setCreateTime(LocalDateTime.now());
            tutorStripeMapper.insertSelective(tutorStripe);
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PaymentIntent createPaymentIntent(Integer appointmentId) throws StripeException {
        Appointment appointment = appointmentMapper.selectByPrimaryKey(appointmentId);
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long)(int) appointment.getFee())
                .setCurrency("usd")
                .setApplicationFeeAmount(123L)
                .addPaymentMethodType("card")
                .putMetadata("appointment_id", String.valueOf(appointment.getId()))
                .build();

        TutorStripe tutorStripe = tutorStripeMapper.selectByTutorId(appointment.getTutorId());

        RequestOptions requestOptions = RequestOptions.builder()
                 .setStripeAccount(tutorStripe.getStripeAccount())
                 .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params, requestOptions);

        return paymentIntent;
    }

    @Override
    public void fulfillPurchase(String sigHeader, String webhookEndpoint) {
        Event event = null;

        try{
            event = Webhook.constructEvent(webhookEndpoint, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
        }

        if ("payment_intent.succeeded".equals(event.getType())) {
            // Deserialize the nested object inside the event
            System.out.println("==================== payment intent succeed");
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            PaymentIntent paymentIntent = null;
            if (dataObjectDeserializer.getObject().isPresent()) {
                paymentIntent = (PaymentIntent) dataObjectDeserializer.getObject().get();
                String connectedAccountId = event.getAccount();
                Map<String, String> metadata = paymentIntent.getMetadata();
               // if(metadata.containsKey("appointment_id")){
                    System.out.println("=================== save transaction ");
                    transactionService.saveAppointmentTransaction(paymentIntent);
              //  }
               // handleSuccessfulPaymentIntent(connectedAccountId, paymentIntent);
            } else {
                // Deserialization failed, probably due to an API version mismatch.
                // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
                // instructions on how to handle this case, or return an error here.
            }
        }
    }
}
