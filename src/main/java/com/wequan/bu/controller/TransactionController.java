package com.wequan.bu.controller;

import com.stripe.exception.StripeException;
import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.RefundApplication;
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

    @Autowired
    private MessageHandler messageHandler;

    @GetMapping("/user/{id}/transactions")
    @ApiOperation(value = "get transactions for user", notes ="用户取回交易信息,用户为付款方，可以设置status, 0 表示代付款， 1表示已付款，2表示已退款")
    public Result<List<Transaction>> getUserTransactions(@PathVariable("id") Integer userId,
                                                         @RequestParam(value = "status", required = false) Short status,
                                                         @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if( userId < 0 ){
            String message = messageHandler.getFailResponseMessage("40008");
            return ResultGenerator.fail(message);
        }
        List<Transaction> transactions = transactionService.findByUserId(userId, status, pageNum, pageSize);
        return ResultGenerator.success(transactions);
    }

    @GetMapping("/tutor/{id}/transactions")
    @ApiOperation(value = "get transactions for tutor", notes ="Tutor取回交易信息,Tutor为收款方，可以设置status, 0 表示代付款， 1表示已付款，2表示已退款")
    public Result<List<Transaction>> getTutorTransactions(@PathVariable("id") Integer tutorId,
                                                         @RequestParam(value = "status", required = false) Short status,
                                                         @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if( tutorId < 0 ){
            String message = messageHandler.getFailResponseMessage("40008");
            return ResultGenerator.fail(message);
        }
        try{
            List<Transaction> transactions = transactionService.findByTutorId(tutorId, status, pageNum, pageSize);
            return ResultGenerator.success(transactions);
        }catch (Exception e) {
            return ResultGenerator.fail(e.getMessage());
        }
    }

    @PostMapping("/user/{id}/transaction/{transaction_id}/cancel")
    @ApiOperation(value = "cancel transaction", notes = "用户在付款前，可以任意取消订单，若付款后，在辅导开始前，取消订单，返还部分金额, 如果离辅导开始后，需要提交退款申请")
    public Result deleteTransactionyByUser(@PathVariable("id") Integer id,
                                    @PathVariable("transaction_id") String transactionId) throws StripeException {
        try {
            transactionService.cancelTransactionByUser(id, transactionId);
        }catch (Exception e){
            return ResultGenerator.fail(e.getMessage());
        }
        return ResultGenerator.success();
    }

    @PostMapping("/tutor/{id}/transaction/{transaction_id}/cancel")
    @ApiOperation(value = "cancel transaction", notes = "在用户付款前，tutor可以任意取消订单，如果付款后，tutor取消订单，需要退还全款")
    public Result deleteTransactionByTutor(@PathVariable("id") Integer id,
                                    @PathVariable("transaction_id") String transactionId) throws StripeException {
        try {
            transactionService.cancelTransactionByTutor(id, transactionId);
        }catch (Exception e){
            return ResultGenerator.fail(e.getMessage());
        }
        return ResultGenerator.success();
    }

    @PostMapping("/user/{id}/transaction/{transaction_id}/refund_apply")
    @ApiOperation(value = "apply for refund", notes = "当辅导开始后，user想要退款，user需要提交退款申请，填写退款理由，如tutor没有出席，tutor教学质量差，然后交由管理员审核")
    public Result refundApply(@PathVariable("id") Integer id,
                              @PathVariable("transaction_id") String transactionId,
                              @RequestBody RefundApplication refundApplication){
        transactionService.refundApply(refundApplication);
        return ResultGenerator.success();
    }

    @GetMapping("/transactions")
    @ApiOperation(value = "get all transactions", notes = "返回所有的交易记录, 服务于管理员")
    public Result<List<Transaction>> getAllTransactions( @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize){
        return ResultGenerator.success(transactionService.findAll(pageNum, pageSize));
    }

    @GetMapping("/transaction/{id}/refund_record")
    @ApiOperation(value ="return refund record of transaction", notes = "返回某交易的退款信息")
    public Result<Transaction> getRefundRecord(@PathVariable("id") String id){
        return ResultGenerator.success(transactionService.findTransactionByToTransactionId(id));
    }
}
