package com.wequan.bu.repository.model.extend;

import lombok.Data;

/**
 * @author ChrisChen
 */
@Data
public class UserFollowBriefInfo {

    private Integer userId;
    private String userName;
    private String firstName;
    private String lastName;
    private Byte[] avatar;
    private String avatarUrlInProvider;
    private Boolean mutual;

}
