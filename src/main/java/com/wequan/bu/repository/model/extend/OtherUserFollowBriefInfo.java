package com.wequan.bu.repository.model.extend;

import lombok.Data;

/**
 * @author ChrisChen
 */
@Data
public class OtherUserFollowBriefInfo {
    private Integer userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String avatarUrl;
    private String avatarUrlInProvider;
    private Boolean follow;
}
