package com.wequan.bu.controller.vo;

public class ProfessorCourseReview {
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

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Float getQualityScore() {
        return qualityScore;
    }

    public void setQualityScore(Float qualityScore) {
        this.qualityScore = qualityScore;
    }

    public Float getDifficultScore() {
        return difficultScore;
    }

    public void setDifficultScore(Float difficultScore) {
        this.difficultScore = difficultScore;
    }

    public Short getNumberOfExams() {
        return numberOfExams;
    }

    public void setNumberOfExams(Short numberOfExams) {
        this.numberOfExams = numberOfExams;
    }

    public Short getNumberOfQuizzes() {
        return numberOfQuizzes;
    }

    public void setNumberOfQuizzes(Short numberOfQuizzes) {
        this.numberOfQuizzes = numberOfQuizzes;
    }

    public Short getNumberOfHomeworks() {
        return numberOfHomeworks;
    }

    public void setNumberOfHomeworks(Short numberOfHomeworks) {
        this.numberOfHomeworks = numberOfHomeworks;
    }

    public Short getNumberOfProjects() {
        return numberOfProjects;
    }

    public void setNumberOfProjects(Short numberOfProjects) {
        this.numberOfProjects = numberOfProjects;
    }

    public Short getNumberOfPapers() {
        return numberOfPapers;
    }

    public void setNumberOfPapers(Short numberOfPapers) {
        this.numberOfPapers = numberOfPapers;
    }

    public Float getGradeReceived() {
        return gradeReceived;
    }

    public void setGradeReceived(Float gradeReceived) {
        this.gradeReceived = gradeReceived;
    }

    public Short getTag() {
        return tag;
    }

    public void setTag(Short tag) {
        this.tag = tag;
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