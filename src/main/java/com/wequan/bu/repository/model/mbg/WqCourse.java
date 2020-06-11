package com.wequan.bu.repository.model.mbg;

public class WqCourse {
    private Integer id;

    private String name;

    private Short schoolId;

    private String codeFirst;

    private String codeSecond;

    private Integer categoryId;

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

    public Short getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Short schoolId) {
        this.schoolId = schoolId;
    }

    public String getCodeFirst() {
        return codeFirst;
    }

    public void setCodeFirst(String codeFirst) {
        this.codeFirst = codeFirst;
    }

    public String getCodeSecond() {
        return codeSecond;
    }

    public void setCodeSecond(String codeSecond) {
        this.codeSecond = codeSecond;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}