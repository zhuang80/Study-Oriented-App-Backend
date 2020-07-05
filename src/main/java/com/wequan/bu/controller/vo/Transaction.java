package com.wequan.bu.controller.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Database Table Remarks:
 *   用户交易支付记录
 */
@Data
public class Transaction {
    private String id;
    /**
     * Database Column Remarks:
     *   交易涉及类型，包括0 -> appointment with tutor; 1 -> public class; 2 -> purchase study point
     */
    private Short type;
    private Integer payFrom;
    private Integer payTo;
    private Integer payAmount;
    private Short paymentMethod;
    private String thirdPartyTransactionId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Short status;
    private String toTransactionId;
}