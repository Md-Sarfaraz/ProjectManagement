package com.sarfaraz.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserInputException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserInputException(String message) {
		super(message);
	}

	public UserInputException() {
	}

	public UserInputException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
}
