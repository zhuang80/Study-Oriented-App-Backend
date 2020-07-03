package com.wequan.bu.controller;

import com.stripe.exception.StripeException;
import com.wequan.bu.controller.vo.Transaction;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ChrisChen
 */
@RestController
@Api(tags = "Payment Transaction")
public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/user/{id}/transactions")
    public Result<List<Transaction>> getUserTransactions(@PathVariable("id") Integer userId,
                                                         @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Transaction> transactions = null;
        return ResultGenerator.success(transactions);
    }

    @PostMapping("/user/{id}/transaction/{transaction_id}/cancel")
    @ApiOperation(value = "cancel transaction", notes = "用户在付款前，可以任意取消订单，若付款后，在辅导开始前的12小时，取消订单，返还部分金额, 如果离辅导开始不足12小时，需要提交退款申请")
    public Result deleteTransaction(@PathVariable("id") Integer id,
                                    @PathVariable("transaction_id") String transactionId) throws StripeException {
        try {
            transactionService.cancelTransaction(id, transactionId);
        }catch (Exception e){
            return ResultGenerator.fail(e.getMessage());
        }
        return ResultGenerator.success();
    }
}
