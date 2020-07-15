package com.wequan.bu.repository.model;

import lombok.Data;

import java.util.Date;

/**
 * Database Table Remarks:
 *   帖子对应科目表
 * This class corresponds to the database table bu.wq_thread_subject
 * @author ChrisChen
 */
@Data
public class ThreadSubject {
    /**
     * Database Column Remarks:
     *   帖子id，关联thread(id)
     */
    private Integer threadId;

    /**
     * Database Column Remarks:
     *   科目id，关联subjects(id)
     */
    private Integer subjectId;

    /**
     * Database Column Remarks:
     *   创建时间
     */
    private Date createTime;

}