package com.wequan.bu.controller.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Data
public class SubjectGroup<T> {
    private Integer id;
    private String subjectName;
    private T data;
}
