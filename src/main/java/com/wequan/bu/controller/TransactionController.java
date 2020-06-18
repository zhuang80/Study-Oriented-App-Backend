package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.Transaction;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ChrisChen
 */
@RestController
@Api(tags = "Payment Transaction")
public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    @GetMapping("/user/{id}/transactions")
    public Result<List<Transaction>> getUserTransactions(@PathVariable("id") Integer userId,
                                                         @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Transaction> transactions = null;
        return ResultGenerator.success(transactions);
    }
}
