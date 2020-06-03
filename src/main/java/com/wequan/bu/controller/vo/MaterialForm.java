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
}
