package com.wequan.bu.controller.vo;

import com.wequan.bu.repository.model.TutorInquiry;
import lombok.Data;

import java.util.List;

/**
 * @author Zhaochao Huang
 * 根据subject来封装TutorInquiry的查询结果
 */
@Data
public class TutorInquiryGroup {
    private Integer id;
    private String subjectName;
    private List<TutorInquiry> tutorInquiryList;
}
