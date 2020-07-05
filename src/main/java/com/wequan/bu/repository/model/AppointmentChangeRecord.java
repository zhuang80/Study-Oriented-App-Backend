package com.wequan.bu.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhaochao Huang
 */
@Data
public class AppointmentChangeRecord {
    private Integer id;
    private Integer appointmentId;
    private Integer userId;
    private Boolean isTutor;
    /**
     * Database Column Remarks:
     *   1 -> cancel before payment;
     *   2 -> cancel after payment and before appointment start;
     *   3 -> cancel after appointment start - need admin approve;
     *
     */
    private Short changeType;
    private String changeReason;
    private Integer refundAmount;
    private LocalDateTime createTime;
    /**
     * Database Column Remarks:
     *   1 -> approve; -1 -> reject
     * @mbg.generated
     */
    private Short adminAction;
    private String adminComment;
    private LocalDateTime updateTime;
    private String transactionId;

}