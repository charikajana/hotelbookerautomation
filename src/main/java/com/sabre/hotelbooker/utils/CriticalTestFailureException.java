package com.sabre.hotelbooker.utils;

/**
 * Critical test failure exception that stops test execution immediately.
 * This exception is designed to be unrecoverable and should stop the entire test scenario.
 */
public class CriticalTestFailureException extends RuntimeException {
    
    public CriticalTestFailureException(String message) {
        super(message);
    }
    
    public CriticalTestFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}