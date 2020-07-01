package com.wequan.bu.controller;

import com.nimbusds.oauth2.sdk.auth.ClientSecretPost;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.WebhookEndpoint;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.Appointment;
import com.wequan.bu.service.StripeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value="return client secret", notes="根据appointment生成一个client secret 前端使用client secret完成交易")
    public Result<String> getClientSecret(@RequestParam("appointment_id") Integer appointmentId) throws StripeException {
        PaymentIntent paymentIntent = stripeService.createPaymentIntent(appointmentId);
        return ResultGenerator.success(paymentIntent.getClientSecret());
    }

    @PostMapping("/webhook")
    public Result handlePaymentIntent(HttpServletRequest request,
                                  @RequestBody String webhookEndpoint) throws Exception {
        System.out.println("============================ enter webhook");
        System.out.println(webhookEndpoint);
        System.out.println(request.getHeader("Stripe-Signature"));
        stripeService.handlePaymentIntent(request.getHeader("Stripe-Signature"), webhookEndpoint);
        return ResultGenerator.success();
    }

    @PostMapping("/refund_webhook")
    public Result handleRefund(HttpServletRequest request,
                                      @RequestBody String webhookEndpoint) throws Exception {
        System.out.println("============================ enter webhook");
        System.out.println(webhookEndpoint);
        System.out.println(request.getHeader("Stripe-Signature"));
        stripeService.handleRefund(request.getHeader("Stripe-Signature"), webhookEndpoint);
        return ResultGenerator.success();
    }
}
