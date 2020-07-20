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

    @Value("${APPLICATION_FEE_RATE}")
    private Double applicationFeeRate;

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

    @Value("${TRANSFER_WEBHOOK_SECRET}")
    private String transferWebhookSecret;

 //local test webhook secret
/*
    private String paymentIntentWebhookSecret = "whsec_UYCgjzmqTIMbBgZsuI3mxc63mD9YaHdi";
    private String refundWebhookSecret="whsec_UYCgjzmqTIMbBgZsuI3mxc63mD9YaHdi";
    private String accountWebhookSecret = "whsec_UYCgjzmqTIMbBgZsuI3mxc63mD9YaHdi";
    private String transferWebhookSecret = "whsec_UYCgjzmqTIMbBgZsuI3mxc63mD9YaHdi";
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

   @Autowired
   private TutorService tutorService;

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
    public PaymentIntent createPaymentIntent(Integer amount, String destination, Map<String, String> metadata) throws StripeException, Exception{
        PaymentIntentCreateParams.TransferData transferData = PaymentIntentCreateParams.TransferData
                .builder()
                .setDestination(destination)
                .build();

        Long applicationFeeAmount = Math.round(amount * applicationFeeRate);
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long)(int) amount)
                .setCurrency("usd")
                .setApplicationFeeAmount(applicationFeeAmount)
                .addPaymentMethodType("card")
                .putAllMetadata(metadata)
                .setTransferData(transferData)
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return paymentIntent;
    }

    @Override
    public PaymentIntent createPaymentIntent(Integer appointmentId) throws StripeException, Exception {
        Appointment appointment = appointmentMapper.selectByPrimaryKey(appointmentId);

        if(appointment == null) {
            throw new Exception("No such appointment.");
        }

        //check whether there exists a transaction for this appointment
        if(appointment.getTransactionId() != null){
            Transaction transaction = transactionService.findById(appointment.getTransactionId());
            //check whether the transaction require customer to pay out
            if(transaction.getStatus() == TransactionStatus.REQUIRES_PAYMENT_METHOD.getValue()){
                return PaymentIntent.retrieve(transaction.getThirdPartyTransactionId());
            }else{
                throw new Exception("Can't checkout.");
            }
        }

        TutorStripe tutorStripe = tutorStripeMapper.selectByTutorId(appointment.getTutorId());
        Tutor tutor = tutorService.findById(appointment.getTutorId());

        Map<String, String> metadata = new HashMap<>();
        metadata.put("type", String.valueOf(TransactionType.APPOINTMENT.getValue()));
        metadata.put("appointment_id", String.valueOf(appointment.getId()));
        metadata.put("from", String.valueOf(appointment.getUserId()));
        metadata.put("to", String.valueOf(tutor.getUserId()));

        PaymentIntent paymentIntent = createPaymentIntent(appointment.getFee(), tutorStripe.getStripeAccount(), metadata);
        return paymentIntent;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlePaymentIntent(String sigHeader, String webhookEndpoint) throws Exception {
        Event event = null;

        try{
            event = Webhook.constructEvent(webhookEndpoint, sigHeader, paymentIntentWebhookSecret);
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
        }
        if(event != null) {
            if ("payment_intent.succeeded".equals(event.getType())) {
                log.info("=============================> payment intent succeeded event webhook");
                handlePaymentIntentSucceeded(event);
            }
            if("payment_intent.created".equals(event.getType())){
                log.info("=============================> payment intent created event webhook");
                handlePaymentIntentCreated(event);
            }
            if("payment_intent.canceled".equals(event.getType())){
                log.info("=============================> payment intent canceled event webhook");
                handlePaymentIntentCanceled(event);
            }
        }
    }

    private void handlePaymentIntentCreated(Event event) throws Exception {
        PaymentIntent paymentIntent = (PaymentIntent) deserializeObject(event);
        transactionService.saveTransaction(paymentIntent);
    }

    private void handlePaymentIntentSucceeded(Event event) throws Exception {
        PaymentIntent paymentIntent = (PaymentIntent) deserializeObject(event);
        Map<String, String> metadata = paymentIntent.getMetadata();
        Short type = Short.parseShort(metadata.get("type"));

        //update transaction status and transfer id
        transactionService.updateByPaymentIntent(paymentIntent);

        //for public class type transaction, the money is transfered to connected account several days after public class ends
        if(TransactionType.PUBLIC_CLASS.getValue() == type){
            Integer onlineEventId = Integer.parseInt(metadata.get("online_event_id"));
            Integer userId = Integer.parseInt(metadata.get("from"));
            //user join the public class
            onlineEventService.saveOrUpdateOnlineEventMember(onlineEventId, userId, (short) 1);

            onlineEventService.addTransferQuartzJobAndTrigger(paymentIntent);
        }
    }

    private void handlePaymentIntentCanceled(Event event) throws Exception {
        PaymentIntent paymentIntent = (PaymentIntent) deserializeObject(event);
        Map<String, String> metadata = paymentIntent.getMetadata();
        Short type = Short.parseShort(metadata.get("type"));

        if(TransactionType.APPOINTMENT.getValue() == type){
            transactionService.updateStatus(paymentIntent.getId(), TransactionStatus.CANCELED);
            appointmentService.updateStatus(paymentIntent.getId(), AppointmentStatus.CANCELED);
        }
        if(TransactionType.PUBLIC_CLASS.getValue() == type){
            transactionService.updateStatus(paymentIntent.getId(), TransactionStatus.CANCELED);
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
    public PaymentIntent updatePaymentIntent(String transactionId, Integer amount) throws StripeException {
        Transaction transaction = transactionService.findById(transactionId);

        PaymentIntent paymentIntent = PaymentIntent.retrieve(transaction.getThirdPartyTransactionId());
        PaymentIntentUpdateParams params = PaymentIntentUpdateParams.builder()
                .setAmount((long)(int) amount)
                .build();
        PaymentIntent updatedPaymentIntent = paymentIntent.update(params);

        transactionService.updateByPaymentIntent(updatedPaymentIntent);
        return updatedPaymentIntent;
    }


    @Override
    public Refund createRefund(String transactionId, Integer refundAmount) throws StripeException {
        Transaction transaction = transactionService.findById(transactionId);

        RefundCreateParams params = RefundCreateParams.builder()
                .setPaymentIntent(transaction.getThirdPartyTransactionId())
                .setAmount((long) refundAmount)
                .setReason(RefundCreateParams.Reason.REQUESTED_BY_CUSTOMER)
                .setReverseTransfer(true)
                .putMetadata("transaction_id", transactionId)
                .build();

        return Refund.create(params);
    }

    @Override
    public Refund createSeparateRefund(String transactionId, Integer refundAmount) throws StripeException {
        Transaction transaction = transactionService.findById(transactionId);

        RefundCreateParams params = RefundCreateParams.builder()
                .setPaymentIntent(transaction.getThirdPartyTransactionId())
                .setAmount((long) refundAmount)
                .putMetadata("transaction_id", transactionId)
                .build();

        return Refund.create(params);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleRefund(String sigHeader, String webhookEndpoint) throws Exception {
        Event event = null;

        try{
            event = Webhook.constructEvent(webhookEndpoint, sigHeader, refundWebhookSecret);
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
        }
        if(event != null) {
            if ("charge.refunded".equals(event.getType())) {
                log.info("=============================> charge refunded event webhook");
                handleChargeRefunded(event);
            }
        }
    }

    private void handleChargeRefunded(Event event) throws Exception {
        Charge charge = (Charge) deserializeObject(event);
        Map<String, String> metadata = charge.getMetadata();
        Short type = Short.parseShort(metadata.get("type"));

        String paymentIntentId= charge.getPaymentIntent();
        transactionService.updateStatus(paymentIntentId, TransactionStatus.REFUNDED);
        transactionService.addRefundTransactionRecord(charge);

        if(TransactionType.APPOINTMENT.getValue() == type){
            appointmentService.updateStatus(paymentIntentId, AppointmentStatus.REFUNDED);
        }
        if(TransactionType.PUBLIC_CLASS.getValue() == type){
            Integer onlineEventId = Integer.parseInt(metadata.get("online_event_id"));
            Integer userId = Integer.parseInt(metadata.get("from"));
            //user leave the public class
            onlineEventService.saveOrUpdateOnlineEventMember(onlineEventId, userId, (short) -1);
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
    @Transactional(rollbackFor = Exception.class)
    public void handleTransfer(String sigHeader, String payload) throws Exception {
        Event event = null;

        try{
            event = Webhook.constructEvent(payload, sigHeader, transferWebhookSecret);
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
        }

        if(event != null) {
            if ("transfer.created".equals(event.getType())) {
                log.info("=============================> transfer.created event webhook");
                handleTransferCreated(event);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    private void handleTransferCreated(Event event) throws Exception {
        Transfer transfer = (Transfer) deserializeObject(event);
        String chargeId = transfer.getSourceTransaction();
        Charge charge = Charge.retrieve(chargeId);
        String paymentIntentId = charge.getPaymentIntent();

        transactionService.updateTransferIdByThirdPartyTransactionId(paymentIntentId, transfer.getId());
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
    public PaymentIntent createSeparatePaymentIntent(Integer amount, Map<String, String> metadata) throws StripeException{
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long) (int) amount)
                .setCurrency("usd")
                .addPaymentMethodType("card")
                .putAllMetadata(metadata)
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return paymentIntent;
    }

    @Override
    public PaymentIntent createSeparatePaymentIntent(Integer publicClassId, Integer userId) throws StripeException, Exception{
        OnlineEvent onlineEvent = onlineEventService.findById(publicClassId);
        Transaction transaction = transactionService.findByOnlineEventIdAndUserId(publicClassId, userId);

        //check whether there exists a transaction for the user who want to join this public class
        if(transaction != null){
            //check whether the transaction require customer to pay out
            if(transaction.getStatus() == TransactionStatus.REQUIRES_PAYMENT_METHOD.getValue()){
                return PaymentIntent.retrieve(transaction.getThirdPartyTransactionId());
            }else{
                throw new Exception("Can't checkout.");
            }
        }
        //set up metadata which is used when payment_intent.created webhook is triggered
        Map<String, String> metadata = new HashMap<>();
        metadata.put("type", String.valueOf(TransactionType.PUBLIC_CLASS.getValue()));
        metadata.put("online_event_id", String.valueOf(publicClassId));
        metadata.put("from", String.valueOf(userId));
        metadata.put("to", String.valueOf(onlineEvent.getCreateBy()));

        return createSeparatePaymentIntent(onlineEvent.getFee(), metadata);
    }

    @Override
    public void createSeparateTransfer(Long amount, String destination, String chargeId) throws StripeException {
        TransferCreateParams params = TransferCreateParams.builder()
                .setAmount(amount)
                .setCurrency("usd")
                .setDestination(destination)
                .setSourceTransaction(chargeId)
                .build();
        Transfer transfer = Transfer.create(params);
    }

    @Override
    public void createSeparateTransfer(Integer id, String chargeId, Integer userId) throws StripeException, Exception {
        OnlineEvent onlineEvent = onlineEventService.findById(id);
        Long amount = (long)(int)transactionService.findTransactionAmountByOnlineEventIdAndUserId(id, userId);
        if(amount == -1){
            throw new Exception("Can't transfer. The transaction is not confirmed.");
        }

        TutorStripe tutorStripe = tutorStripeMapper.selectByUserId(onlineEvent.getCreateBy());

        System.out.println("transfer amount: "+ amount + " to tutor:" + tutorStripe.getTutorId() + "-----" + LocalDateTime.now().toString());
        createSeparateTransfer(amount, tutorStripe.getStripeAccount(), chargeId);
    }

    @Override
    public void reverseTransfer(String transactionId, Integer amount) throws StripeException {
        Transaction transaction = transactionService.findById(transactionId);
        String transferId = transaction.getTransferId();

        Transfer transfer = Transfer.retrieve(transferId);
        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);

        transfer.getReversals().create(params);
    }


    @Override
    public String retrieveClientSecret(String transactionId) throws Exception {
        Transaction transaction = transactionService.findById(transactionId);

        if(transaction == null) {
            throw new Exception("No such transaction.");
        }

        PaymentIntent paymentIntent = PaymentIntent.retrieve(transaction.getThirdPartyTransactionId());
        return paymentIntent.getClientSecret();
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
