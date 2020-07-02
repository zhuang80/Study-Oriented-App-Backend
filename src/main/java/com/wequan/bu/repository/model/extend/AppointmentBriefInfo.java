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

    private String tutorName;
    private String userName;

}