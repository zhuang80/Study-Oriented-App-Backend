package com.wequan.bu.repository.model.extend;

import lombok.Data;

/**
 * @author ChrisChen
 */
@Data
public class ThreadBriefInfo {

    private Integer id;
    private Integer createBy;
    private String title;
    private Short category;
    private Short tagId;

}
