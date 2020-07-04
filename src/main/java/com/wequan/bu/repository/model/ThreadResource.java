package com.wequan.bu.repository.model;

import lombok.Data;

@Data
public class ThreadResource {

    /**
     *主键
     */
    private Integer id;

    /**
     *资源所属类型，1 -> 帖子; 2 -> 回复帖子
     */
    private Short type;

    /**
     *S3链接
     */
    private String links;
}
