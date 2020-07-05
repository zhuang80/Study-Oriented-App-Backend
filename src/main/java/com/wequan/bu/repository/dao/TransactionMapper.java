package com.wequan.bu.repository.dao;

import com.wequan.bu.controller.vo.Transaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface TransactionMapper extends GeneralMapper<Transaction> {

    public void updateByThirdPartyTransactionId(Transaction transaction);

    public void deleteByThirdPartyTransactionId(@Param("payment_intent_id") String paymentIntentId);

    public Transaction selectByThirdPartyTransactionId(@Param("third_party_transaction_id") String thirdPartyTransactionId);

    public List<Transaction> selectByUserId(@Param("user_id") Integer userId);
}
