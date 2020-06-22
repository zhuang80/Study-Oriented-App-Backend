package com.wequan.bu.controller.vo;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Zhaochao Huang
 */
public class MultipartFileWrapper {
    private Short type;
    private Integer uploadBy;
    private MultipartFile[] files;

    public MultipartFileWrapper(){}

    public MultipartFileWrapper(Short type, Integer userId, MultipartFile[] files){
        this.type = type;
        this.uploadBy = userId;
        this.files = files;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Integer getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(Integer uploadBy) {
        this.uploadBy = uploadBy;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
