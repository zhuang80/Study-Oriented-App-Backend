package com.wequan.bu.repository.model.extend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Base64;

/**
 * @author ChrisChen
 */
@Data
public class UserFollowBriefInfo {

    private Integer userId;
    private String userName;
    private String firstName;
    private String lastName;
    @JsonIgnore
    private byte[] avatar;
    private String avatarBase64Encoded;
    private String avatarUrlInProvider;
    private Boolean mutual;

    public String getAvatarBase64Encoded() {
        if (this.avatar != null) {
            this.avatarBase64Encoded = Base64.getEncoder().encodeToString(this.avatar);
        }
        return this.avatarBase64Encoded;
    }
}
