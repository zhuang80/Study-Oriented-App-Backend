package com.wequan.bu.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhaochao Huang
 */
@Data
public class Tag {
    private Short id;
    private String name;
    /**
     * Database Column Remarks:
     *   标签类别
     *   1, online event
     *   2, discussion group
     *   3, thread
     *   4, professor
     *   5, tutor review
     */
    private Short category;
    private Integer createBy;
    private LocalDateTime createTime;

}
