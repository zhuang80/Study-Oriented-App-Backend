package com.wequan.bu.repository.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTo;
}