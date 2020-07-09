package com.wequan.bu.repository.model;

import lombok.Data;

/**
 * @author ChrisChen
 */
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
    private String link;

    /**
     *同S3链接相关的文件类型
     */
    private String fileType;

    /**
     *同S3链接相关的文件名
     */
    private String fileName;

    /**
     * 资源所属thread/stream的id
     */
    private Integer belongId;
}
