package com.wequan.bu.repository.model;

/**
 * @author Zhaochao Huang
 */
public class ProfessorCourseRate {
    private Integer id;
    private Integer professorId;
    private Integer courseId;
    private Integer userId;
    private Double qualityScore;
    private Double difficultScore;
    private Integer numberOfExams;
    private Integer numberOfQuizzes;
    private Integer numberOfHomeworks;
    private Integer numberOfProjects;
    private Integer numberOfPapers;
    private Double gradeReceived;
    private Integer tags;
    private String comment;
    private Boolean anonymous;
    private Boolean usingTextbook;
    private Boolean attendance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Integer professorId) {
        this.professorId = professorId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getQualityScore() {
        return qualityScore;
    }

    public void setQualityScore(Double qualityScore) {
        this.qualityScore = qualityScore;
    }

    public Double getDifficultScore() {
        return difficultScore;
    }

    public void setDifficultScore(Double difficultScore) {
        this.difficultScore = difficultScore;
    }

    public Integer getNumberOfExams() {
        return numberOfExams;
    }

    public void setNumberOfExams(Integer numberOfExams) {
        this.numberOfExams = numberOfExams;
    }

    public Integer getNumberOfQuizzes() {
        return numberOfQuizzes;
    }

    public void setNumberOfQuizzes(Integer numberOfQuizzes) {
        this.numberOfQuizzes = numberOfQuizzes;
    }

    public Integer getNumberOfHomeworks() {
        return numberOfHomeworks;
    }

    public void setNumberOfHomeworks(Integer numberOfHomeworks) {
        this.numberOfHomeworks = numberOfHomeworks;
    }

    public Integer getNumberOfProjects() {
        return numberOfProjects;
    }

    public void setNumberOfProjects(Integer numberOfProjects) {
        this.numberOfProjects = numberOfProjects;
    }

    public Integer getNumberOfPapers() {
        return numberOfPapers;
    }

    public void setNumberOfPapers(Integer numberOfPapers) {
        this.numberOfPapers = numberOfPapers;
    }

    public Double getGradeReceived() {
        return gradeReceived;
    }

    public void setGradeReceived(Double gradeReceived) {
        this.gradeReceived = gradeReceived;
    }

    public Integer getTags() {
        return tags;
    }

    public void setTags(Integer tags) {
        this.tags = tags;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Boolean getUsingTextbook() {
        return usingTextbook;
    }

    public void setUsingTextbook(Boolean usingTextbook) {
        this.usingTextbook = usingTextbook;
    }

    public Boolean getAttendance() {
        return attendance;
    }

    public void setAttendance(Boolean attendance) {
        this.attendance = attendance;
    }
}
