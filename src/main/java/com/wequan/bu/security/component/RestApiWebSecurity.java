package com.wequan.bu.security.component;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ChrisChen
 */
public class RestApiWebSecurity {

    public boolean checkUserId(Authentication authentication, HttpServletRequest request, int userId) {
//        int authUserId = Integer.parseInt(authentication.getPrincipal().toString());
//        return authUserId == userId;
        return true;
    }
}
