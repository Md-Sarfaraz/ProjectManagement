package com.sarfaraz.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException() {
	}

	public UserNotFoundException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
}
