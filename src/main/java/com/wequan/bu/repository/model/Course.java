package com.wequan.bu.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@JsonIgnoreProperties("handler")
public class Course{
    private Integer id;
    private String name;
    private String codeFirst;
    private String codeSecond;
    private Integer schoolId;
  //  private List<ProfessorRate> professorRateList;
    private List<Professor> professors;

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

    public List<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }
}
