package com.wequan.bu.repository.model;

/**
 * @author Zhaochao Huang
 */
public class Department {
    private Integer id;
    private String name;
    private Integer school_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSchool_id() {
        return school_id;
    }

    public void setSchool_id(Integer school_id) {
        this.school_id = school_id;
    }
}
