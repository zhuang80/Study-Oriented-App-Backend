package com.wequan.bu.repository.model.extend;

import lombok.Data;

/**
 * @author ChrisChen
 */
@Data
public class UserBriefInfo {

    private Integer id;
    private String userName;
    private String firstName;
    private String lastName;
    private Short schoolId;
    private byte[] avatar;
    private String avatarUrlInProvider;

}
