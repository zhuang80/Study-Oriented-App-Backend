package com.wequan.bu.repository.model.extend;

import com.wequan.bu.repository.model.School;
import com.wequan.bu.repository.model.Subject;
import com.wequan.bu.repository.model.Tutor;
import lombok.Data;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Data
public class TutorRateInfo extends Tutor {
    private Double score;
    private Integer viewNumber;
    private List<Subject> subjectList;
}
