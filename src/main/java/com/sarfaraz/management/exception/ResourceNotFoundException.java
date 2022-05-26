package com.sarfaraz.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException() {
	}

	public ResourceNotFoundException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
}
