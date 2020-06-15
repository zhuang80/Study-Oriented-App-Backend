package com.wequan.bu.repository.model;

import lombok.Data;

import java.util.Date;

/**
 * @author Zhaochao Huang
 */
@Data
public class Material {

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

}
