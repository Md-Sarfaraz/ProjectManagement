package com.sarfaraz.management.controller.advice;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetail {

	private Date timestamp;
	private String message;
	private String error;
	private String path;
	private int status;

	public ErrorDetail(String message, String error, int status) {
		this.timestamp = new Date();
		this.message = message;
		this.error = error;
		this.status = status;
	}

	public ErrorDetail(String message, String error, int status, String path) {
		this.timestamp = new Date();
		this.message = message;
		this.error = error;
		this.status = status;
		this.path = path;
	}

}
