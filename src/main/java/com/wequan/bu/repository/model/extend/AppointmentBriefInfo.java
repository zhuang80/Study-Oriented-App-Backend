package com.wequan.bu.repository.model.extend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wequan.bu.repository.model.Appointment;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ChrisChen
 */

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"fee", "transactionId"})
public class AppointmentBriefInfo extends Appointment {

    private String tutorName;
    private String userName;

}