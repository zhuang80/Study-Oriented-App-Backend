package com.wequan.bu.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhaochao Huang
 */
@Data
public class OnlineEventMember {
    private Integer id;
    private Integer onlineEventId;
    private Integer memberId;
    private Short action;
    private LocalDateTime actionTime;
}
