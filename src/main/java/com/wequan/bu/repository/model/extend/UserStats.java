package com.wequan.bu.repository.model.extend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wequan.bu.repository.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ChrisChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"accessToken", "invitationCode", "following", "followed", "providerId"})
public class UserStats extends User {

    private String subjectIds;
    private int numberOfThreads;
    private int numberOfFollowing;
    private int numberOfFollowed;
    private int numberOfGroups;

}
