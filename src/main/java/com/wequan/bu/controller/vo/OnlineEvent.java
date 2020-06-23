package com.wequan.bu.controller.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author ChrisChen
 */
@Data
public class OnlineEvent extends BaseVo {

    private String name;
    private short type;
    private String briefDescription;
    private double fee;
    private short status;
    private String method;
    private String methodDetail;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private byte[] logo;
    private byte[] image;
    private boolean visible;
    private short belongSchoolId;
    private String guid;
    private int numberOfMember;
    private Integer tagId;

}
