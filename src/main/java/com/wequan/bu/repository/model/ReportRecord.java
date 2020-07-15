package com.wequan.bu.repository.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Database Table Remarks:
 *   举报记录表
 * This class corresponds to the database table bu.wq_report_records
 * @author ChrisChen
 */
@Data
@Builder
public class ReportRecord {
    /**
     * Database Column Remarks:
     *   主键
     */
    private Integer id;

    /**
     * Database Column Remarks:
     *   举报人
     */
    private Integer userId;

    /**
     * Database Column Remarks:
     *   被举报资源类型，1 -> thread; 2 -> thread_stream; 3 -> material
     */
    private Short resourceType;

    /**
     * Database Column Remarks:
     *   被举报资源id
     */
    private Integer resourceId;

    /**
     * Database Column Remarks:
     *   举报时间
     */
    private Date reportTime;

    /**
     * Database Column Remarks:
     *   原因
     */
    private String reason;

}
