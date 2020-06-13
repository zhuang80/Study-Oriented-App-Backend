package com.wequan.bu.controller.vo;

public class CourseVo {
    private Integer id;
    private String name;
    private String codeFirst;
    private String codeSecond;
    private Integer schoolId;
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

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
