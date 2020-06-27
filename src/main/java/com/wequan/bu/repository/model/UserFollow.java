package com.wequan.bu.repository.model;

import lombok.Data;

@Data
public class UserFollow {

    /**
     * following or followed user
     */
    private User user;

    /**
     * mutual follow or not
     */
    private Boolean mutual;
}