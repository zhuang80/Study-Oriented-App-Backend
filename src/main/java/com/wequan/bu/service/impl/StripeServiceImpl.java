package com.wequan.bu.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.oauth.TokenResponse;
import com.stripe.net.OAuth;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentIntentUpdateParams;
import com.stripe.param.RefundCreateParams;
import com.stripe.param.TransferCreateParams;
import com.wequan.bu.controller.vo.Transaction;
import com.wequan.bu.repository.dao.AppointmentMapper;
import com.wequan.bu.repository.dao.TutorStripeMapper;
import com.wequan.bu.repository.model.Appointment;
import com.wequan.bu.repository.model.OnlineEvent;
import com.wequan.bu.repository.model.Tutor;
import com.wequan.bu.repository.model.TutorStripe;
import com.wequan.bu.service.*;
import com.wequan.bu.util.AppointmentStatus;
import com.wequan.bu.util.TransactionStatus;
import com.wequan.bu.util.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    @Value("${PAYMENT_INTENT_WEBHOOK_SECRET}")
    private String paymentIntentWebhookSecret;

    @Value("${REFUND_WEBHOOK_SECRET}")
    private String refundWebhookSecret;

    @Value("${ACCOUNT_WEBHOOK_SECRET}")
    private String accountWebhookSecret;

