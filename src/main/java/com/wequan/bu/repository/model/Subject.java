package com.wequan.bu.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Database Table Remarks:
 *   科目表，比如数学，化学。
 *   其中类别(category)一栏，1 -> user/tutor使用; 2 -> course使用; 3 -> thread使用
 * @author Zhaochao Huang
 */
@Data
public class Subject {
    private Integer id;
    private String name;
    private Integer createBy;
    private LocalDateTime createTime;
    private Short category;
    private Short schoolId;
}