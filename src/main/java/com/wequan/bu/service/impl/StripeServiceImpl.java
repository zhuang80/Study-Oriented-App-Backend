package com.wequan.bu.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.oauth.TokenResponse;
import com.stripe.net.OAuth;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;
import com.wequan.bu.controller.vo.Transaction;
import com.wequan.bu.repository.dao.AppointmentMapper;
import com.wequan.bu.repository.dao.TutorStripeMapper;
import com.wequan.bu.repository.model.Appointment;
import com.wequan.bu.repository.model.TutorStripe;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.AppointmentService;
import com.wequan.bu.service.StripeService;
import com.wequan.bu.service.TransactionService;
import com.wequan.bu.util.AppointmentStatus;
import com.wequan.bu.util.TransactionStatus;
import com.wequan.bu.util.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zhaochao Huang
 */
@Service
public class StripeServiceImpl extends AbstractService<TutorStripe> implements StripeService {

    private static final Logger log = LoggerFactory.getLogger(StripeServiceImpl.class);

    @Value("${SECRET_KEY}")
    private String secretKey;

    @Value("${CLIENT_ID}")
    private String clientId;

    //@Value("${WEBHOOK_SECRET}")
   // private String webhookSecret;

    //local test webhook secret
    private String webhookSecret = "whsec_UYCgjzmqTIMbBgZsuI3mxc63mD9YaHdi";

    @Autowired
    private TutorStripeMapper tutorStripeMapper;

   @Autowired
   private TransactionService transactionService;

   @Autowired
   private AppointmentMapper appointmentMapper;

   @Autowired
   private AppointmentService appointmentService;

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
        TutorStripe tutorStripe = tutorStripeMapper.selectByTutorId(appointment.getTutorId());

        PaymentIntentCreateParams.TransferData transferData = PaymentIntentCreateParams.TransferData
                .builder()
                .setDestination(tutorStripe.getStripeAccount())
                .build();

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long)(int) appointment.getFee())
                .setCurrency("usd")
                .setApplicationFeeAmount(123L)
                .addPaymentMethodType("card")
                .putMetadata("type", String.valueOf(TransactionType.APPOINTMENT.getValue()))
                .putMetadata("appointment_id", String.valueOf(appointment.getId()))
                .setTransferData(transferData)
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return paymentIntent;
    }

    @Override
    public void handlePaymentIntent(String sigHeader, String webhookEndpoint) throws Exception {
        Event event = null;
        PaymentIntent paymentIntent = null;

        try{
            event = Webhook.constructEvent(webhookEndpoint, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
        }
        if(event != null) {
            if ("payment_intent.succeeded".equals(event.getType())) {
                paymentIntent = (PaymentIntent) deserializeObject(event);
                log.info("=============================> payment intent succeeded event webhook");
                transactionService.update(paymentIntent);
            }
            if("payment_intent.created".equals(event.getType())){
                paymentIntent = (PaymentIntent) deserializeObject(event);
                log.info("=============================> payment intent created event webhook");
                transactionService.saveAppointmentTransaction(paymentIntent);
            }
            if("payment_intent.canceled".equals(event.getType())){
                paymentIntent = (PaymentIntent) deserializeObject(event);
                log.info("=============================> payment intent canceled event webhook");
                transactionService.updateStatus(paymentIntent.getId(), TransactionStatus.CANCELED);
                appointmentService.updateStatus(paymentIntent.getId(), AppointmentStatus.CANCELED);
            }
        }
    }

    @Override
    public PaymentIntent cancelPaymentIntent(String paymentIntentId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        PaymentIntent updatedPaymentIntent = paymentIntent.cancel();
        return updatedPaymentIntent;
    }

    @Override
    public Refund createRefund(String transactionId) throws StripeException {
        Transaction transaction = transactionService.findById(transactionId);
        //hardcode 80% refund rate, need to be changed later
        RefundCreateParams params = RefundCreateParams.builder()
                .setPaymentIntent(transaction.getThirdPartyTransactionId())
                .setAmount(Math.round(0.8 * transaction.getPayAmount()))
                .setReason(RefundCreateParams.Reason.REQUESTED_BY_CUSTOMER)
                .setRefundApplicationFee(true)
                .setReverseTransfer(true)
                .putMetadata("transaction_id", transactionId)
                .build();

        return Refund.create(params);
    }

    @Override
    public void handleRefund(String sigHeader, String webhookEndpoint) throws Exception {
        Event event = null;
        Charge charge = null;
        Transfer transfer = null;

        try{
            event = Webhook.constructEvent(webhookEndpoint, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
        }
        if(event != null) {
            if ("charge.refunded".equals(event.getType())) {
                charge = (Charge) deserializeObject(event);
                log.info("=============================> charge refunded event webhook");
                String paymentIntentId= charge.getPaymentIntent();
                transactionService.updateStatus(paymentIntentId, TransactionStatus.REFUNDED);
                transactionService.addRefundRecord(charge);
                appointmentService.updateStatus(paymentIntentId, AppointmentStatus.REFUNDED);
            }
        }
    }

    private Object deserializeObject(Event event) throws Exception {
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        if (dataObjectDeserializer.getObject().isPresent()) {
            return dataObjectDeserializer.getObject().get();
        } else {
            throw new Exception("fail to deserialize object data from event");
        }
    }
}
