package com.wequan.bu.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Data
@JsonIgnoreProperties("handler")
public class Course{

    private Integer id;
    private String name;
    private String codeFirst;
    private String codeSecond;
    private Integer schoolId;
    private Integer subjectId;
    private List<Professor> professors;

}
