package com.wequan.bu.controller.vo;

import lombok.Data;

import java.util.Date;

@Data
public class StudyPointHistory {

    private int id;
    private int userId;
    //+upload doc and approve, +reply thread, purchase
    //-raise thread, download doc
    private String actionLog;
    private int changeAmount;
    private int remainingAmount;
    private Date actionTime;
    private String transactionId;

}
