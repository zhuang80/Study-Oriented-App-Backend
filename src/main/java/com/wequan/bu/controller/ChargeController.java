package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.service.StripeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
