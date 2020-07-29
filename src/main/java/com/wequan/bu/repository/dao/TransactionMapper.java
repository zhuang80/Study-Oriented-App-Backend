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

    public List<Transaction> selectByUserId(@Param("user_id") Integer userId, @Param("status") Short status);

    public Integer selectTotalTransactionAmountByOnlineEventId(@Param("discussion_group_id") Integer discussionGroupId,
                                                                @Param("status") Short status);

    public Transaction selectByOnlineEventIdAndUserId(@Param("online_event_id") Integer onlineEventId,
                                                      @Param("user_id") Integer userId);

    public Transaction selectRefundTransactionByToTransactionId(@Param("to_transaction_id") String toTransactionId);
}
