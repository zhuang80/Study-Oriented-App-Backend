package com.wequan.bu.repository.model;

import lombok.Data;

import java.sql.Date;

@Data
public class ReportRecord {
    /**
     * primary key
     */
    private Integer id;

    /**
     * user who posts report
     */
    private Integer userId;
    /**
     * 1 for thread, 2 for threadStream(reply), 3 for material
     */
    private Short resourceType;

    /**
     * resource id which is reported
     */
    private Integer resourceId;

    private Date reportTime;

    private String reason;

}
