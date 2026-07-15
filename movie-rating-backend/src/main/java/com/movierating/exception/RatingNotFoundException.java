package com.movierating.exception;

public class RatingNotFoundException extends RuntimeException {

	public RatingNotFoundException(String message) {
		super(message);
	}
}
