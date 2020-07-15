package com.wequan.bu.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author Zhaochao Huang
 */
@Data
@JsonIgnoreProperties("handler")
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

    private String comment;

    private Boolean anonymous;

    private Boolean usingTextbook;

    private Boolean attendance;

    private User user;

}
