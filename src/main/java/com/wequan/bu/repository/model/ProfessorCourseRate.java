package com.wequan.bu.repository.model;

import lombok.Data;

/**
 * @author Zhaochao Huang
 */
@Data
public class ProfessorCourseRate {

    private Integer id;

    private Integer professorId;

    private Integer courseId;

    private Integer createBy;

    private Float qualityScore;

    private Float difficultScore;

    private Short numberOfExams;

    private Short numberOfQuizzes;

    private Short numberOfHomeworks;

    private Short numberOfProjects;

    private Short numberOfPapers;

    private Float gradeReceived;

    private Short tag;

    private String comment;

    private Boolean anonymous;

    private Boolean usingTextbook;

    private Boolean attendance;



}
