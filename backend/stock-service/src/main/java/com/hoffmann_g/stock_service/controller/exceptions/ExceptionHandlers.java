package com.hoffmann_g.stock_service.controller.exceptions;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> database(ResourceNotFoundException e, HttpServletRequest request) {
		String error = e.getClass().getSimpleName();
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
    }

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> database(MethodArgumentNotValidException e, HttpServletRequest request) {
		
		Set<String> errors = new HashSet<>();
    	e.getBindingResult().getAllErrors().forEach((error) -> {
			String errorMessage = error.getDefaultMessage();
			errors.add(errorMessage);
    	});

		String error = e.getClass().getSimpleName();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, errors.toString(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
    }

	@ExceptionHandler(ProductAlreadyExistsException.class)
	public ResponseEntity<StandardError> database(ProductAlreadyExistsException e, HttpServletRequest request) {
		String error = e.getClass().getSimpleName();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
    }

	@ExceptionHandler(InvalidArgumentException.class)
	public ResponseEntity<StandardError> database(InvalidArgumentException e, HttpServletRequest request) {
		String error = e.getClass().getSimpleName();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
    }

	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<StandardError> database(InsufficientStockException e, HttpServletRequest request) {
		String error = e.getClass().getSimpleName();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
    }

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<StandardError> database(MissingServletRequestParameterException e, HttpServletRequest request) {
		String error = e.getClass().getSimpleName();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
    }

}
