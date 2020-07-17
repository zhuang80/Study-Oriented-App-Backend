package com.wequan.bu.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhaochao Huang
 */
@Data
public class OnlineEventTransaction {
    private Integer onlineEventId;
    private String transactionId;
    private LocalDateTime createTime;
}
