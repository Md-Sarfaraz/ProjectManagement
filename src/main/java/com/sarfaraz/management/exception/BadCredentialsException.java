package com.sarfaraz.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BadCredentialsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public BadCredentialsException(String message) {
		super(message);
	}

	public BadCredentialsException() {
	}

	public BadCredentialsException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

}
