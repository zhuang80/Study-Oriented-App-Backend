package com.wequan.bu.repository.model;

import lombok.Data;

import java.util.Date;

/**
 * Database Table Remarks:
 *   用于存储申请Tutor时的上传资料信息（每种类型的资料允许多个）
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table bu.wq_tutor_application_file
 *
 * @mbg.generated do_not_delete_during_merge
 */
@Data
public class TutorApplicationFile {
    /**
     * Database Column Remarks:
     *   主键，uuid
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bu.wq_tutor_application_file.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     * Database Column Remarks:
     *   上传资料类型，1 -> resume; 2 -> transcript; 3 -> other_proof
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bu.wq_tutor_application_file.type
     *
     * @mbg.generated
     */
    private Short type;

    /**
     * Database Column Remarks:
     *   文件存储路径，AWS S3链接
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bu.wq_tutor_application_file.store_path
     *
     * @mbg.generated
     */
    private String storePath;

    /**
     * Database Column Remarks:
     *   文件类型
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bu.wq_tutor_application_file.file_type
     *
     * @mbg.generated
     */
    private String fileType;

    /**
     * Database Column Remarks:
     *   文件名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bu.wq_tutor_application_file.file_name
     *
     * @mbg.generated
     */
    private String fileName;

    /**
     * Database Column Remarks:
     *   上传者，关联user_profile(id)
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bu.wq_tutor_application_file.upload_by
     *
     * @mbg.generated
     */
    private Integer uploadBy;

    /**
     * Database Column Remarks:
     *   上传时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bu.wq_tutor_application_file.upload_time
     *
     * @mbg.generated
     */
    private Date uploadTime;

    /**
     * Database Column Remarks:
     *   文件来源种类，1 -> google drive; 2 -> dropbox; 3 -> onedrive; 4 -> local storage
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bu.wq_tutor_application_file.source_from
     *
     * @mbg.generated
     */
    private Short sourceFrom;

    /**
     * Database Column Remarks:
     *   文件外链，对应source_from
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bu.wq_tutor_application_file.source_external_link
     *
     * @mbg.generated
     */
    private String sourceExternalLink;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu.wq_tutor_application_file
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TutorApplicationFile other = (TutorApplicationFile) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getStorePath() == null ? other.getStorePath() == null : this.getStorePath().equals(other.getStorePath()))
            && (this.getFileType() == null ? other.getFileType() == null : this.getFileType().equals(other.getFileType()))
            && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
            && (this.getUploadBy() == null ? other.getUploadBy() == null : this.getUploadBy().equals(other.getUploadBy()))
            && (this.getUploadTime() == null ? other.getUploadTime() == null : this.getUploadTime().equals(other.getUploadTime()))
            && (this.getSourceFrom() == null ? other.getSourceFrom() == null : this.getSourceFrom().equals(other.getSourceFrom()))
            && (this.getSourceExternalLink() == null ? other.getSourceExternalLink() == null : this.getSourceExternalLink().equals(other.getSourceExternalLink()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu.wq_tutor_application_file
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getStorePath() == null) ? 0 : getStorePath().hashCode());
        result = prime * result + ((getFileType() == null) ? 0 : getFileType().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getUploadBy() == null) ? 0 : getUploadBy().hashCode());
        result = prime * result + ((getUploadTime() == null) ? 0 : getUploadTime().hashCode());
        result = prime * result + ((getSourceFrom() == null) ? 0 : getSourceFrom().hashCode());
        result = prime * result + ((getSourceExternalLink() == null) ? 0 : getSourceExternalLink().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu.wq_tutor_application_file
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", storePath=").append(storePath);
        sb.append(", fileType=").append(fileType);
        sb.append(", fileName=").append(fileName);
        sb.append(", uploadBy=").append(uploadBy);
        sb.append(", uploadTime=").append(uploadTime);
        sb.append(", sourceFrom=").append(sourceFrom);
        sb.append(", sourceExternalLink=").append(sourceExternalLink);
        sb.append("]");
        return sb.toString();
    }
}