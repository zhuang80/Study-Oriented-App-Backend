package com.wequan.bu.repository.model.extend;

import com.wequan.bu.repository.model.FavoriteTutor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ChrisChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FavoriteTutorBriefInfo extends FavoriteTutor {

    private String tutorName;
    private String tutorFirstName;
    private String tutorLastName;
    private Float overallRating;
    private String subjectIds;

}
