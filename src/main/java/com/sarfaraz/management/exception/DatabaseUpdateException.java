package com.sarfaraz.management.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DatabaseUpdateException extends RuntimeException {
    public DatabaseUpdateException(String s) {
        super(s);
    }

    public DatabaseUpdateException(String s, Throwable t) {
        super(s, t);
    }
}
