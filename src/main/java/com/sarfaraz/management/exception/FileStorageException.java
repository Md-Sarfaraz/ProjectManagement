package com.sarfaraz.management.exception;

public class FileStorageException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String msg;

	public FileStorageException(String msg) {
		this.msg = msg;
	}

	public FileStorageException() {
	}

	public String getMsg() {
		return msg;
	}

	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}
}