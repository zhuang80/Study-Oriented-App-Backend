package com.wequan.bu.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author ChrisChen
 */
public class OAuth2AuthenticationProcessingException extends AuthenticationException {

    public OAuth2AuthenticationProcessingException(String message, Throwable t) {
        super(message, t);
    }

    public OAuth2AuthenticationProcessingException(String message) {
        super(message);
    }
}