/*  //local test webhook secret
    private String paymentIntentWebhookSecret = "whsec_UYCgjzmqTIMbBgZsuI3mxc63mD9YaHdi";
    private String refundWebhookSecret="whsec_UYCgjzmqTIMbBgZsuI3mxc63mD9YaHdi";
    private String accountWebhookSecret = "whsec_UYCgjzmqTIMbBgZsuI3mxc63mD9YaHdi";
*/
    @Autowired
    private TutorStripeMapper tutorStripeMapper;

   @Autowired
   private TransactionService transactionService;

   @Autowired
   private AppointmentMapper appointmentMapper;

   @Autowired
   private AppointmentService appointmentService;

   @Autowired
   private OnlineEventService onlineEventService;

    @PostConstruct
    public void postConstruct(){
        Stripe.apiKey = secretKey;
        this.setMapper(tutorStripeMapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void storeConnectedId(String code, Integer tutorId) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("code", code);

        TokenResponse stripeResponse = OAuth.token(params, null);
        String connectedAccountId = stripeResponse.getStripeUserId();

        TutorStripe tutorStripe = tutorStripeMapper.selectByTutorId(tutorId);
        if(tutorStripe == null) {
            tutorStripe = new TutorStripe();
            tutorStripe.setTutorId(tutorId);
            tutorStripe.setStripeAccount(connectedAccountId);
            tutorStripe.setCreateTime(LocalDateTime.now());
            tutorStripeMapper.insertSelective(tutorStripe);
        }else{
            tutorStripe.setStripeAccount(connectedAccountId);
            tutorStripeMapper.updateByPrimaryKeySelective(tutorStripe);
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
    public String retrieveClientSecret(Integer appointmentId) throws StripeException, Exception {
        Appointment appointment = appointmentService.findById(appointmentId);
        if(appointment == null) {
            throw new Exception("No such appointment.");
        }
        Transaction transaction = transactionService.findById(appointment.getTransactionId());
        if(transaction == null) {
            throw new Exception("No transaction exists for this appointment.");
        }
        PaymentIntent paymentIntent = PaymentIntent.retrieve(transaction.getThirdPartyTransactionId());

        return paymentIntent.getClientSecret();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlePaymentIntent(String sigHeader, String webhookEndpoint) throws Exception {
        Event event = null;
        PaymentIntent paymentIntent = null;

        try{
            event = Webhook.constructEvent(webhookEndpoint, sigHeader, paymentIntentWebhookSecret);
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
    @Transactional(rollbackFor = Exception.class)
    public PaymentIntent updatePaymentIntent(Integer appointmentId) throws StripeException {
        Appointment appointment = appointmentService.findById(appointmentId);
        Transaction transaction = transactionService.findById(appointment.getTransactionId());

        PaymentIntent paymentIntent = PaymentIntent.retrieve(transaction.getThirdPartyTransactionId());
        PaymentIntentUpdateParams params = PaymentIntentUpdateParams.builder()
                .setAmount((long)(int)appointment.getFee())
                .build();
        PaymentIntent updatedPaymentIntent = paymentIntent.update(params);

        transactionService.update(updatedPaymentIntent);
        return updatedPaymentIntent;
    }

    @Override
    public Refund createRefund(String transactionId, Integer refundAmount) throws StripeException {
        Transaction transaction = transactionService.findById(transactionId);

        RefundCreateParams params = RefundCreateParams.builder()
                .setPaymentIntent(transaction.getThirdPartyTransactionId())
                .setAmount((long) refundAmount)
                .setReason(RefundCreateParams.Reason.REQUESTED_BY_CUSTOMER)
                .setRefundApplicationFee(true)
                .setReverseTransfer(true)
                .putMetadata("transaction_id", transactionId)
                .build();

        return Refund.create(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleRefund(String sigHeader, String webhookEndpoint) throws Exception {
        Event event = null;
        Charge charge = null;
        Transfer transfer = null;

        try{
            event = Webhook.constructEvent(webhookEndpoint, sigHeader, refundWebhookSecret);
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

    @Override
    public String getState() {
        return String.valueOf(UUID.randomUUID());
    }

    @Override
    public String getUrl(String state) {
        String url = "https://connect.stripe.com/express/oauth/authorize" +
                "?client_id=" + clientId +
                "&state=" + state;
        return url;
    }

    @Override
    public void revoke(Integer tutorId) throws StripeException, Exception {
        TutorStripe tutorStripe = tutorStripeMapper.selectByTutorId(tutorId);

        if(tutorStripe != null){
            Map<String, Object> params = new HashMap<>();
            params.put("client_id", clientId);
            params.put("stripe_user_id", tutorStripe.getStripeAccount());
            OAuth.deauthorize(params, null);
        }else{
            throw new Exception("No such Tutor.");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleAccount(String sigHeader, String payload) throws Exception {
        Event event = null;

        try{
            event = Webhook.constructEvent(payload, sigHeader, accountWebhookSecret);
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
        }
        if(event != null) {
            if ("account.application.deauthorized".equals(event.getType())) {
                log.info("=============================> account deauthorized event webhook");
                String connectedAccountId = event.getAccount();

                tutorStripeMapper.deleteByStripeAccount(connectedAccountId);
            }
        }
    }

    @Override
    public PaymentIntent createSeparatePaymentIntent(Integer amount, String guid, Map<String, String> metadata) throws StripeException{
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long) (int) amount)
                .setCurrency("usd")
                .addPaymentMethodType("card")
                .setTransferGroup(guid)
                .putAllMetadata(metadata)
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return paymentIntent;
    }

    @Override
    public PaymentIntent createSeparatePaymentIntent(Integer publicClassId) throws StripeException{
        OnlineEvent onlineEvent = onlineEventService.findById(publicClassId);
        //set up metadata which is used when payment_intent.created webhook is triggered
        Map<String, String> metadata = new HashMap<>();
        metadata.put("type", String.valueOf(TransactionType.PUBLIC_CLASS.getValue()));
        metadata.put("online_event_id", String.valueOf(publicClassId));

        return createSeparatePaymentIntent(onlineEvent.getFee(), onlineEvent.getGuid(), metadata);
    }

    @Override
    public void createSeparateTransfer(String guid, Long amount, String destination) throws StripeException {
        TransferCreateParams params = TransferCreateParams.builder()
                .setAmount(amount)
                .setCurrency("usd")
                .setDestination(destination)
                .setTransferGroup(guid)
                .build();
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
