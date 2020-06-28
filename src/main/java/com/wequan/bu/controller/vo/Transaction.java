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

    private String uuid;

    /**
     * Database Column Remarks:
     *   交易涉及类型，包括0 -> appointment with tutor; 1 -> public class; 2 -> purchase study point
     */
    private Short type;

    /**
     * Database Column Remarks:
     *   交易对手方，关联user_profile(id)
     */
    private Integer payFrom;

    /**
     * Database Column Remarks:
     *   交易对手方，关联user_profile(id)
     */
    private Integer payTo;
    private Integer payAmount;

    /**
     * Database Column Remarks:
     *   支付方式
     */
    private Short paymentMethod;

    /**
     * Database Column Remarks:
     *   第三方交易号
     */
    private String thirdPartyTransactionId;
    private LocalDateTime createTime;

    /**
     * Database Column Remarks:
     *   1 -> success; -1 -> fail
     */
    private Short status;
}