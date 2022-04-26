package com.sarfaraz.management.controller.advice;

import com.sarfaraz.management.exception.BadCredentialsException;
import com.sarfaraz.management.exception.FileNotFoundException;
import com.sarfaraz.management.exception.FileStorageException;
import com.sarfaraz.management.exception.UserNotFoundException;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AppExceptionAdvice {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetail> globalException(Exception exception, WebRequest request) {
		ErrorDetail error = new ErrorDetail(exception.getMessage(), request.getDescription(false),
				HttpStatus.INTERNAL_SERVER_ERROR.value());
		log.info(exception.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(FileStorageException.class)
	public ModelAndView handleException(FileStorageException exception, WebRequest request) {

		ModelAndView mav = new ModelAndView();
		mav.addObject("message", exception.getMsg());
		mav.setViewName("error/500");
		return mav;
	}

	@ExceptionHandler(FileNotFoundException.class)
	public ModelAndView downloadFileException(FileStorageException exception, WebRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("message", exception.getMsg());
		mav.setViewName("error/500");
		return mav;
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDetail> userNotFound(UserNotFoundException exception, WebRequest request) {
		ErrorDetail error = new ErrorDetail(exception.getMessage(), request.getDescription(false),
				HttpStatus.NOT_FOUND.value());
		log.info(" User :: {}", exception.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorDetail> badRequest(UserNotFoundException exception, WebRequest request) {
		ErrorDetail error = new ErrorDetail(exception.getMessage(), request.getDescription(false),
				HttpStatus.NOT_FOUND.value());
		log.info(exception.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
}