package com.wequan.bu.controller.vo;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Zhaochao Huang
 */
public class MaterialForm {
    private Integer type;
    private String sourceFrom;
    private Integer studyPointsRequired;
    private String term;
    private MultipartFile[] files;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom;
    }

    public Integer getStudyPointsRequired() {
        return studyPointsRequired;
    }

    public void setStudyPointsRequired(Integer studyPointsRequired) {
        this.studyPointsRequired = studyPointsRequired;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
