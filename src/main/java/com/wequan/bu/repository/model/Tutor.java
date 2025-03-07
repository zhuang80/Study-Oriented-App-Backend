package com.wequan.bu.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhen Lin
 */
@Data
@JsonIgnoreProperties("handler")
public class Tutor {

    private Integer id;
    private User user;
    private Integer userId;
    private String briefIntroduction;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Short status;
    private Short latePolicyId;
    private Short cancellationPolicyId;
    private Boolean tutorAvailable;
    private Integer tutorApplicationId;
    private String currentCity;
    private String currentState;
    private String teachMethod;
    private Integer hourlyRate;
}
