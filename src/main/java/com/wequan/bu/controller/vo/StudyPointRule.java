package com.wequan.bu.controller.vo;

/**
 * @author ChrisChen
 */
public enum StudyPointRule {
    /**
     * Study Point Rules
     */
    REGISTER_SUCCESS(5),
    IMPROVE_USER_PROFILE(5),
    TUTOR_APPLICATION_SUCCESS(15),
    APPOINTMENT_SUCCESS(5),
    POST_THREAD(5);

    private int studyPoint;

    StudyPointRule(int studyPoint) {
        this.studyPoint = studyPoint;
    }

    public int getStudyPoint() {
        return studyPoint;
    }
}
