package com.wequan.bu.exception;

/**
 * @author ChrisChen
 */
public class NotImplementedException extends Exception {

    public NotImplementedException() {
        super("Not implemented yet");
    }

    public NotImplementedException(String message) {
        super(message);
    }

}
