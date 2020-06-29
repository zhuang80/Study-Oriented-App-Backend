package com.wequan.bu.repository.dao;

import com.wequan.bu.controller.vo.Transaction;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Zhaochao Huang
 */
@Mapper
public interface TransactionMapper extends GeneralMapper<Transaction> {

    public void updateByThirdPartyTransactionId(Transaction transaction);
}
