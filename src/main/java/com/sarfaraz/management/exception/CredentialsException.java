package com.sarfaraz.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CredentialsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CredentialsException(String message) {
		super(message);
	}

	public CredentialsException() {

	}

	public CredentialsException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

}
