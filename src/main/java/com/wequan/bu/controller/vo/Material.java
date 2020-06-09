package com.wequan.bu.controller.vo;

import java.util.Date;

public class Material {

    private int id;
    private short type;
    private String storeDirectory;
    private String fileName;
    private String sourceFrom;
    private int subjectId;
    private int courseId;
    private int professorId;
    private short studyPointsRequired;
    private String term;
    private String fileType;
    private int uploadBy;
    private Date uploadTime;
    private String sourceExternalLink;
    private short totalPage;
    private byte[] previewPages;
    private int likes;
    private short status;
    private int checker;
    private String comment;
    private Date checkTime;
    private String uuid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
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

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public short getStudyPointsRequired() {
        return studyPointsRequired;
    }

    public void setStudyPointsRequired(short studyPointsRequired) {
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

    public int getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(int uploadBy) {
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

    public short getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(short totalPage) {
        this.totalPage = totalPage;
    }

    public byte[] getPreviewPages() {
        return previewPages;
    }

    public void setPreviewPages(byte[] previewPages) {
        this.previewPages = previewPages;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public int getChecker() {
        return checker;
    }

    public void setChecker(int checker) {
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
}
