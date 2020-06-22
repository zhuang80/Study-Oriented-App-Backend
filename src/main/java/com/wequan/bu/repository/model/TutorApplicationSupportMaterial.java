package com.wequan.bu.repository.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Database Table Remarks:
 *   用于存储申请Tutor时的上传资料信息（每种类型的资料允许多个）
 * @author Zhaochao Huang
 */
@Data
public class TutorApplicationSupportMaterial {
    private Integer id;
    /**
     * Database Column Remarks:
     *   上传资料类型，1 -> resume; 2 -> transcript; 3 -> other_proof
     */
    private Short type;
    /**
     * Database Column Remarks:
     *   文件存储路径，AWS S3链接
     */
    private String storePath;
    private String fileType;
    private String fileName;
    private Integer uploadBy;
    private LocalDateTime uploadTime;
    private String uuid;
    /**
     * Database Column Remarks:
     *   文件来源种类，1 -> google drive; 2 -> dropbox; 3 -> onedrive; 4 -> local storage
     */
    private Short sourceFrom;
    /**
     * Database Column Remarks:
     *   文件外链，对应source_from
     */
    private String sourceExternalLink;
}