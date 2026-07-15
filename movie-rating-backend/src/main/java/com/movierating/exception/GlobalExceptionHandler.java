package com.movierating.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.movierating.dto.ApiResponse;

@RestControllerAdvice // applies these handlers to every @RestController in the app
public class GlobalExceptionHandler {

	@ExceptionHandler(MovieNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleMovieNotFound(MovieNotFoundException ex) {
		return buildError(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MovieAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleMovieExists(MovieAlreadyExistsException ex) {
		return buildError(ex.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
		return buildError(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleUserExists(UserAlreadyExistsException ex) {
		return buildError(ex.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
		return buildError(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(RatingNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleRatingNotFound(RatingNotFoundException ex) {
		return buildError(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	// triggered automatically whenever a @Valid DTO fails its validation annotations
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleValidation(MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors()
				.forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

		ApiResponse response = new ApiResponse(false, "Validation failed", errors);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
		return buildError("Something went wrong: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<ErrorResponse> buildError(String message, HttpStatus status) {

		ErrorResponse error = new ErrorResponse(false, message, status.value(), LocalDateTime.now());

		return new ResponseEntity<>(error, status);
	}

}
