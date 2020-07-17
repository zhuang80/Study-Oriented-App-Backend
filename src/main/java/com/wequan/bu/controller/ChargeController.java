package com.wequan.bu.controller;

import com.nimbusds.oauth2.sdk.auth.ClientSecretPost;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.WebhookEndpoint;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.Appointment;
import com.wequan.bu.service.StripeService;
import com.wequan.bu.util.TransactionType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Zhaochao Huang
 */
@RestController
@Api(tags = "Charge")
public class ChargeController {

    @Autowired
    private StripeService stripeService;

    @GetMapping("/connect/oauth")
    public Result connect(HttpServletRequest request){
        String code = request.getParameter("code");
        String state = request.getParameter("state");

        HttpSession session = request.getSession(false);
        if(session == null || code == null || state == null) {
            return ResultGenerator.fail("Fail to connect.");
        }

        if(!state.equals(session.getAttribute("state"))){
            return ResultGenerator.fail("Fail to connect. State doesn't match.");
        }
        Integer tutorId= Integer.parseInt((String) session.getAttribute("tutor_id"));

        try {
            stripeService.storeConnectedId(code, tutorId);
            return ResultGenerator.success();
        }catch (StripeException e){
            return ResultGenerator.fail(e.getMessage());
        }
    }

    @GetMapping("/client_secret")
    @ApiOperation(value="return client secret", notes="根据appointment生成一个client secret 前端使用client secret完成交易")
    public Result<String> getClientSecret(@RequestParam("appointment_id") Integer appointmentId){
        try {
            PaymentIntent paymentIntent = stripeService.createPaymentIntent(appointmentId);
            return ResultGenerator.success(paymentIntent.getClientSecret());
        }catch (StripeException e){
            return ResultGenerator.fail(e.getMessage());
        }catch (Exception e){
            return ResultGenerator.fail(e.getMessage());
        }

    }

    @GetMapping("/client_secret/retrieve")
    @ApiOperation(value="retrieve client secret by appointment id", notes="根据appointment id来取回client secret")
    public Result<String> retrieveClientSecret(@RequestParam("appointment_id") Integer appointmentId){
        try{
            return ResultGenerator.success(stripeService.retrieveClientSecret(appointmentId));
        } catch (StripeException e) {
            return ResultGenerator.fail(e.getMessage());
        } catch (Exception e) {
            return ResultGenerator.fail(e.getMessage());
        }
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

    @PostMapping("/account_webhook")
    public Result handleAccount(HttpServletRequest request,
                                @RequestBody String payload){
        try{
            stripeService.handleAccount(request.getHeader("Stripe-Signature"), payload);
        }catch(Exception e){
            return ResultGenerator.fail(e.getMessage());
        }
        return ResultGenerator.success();

    }

    @GetMapping("/connect")
    @ApiOperation(value = "redirect to stripe sign up page", notes = "生成state，放入session，然后重定向到stripe的注册页面")
    public void getStripeConnectPage(HttpServletRequest request, HttpServletResponse response){
        String tutorId  = request.getParameter("tutor_id");
        System.out.println("=============================> " + tutorId);
        String state = stripeService.getState();
        String url = stripeService.getUrl(state);

        HttpSession session = request.getSession();
        session.setAttribute("tutor_id", tutorId);
        session.setAttribute("state", state);

        System.out.println("=========================================> " + session.getId());
        response.setHeader("Location", url);
        response.setStatus(302);
    }

    @GetMapping("/revoke")
    @ApiOperation(value = "revoke the account's access", notes = "撤销Tutor的Stripe账号绑定")
    public Result revoke(@RequestParam("tutor_id") Integer tutorId){
        try{
            stripeService.revoke(tutorId);
            return ResultGenerator.success();
        }catch (StripeException e){
            return ResultGenerator.fail(e.getMessage());
        }catch (Exception e){
            return ResultGenerator.fail(e.getMessage());
        }
    }

    @GetMapping("/client_secret/public_class")
    @ApiOperation(value="return client secret", notes="根据public class id生成一个client secret 前端使用client secret完成交易")
    public Result<String> getClientSecretForPublicClass(@RequestParam("public_class_id") Integer publicClassId,
                                                        @RequestParam("user_id") Integer userId){
        try {
            PaymentIntent paymentIntent = stripeService.createSeparatePaymentIntent(publicClassId, userId);
            return ResultGenerator.success(paymentIntent.getClientSecret());
        }catch (StripeException e){
            return ResultGenerator.fail(e.getMessage());
        }
    }

    @GetMapping("/transaction/{id}/client_secret/retrieve")
    @ApiOperation(value = "retrieve client secret for a transaction", notes = "根据transaction id 取回该交易的client secret")
    public Result<String> retrieveClientSecretByTransactionId(@PathVariable("id") String id){
        try{
            return ResultGenerator.success(stripeService.retrieveClientSecret(id));
        } catch (StripeException e) {
            return ResultGenerator.fail(e.getMessage());
        } catch (Exception e) {
            return ResultGenerator.fail(e.getMessage());
        }
    }
}
