package com.wequan.bu.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhaochao Huang
 */
@Data
public class TutorStripe {
    private Integer id;
    private Integer tutorId;
    private String stripeAccount;
    private LocalDateTime createTime;
    private String email;
    private String type;
}
