package com.wequan.bu.controller;

import com.nimbusds.oauth2.sdk.auth.ClientSecretPost;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.WebhookEndpoint;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.service.StripeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Zhaochao Huang
 */
@RestController
@Api(tags = "Charge")
public class ChargeController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/connect/oauth")
    public Result connect(@RequestParam("code") String code,
                          @RequestParam("tutorId") Integer tutorId){
        stripeService.storeConnectedId(code, tutorId);
        return ResultGenerator.success();
    }

    @GetMapping("/client_secret")
    public Result<String> getClientSecret(@RequestParam("tutorId") Integer tutorId) throws StripeException {
        PaymentIntent paymentIntent = stripeService.createPaymentIntent(tutorId);
        return ResultGenerator.success(paymentIntent.getClientSecret());
    }

    @PostMapping("/webhook")
    public Result fulfillPurchase(HttpServletRequest request,
                                  @RequestBody String webhookEndpoint){
        stripeService.fulfillPurchase(request.getHeader("Stripe-Signature"), webhookEndpoint);
        return ResultGenerator.success();
    }
}
