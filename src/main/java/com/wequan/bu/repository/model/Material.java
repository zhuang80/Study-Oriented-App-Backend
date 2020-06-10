package com.wequan.bu.repository.model;

import java.time.LocalDateTime;

/**
 * @author Zhaochao Huang
 */
public class Material {
    private Integer id;
    private LocalDateTime checkTime;
    private Integer checker;
    private String comment;
    private Integer courseId;
    private String fileName;
    private String filtType;
    private Integer likes;
    private String previewPages;
    private Integer professorId;
    private String sourceExternalLink;
    private String sourceFrom;
    private Integer status;
    private String storeDirectory;
    private Integer studyPointsRequired;
    private Integer subjectId;
    private String term;
    private Integer totalPage;
    private Integer type;
    private Integer uploadBy;
    private LocalDateTime uploadTime;
    private String uuid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(LocalDateTime checkTime) {
        this.checkTime = checkTime;
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

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFiltType() {
        return filtType;
    }

    public void setFiltType(String filtType) {
        this.filtType = filtType;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getPreviewPages() {
        return previewPages;
    }

    public void setPreviewPages(String previewPages) {
        this.previewPages = previewPages;
    }

    public Integer getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Integer professorId) {
        this.professorId = professorId;
    }

    public String getSourceExternalLink() {
        return sourceExternalLink;
    }

    public void setSourceExternalLink(String sourceExternalLink) {
        this.sourceExternalLink = sourceExternalLink;
    }

    public String getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStoreDirectory() {
        return storeDirectory;
    }

    public void setStoreDirectory(String storeDirectory) {
        this.storeDirectory = storeDirectory;
    }

    public Integer getStudyPointsRequired() {
        return studyPointsRequired;
    }

    public void setStudyPointsRequired(Integer studyPointsRequired) {
        this.studyPointsRequired = studyPointsRequired;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(Integer uploadBy) {
        this.uploadBy = uploadBy;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
