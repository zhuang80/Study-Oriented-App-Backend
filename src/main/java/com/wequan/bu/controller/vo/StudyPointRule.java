package com.wequan.bu.controller.vo;

/**
 * @author ChrisChen
 */
public enum StudyPointRule {
    /**
     * Study Point Rules
     */
    REGISTER_SUCCESS("register success", 5),
    COMPLETE_USER_PROFILE("complete user profile", 5),
    TUTOR_APPLICATION_SUCCESS("apply tutor success", 15),
    APPOINTMENT_SUCCESS("make appointment success", 5),
    POST_THREAD("post thread", 5);

    private int studyPoint;
    private String action;

    StudyPointRule(String action, int studyPoint) {
        this.action = action;
        this.studyPoint = studyPoint;
    }

    public int getStudyPoint() {
        return studyPoint;
    }

    public String getAction() {
        return action;
    }
}
