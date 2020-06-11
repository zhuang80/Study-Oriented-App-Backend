package com.wequan.bu.controller.vo;

import com.wequan.bu.repository.model.School;

public class CourseSubject {

    //this subject is independent with another used in user/tutor

    private int id;
    private String name;
    private School school;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
