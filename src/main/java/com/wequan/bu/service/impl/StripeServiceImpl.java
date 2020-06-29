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
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${SECRET_KEY}")
    private String secretKey;

    @Value("${CLIENT_ID}")
    private String clientId;

    //@Value("${WEBHOOK_SECRET}")
    //private String webhookSecret;

    //local test webhook secret
    private String webhookSecret = "whsec_UYCgjzmqTIMbBgZsuI3mxc63mD9YaHdi";

    @Autowired
    private TutorStripeMapper tutorStripeMapper;

   @Autowired
   private TransactionService transactionService;

   @Autowired
   private AppointmentMapper appointmentMapper;

    @PostConstruct
    public void postConstruct(){
        System.out.println("==================="+ secretKey);
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
                .putMetadata("type", String.valueOf(TransactionType.APPOINTMENT.getValue()))
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
        System.out.println("================================================");
        System.out.println(sigHeader);
        System.out.println(webhookEndpoint);

        Event event = null;
        PaymentIntent paymentIntent = null;

        try{
            event = Webhook.constructEvent(webhookEndpoint, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
        }

        if ("payment_intent.succeeded".equals(event.getType())) {
            System.out.println("=============================> payment intent succeeded event webhook");
            // Deserialize the nested object inside the event
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            if (dataObjectDeserializer.getObject().isPresent()) {
                paymentIntent = (PaymentIntent) dataObjectDeserializer.getObject().get();
                transactionService.update(paymentIntent);
            } else {
                // Deserialization failed, probably due to an API version mismatch.
                // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
                // instructions on how to handle this case, or return an error here.
            }
        }
        if("payment_intent.created".equals(event.getType())){
            System.out.println("=============================> payment intent created event webhook");
            // Deserialize the nested object inside the event
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            if (dataObjectDeserializer.getObject().isPresent()) {
                paymentIntent = (PaymentIntent) dataObjectDeserializer.getObject().get();
                transactionService.saveAppointmentTransaction(paymentIntent);
            }
        }
    }
}
