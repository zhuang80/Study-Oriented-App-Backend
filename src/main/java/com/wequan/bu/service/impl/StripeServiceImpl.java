package com.wequan.bu.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.oauth.TokenResponse;
import com.stripe.net.OAuth;
import com.wequan.bu.repository.dao.TutorStripeMapper;
import com.wequan.bu.repository.model.TutorStripe;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private String secretKey = "sk_test_51GvSqhEWcWYP1PyNkbOqe9ccNkeR1Fwyqra7tCvsgwY9H8pNvcSpNoqxwgirFsHfD96LRLiRI9k9Gylb3O7Qx6se009LZHlhhm";
    private String clientId = "ca_HUSn3TlzUSpqzLeK4JHl3EIh6BKjVFeM";

    @Autowired
    private TutorStripeMapper tutorStripeMapper;

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
}
