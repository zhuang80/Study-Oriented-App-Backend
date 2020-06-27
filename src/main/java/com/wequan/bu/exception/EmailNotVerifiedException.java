package com.wequan.bu.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author ChrisChen
 */
public class EmailNotVerifiedException extends AuthenticationException {

    public EmailNotVerifiedException(String msg, Throwable t) {
        super(msg, t);
    }

    public EmailNotVerifiedException(String msg) {
        super(msg);
    }

    public EmailNotVerifiedException() {
        this("Email is not verified");
    }

}
