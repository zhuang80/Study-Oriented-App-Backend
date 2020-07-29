package com.wequan.bu.repository.model.extend;

import com.wequan.bu.repository.model.Appointment;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ChrisChen
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class AppointmentBriefInfo extends Appointment {

    private UserBriefInfo userBriefInfo;
    private TutorBasicInfo tutorBasicInfo;

}

@Data
class TutorBasicInfo {
    Integer id;
    String userName;
    String firstName;
    String lastName;
    Short schoolId;
    String avatarUrl;
    String avatarUrlInProvider;
}