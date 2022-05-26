package com.sarfaraz.management.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.sarfaraz.management.exception.CredentialsException;
import com.sarfaraz.management.exception.FileNotFoundException;
import com.sarfaraz.management.exception.FileStorageException;
import com.sarfaraz.management.exception.ResourceNotFoundException;
import com.sarfaraz.management.exception.UserInputException;
import com.sarfaraz.management.exception.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class AppExceptionAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetail> globalException(Exception exception, WebRequest request) {
		ErrorDetail error = new ErrorDetail(request.getDescription(false), exception.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR.value());
		log.info(exception.getClass().getSimpleName());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetail> resourceNotFoundException(ResourceNotFoundException exception,
			WebRequest request) {
		ErrorDetail error = new ErrorDetail(exception.getMessage(), request.getDescription(false),
				HttpStatus.NOT_FOUND.value());
		log.info(" Resource :: {}", exception.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDetail> userNotFound(UserNotFoundException exception, WebRequest request) {
		ErrorDetail error = new ErrorDetail(exception.getMessage(), request.getDescription(false),
				HttpStatus.NOT_FOUND.value());
		log.info(" User :: {}", exception.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CredentialsException.class)
	public ResponseEntity<ErrorDetail> badCredntial(CredentialsException exception, WebRequest request) {
		log.info(exception.getMessage());
		return new ResponseEntity<>(new ErrorDetail(exception.getMessage(), "Bad Credentials",
				HttpStatus.UNAUTHORIZED.value(), request.getDescription(false)), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UserInputException.class)
	public ResponseEntity<ErrorDetail> badRequest(UserInputException exception, WebRequest request) {
		log.info(exception.getMessage());
		return new ResponseEntity<>(new ErrorDetail("Bad Request", exception.getMessage(),
				HttpStatus.BAD_REQUEST.value(), request.getDescription(false)), HttpStatus.BAD_REQUEST);
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
}