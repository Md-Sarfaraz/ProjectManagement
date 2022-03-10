package com.sarfaraz.management.exception;

public class UserInputError extends RuntimeException {
    public UserInputError(String message) {
        super(message);
    }

    public UserInputError() {
    }

    public UserInputError(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
