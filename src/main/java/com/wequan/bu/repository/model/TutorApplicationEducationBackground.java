package com.wequan.bu.repository.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author Zhaochao Huang
 */
@Data
public class TutorApplicationEducationBackground {
    private Integer id;
    private Integer schoolId;
    private Integer degreeId;
    private Integer majorId;
    private Float gpa;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTo;
}