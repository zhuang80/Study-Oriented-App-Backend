package com.wequan.bu.repository.model;

import lombok.Data;

import java.util.Date;

/**
 * Database Table Remarks:
 *   与学科对应的topic表（一学科对多topic），比如数学下有微积分，线性代数等
 * @author Zhaochao Huang
 */
@Data
public class Topic {
    private Integer id;
    private String name;
    private Integer subjectId;
    private Integer createBy;
    private Date createTime;
}