package com.wequan.bu.exception;

/**
 * @author ChrisChen
 */
public class NotImplementedException extends RuntimeException {

    public NotImplementedException() {
        super("Not implemented yet");
    }

    public NotImplementedException(String message) {
        super(message);
    }

}
