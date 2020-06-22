package com.wequan.bu.controller.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Data
public class UploadFileWrapper {
    private Short type;
    private Integer uploadBy;
    private List<File> files;

    public UploadFileWrapper(Short type, Integer userId, List<File> files){
        this.type = type;
        this.uploadBy = userId;
        this.files = files;
    }
}
