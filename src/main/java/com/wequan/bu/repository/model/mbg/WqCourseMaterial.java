package com.wequan.bu.repository.model.mbg;

import java.util.Date;

public class WqCourseMaterial {
    private Integer id;

    private Short type;

    private String storeDirectory;

    private String fileName;

    private String sourceFrom;

    private Integer subjectId;

    private Integer courseId;

    private Integer professorId;

    private Short studyPointsRequired;

    private String term;

    private String fileType;

    private Integer uploadBy;

    private Date uploadTime;

    private String sourceExternalLink;

    private Short totalPage;

    private Integer likes;

    private Short status;

    private Integer checker;

    private String comment;

    private Date checkTime;

    private String uuid;

    private byte[] previewPages;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getStoreDirectory() {
        return storeDirectory;
    }

    public void setStoreDirectory(String storeDirectory) {
        this.storeDirectory = storeDirectory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Integer professorId) {
        this.professorId = professorId;
    }

    public Short getStudyPointsRequired() {
        return studyPointsRequired;
    }

    public void setStudyPointsRequired(Short studyPointsRequired) {
        this.studyPointsRequired = studyPointsRequired;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(Integer uploadBy) {
        this.uploadBy = uploadBy;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getSourceExternalLink() {
        return sourceExternalLink;
    }

    public void setSourceExternalLink(String sourceExternalLink) {
        this.sourceExternalLink = sourceExternalLink;
    }

    public Short getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Short totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getChecker() {
        return checker;
    }

    public void setChecker(Integer checker) {
        this.checker = checker;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public byte[] getPreviewPages() {
        return previewPages;
    }

    public void setPreviewPages(byte[] previewPages) {
        this.previewPages = previewPages;
    }
}